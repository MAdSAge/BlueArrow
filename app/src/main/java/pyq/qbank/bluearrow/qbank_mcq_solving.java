package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;

public class qbank_mcq_solving extends AppCompatActivity implements pasueProgressBar {

    private static final int TIMER_DURATION = 54000;
    Box<iconOrderLoader> red;
    private RecyclerView recyclerView;
    private mcq_model_adapter adapter;
    private List<entityMcq> topicList;
    private Button previousButton, nextButton, doReview, solveNextModule;
    private boolean Mode;
    private String chapterTitle;
    private NestedScrollView nestScrool;
    private TextView moduleName, timerTextView, CorrectCount, WrongCount, SkipCount, chapTitle;
    private ProgressBar pbar;
    private CountDownTimer countDownTimer;
    private long timeRemaining = TIMER_DURATION;     // Remaining time in milliseconds
    private boolean isPaused = false;// 1 minute in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initial UI set up ------------------------------------------------------------------------
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qbank_mcq_solving);

        red = boxStore.boxFor(iconOrderLoader.class);

        hideSystemUI();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mcqSolving), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getting the intent values-----------------------------------------------------------------
        Intent test_Data = getIntent();
        chapterTitle = test_Data.getStringExtra("chapterName");
        Mode = test_Data.getBooleanExtra("testMode", true);

        //if not test mode move to the review mode activity-----------------------------------------
        if (!Mode) {
            Intent intent = new Intent(this, reviewMcqSmall.class);
            intent.putExtra("chapter", chapterTitle);
            startActivity(intent);
            finish();
        }

        //setting the module name
        moduleName = findViewById(R.id.moduleName);
        timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setVisibility(View.GONE);

        moduleName.setText(chapterTitle);


        //getting the nested scroll view
        nestScrool = findViewById(R.id.mcqSolving);
        //Recycler view setUp-----------------------------------------------------------------------
        recyclerView = findViewById(R.id.mcqRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // getting McQs for the current chapter
        topicList = getMcQs(chapterTitle);

        // Example MCQ instance
        recyclerView.setItemViewCacheSize(topicList.size());  // Keep 5 views in memory
        adapter = new mcq_model_adapter(topicList, Mode, this);
        recyclerView.setAdapter(adapter); // Adjust to the number of views you expect to cache


        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);

        //initializing progess bar
        pbar = findViewById(R.id.progressTimer);

        // Initialize the progress bar to max value
        pbar.setMax(100); // Progress bar range (0 to 100)
        pbar.setProgress(100); // Start at 100%

        // Start the timer
        startTimer(TIMER_DURATION);


        // Set initial button states
        updateButtonVisibility(0);

        // Set click listeners
        nextButton.setOnClickListener(v -> handleNextButtonClick());
        previousButton.setOnClickListener(v -> handlePreviousButtonClick());

        // Add scroll listener to update buttons based on scrolling position
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                updateButtonVisibility(currentPosition);
            }
        });
    }

    private void startTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished; // Update the remaining time
                int progress = (int) (millisUntilFinished * 100 / TIMER_DURATION);
                pbar.setProgress(progress);

                // Optionally update the timer text view
                timerTextView.setVisibility(View.VISIBLE);
                timerTextView.setText("⌛ " + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                pbar.setProgress(0);
                Toast.makeText(qbank_mcq_solving.this, "Time is up!", Toast.LENGTH_SHORT).show();
                timeUP(recyclerView);
            }
        };

        countDownTimer.start();
    }

    private void timeUP(RecyclerView recyclerView) {
        // Get the layout manager of the RecyclerView
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

            // Find the first completely visible item position
            int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

            // Check if the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Get the MCQ at the current position
                entityMcq currentMcq = topicList.get(position);

                // Get the ViewHolder for the current position
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);

                if (viewHolder instanceof mcq_model_adapter.mcqViewHolder) {
                    mcq_model_adapter.mcqViewHolder currentHolder = (mcq_model_adapter.mcqViewHolder) viewHolder;

                    // Call the answer checker for time-up scenario
                    adapter.timeUp(currentMcq, currentHolder, 0);
                }
            }
        }


    }


    private List<entityMcq> getMcQs(String chapterTitle) {
        Box<entityMcq> mcqBox = boxStore.boxFor(entityMcq.class);
        List<entityMcq> mcqList = mcqBox.query(entityMcq_.chapter.equal(chapterTitle)).build().find();
        // add a data base cleaner class fro setting up correct answers or edit the data base to do so later
        return mcqList;
    }

    private void submitQuiz() {

        iconOrderLoader currentChap = red.query(iconOrderLoader_.chapter.equal(chapterTitle)).build().findFirst();
        currentChap.setChapter_completed(true);
        currentChap.setChapter_progress(1);
        red.put(currentChap);


        iconOrderLoader nextChap = red.get(currentChap.getId() + 1);

        Toast.makeText(this, "Quiz submitted", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.result_layout);  // Switch to new layout
        doReview = findViewById(R.id.doReview);
        solveNextModule = findViewById(R.id.solveNextModule);

        solveNextModule.setText("--: Solve Next :--\n\n'"+nextChap.getChapter()+"'");

        CorrectCount = findViewById(R.id.CorrectCount);
        WrongCount = findViewById(R.id.WrongCount);
        SkipCount = findViewById(R.id.SkipCount);
        chapTitle = findViewById(R.id.chapterTitle);

        calculateScores(currentChap);


        solveNextModule.setOnClickListener(v -> {

            Toast.makeText(this, "Solve next module", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(this, qbank_mcq_solving.class);
            intent.putExtra("chapterName", nextChap.getChapter());
            intent.putExtra("ReviewMode", false);
            startActivity(intent);
            finish();


        });

        doReview.setOnClickListener(v -> {
            Toast.makeText(this, "Review lesson", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, qbank_mcq_solving.class);
            intent.putExtra("ReviewMode", true);
            startActivity(intent);
            finish();
            // Handle review lesson button click
            // move to the reviwing the module ?
        });


        // Logic for submission
    }

    private void calculateScores(iconOrderLoader currentChap) {


        int correctScore = 0;
        int skipScore = 0;
        int wrongScore = 0;


        // Loop through the topicList and calculate scores
        for (entityMcq m : topicList) {
            int userAnswer = m.getUserAnswer();
            int correctAnswer = m.getCorrectAnswer();

            if (userAnswer == 0) {
                skipScore++;
            } else if (userAnswer == correctAnswer) {
                correctScore++;
            } else {
                wrongScore++;
            }
        }


        currentChap.setCorrect_count(correctScore);
        currentChap.setWrong_count(wrongScore);
        currentChap.setSkip_count(skipScore);
        red.put(currentChap);

        // Calculate percentages
        int totalQuestions = topicList.size();
        int correctPercentage = (correctScore * 100) / totalQuestions;
        int wrongPercentage = (wrongScore * 100) / totalQuestions;
        int skipPercentage = (skipScore * 100) / totalQuestions;

        // Update UI with the calculated scores and percentages
        CorrectCount.setText(String.format("%d correct\n(%d%%)", correctScore, correctPercentage));
        WrongCount.setText(String.format("%d wrong\n(%d%%)", wrongScore, wrongPercentage));
        SkipCount.setText(String.format("%d skipped\n(%d%%)", skipScore, skipPercentage));
        chapTitle.setText(chapterTitle);
    }


    private void handleNextButtonClick() {

        int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int nextPosition = currentPosition + 1;

        if (nextPosition < adapter.getItemCount()) {
            recyclerView.smoothScrollToPosition(nextPosition);
        }


        updateButtonVisibility(nextPosition);
        if (nestScrool != null) {
            nestScrool.scrollTo(0, 0); // Reset the ScrollView's vertical scroll position
        }

        // Reset the timer
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the current timer
        }
        timeRemaining = TIMER_DURATION; // Reset the time remaining to full duration
        startTimer(TIMER_DURATION);
    }

    private void handlePreviousButtonClick() {

        int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int previousPosition = currentPosition - 1;

        if (previousPosition >= 0) {
            recyclerView.smoothScrollToPosition(previousPosition);
        }

        updateButtonVisibility(previousPosition);
        if (nestScrool != null) {
            nestScrool.scrollTo(0, 0); // Reset the ScrollView's vertical scroll position
        }
    }

    private void updateButtonVisibility(int position) {
        // Set previous button visibility based on the position
        previousButton.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

        // Check if we are at the last position
        if (position == adapter.getItemCount() - 1) {
            // Set the text based on ReviewMode only once

            // Assign the submit listener only once at the end
            if (nextButton.getTag() == null || !nextButton.getTag().equals("submit")) {
                nextButton.setOnClickListener(v -> submitQuiz());
                nextButton.setTag("submit"); // Tag to track the listener type
            }
        } else {
            // Set the text to "Next"
            nextButton.setText("Next");

            // Assign the next button listener only once
            if (nextButton.getTag() == null || !nextButton.getTag().equals("next")) {
                nextButton.setOnClickListener(v -> handleNextButtonClick());
                nextButton.setTag("next"); // Tag to track the listener type
            }
        }
    }



    private void hideSystemUI() {
        getSupportActionBar().hide();

        // Set the flags to hide both the status bar and navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN // Hide status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide navigation bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // Keep the UI hidden even after user interaction
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void sendData(boolean data) {


        if (countDownTimer != null && data) {
            countDownTimer.cancel();
            isPaused = true;
            Toast.makeText(this, "Timer paused", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
