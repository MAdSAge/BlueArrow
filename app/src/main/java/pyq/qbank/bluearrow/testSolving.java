package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.objectbox.Box;

public class testSolving extends AppCompatActivity {

    private Box<entityMcq> red;
    private List<entityMcq> mcqList;

    private int TIMER_DURATION; // Duration in milliseconds
    private long remainingTimeInMillis; // Remaining time in milliseconds

    private TextView testTimerTextView, testModuleName;
    private Button testPreviousButton, testNextButton;
    private ProgressBar testProgressTimer;
    private ImageView testQuizNavButton;
    private RecyclerView testMcqRecyclerView;

    private testMcqAdapter adapter;
    private CountDownTimer countDownTimer;

    String test_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_solving);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get intent data
        Intent intent = getIntent();
        test_name = intent.getStringExtra("testTitle");
        String test_id = intent.getStringExtra("testID");
        TIMER_DURATION = intent.getIntExtra("duration", 0) * 1000; // Convert seconds to milliseconds
        remainingTimeInMillis = TIMER_DURATION;

        // Query MCQs
        red = boxStore.boxFor(entityMcq.class);
        mcqList = red.query(entityMcq_.chapter.equal(test_name).and(entityMcq_.chapter_id.equal(test_id))).build().find();

        initializeTheUI();
        startTimer();
        setUiFunction();
        hideSystemUI();

        testNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNextButtonClick();
            }
        });

        testPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePreviousButtonClick();
            }});
    }

    private void initializeTheUI() {
        // Initialize UI elements
        testTimerTextView = findViewById(R.id.testTimerTextView);
        testModuleName = findViewById(R.id.testModuleName);
        testPreviousButton = findViewById(R.id.testPreviousButton);
        testNextButton = findViewById(R.id.testNextButton);
        testProgressTimer = findViewById(R.id.testProgressTimer);
        testQuizNavButton = findViewById(R.id.testQuizNavButton);
        testMcqRecyclerView = findViewById(R.id.testMcqRecyclerView);

        testModuleName.setText(test_name);


        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        testMcqRecyclerView.setLayoutManager(layoutManager);
        adapter = new testMcqAdapter(mcqList);
        testMcqRecyclerView.setAdapter(adapter);
    }

    private void setUiFunction() {
        
        // Additional UI functions can be implemented here
    }

    private void startTimer() {
        // Initialize and start the CountDownTimer
        countDownTimer = new CountDownTimer(remainingTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInMillis = millisUntilFinished;
                updateTimerUI();
            }

            @Override
            public void onFinish() {
                testTimerTextView.setText("00:00:00");
                // Handle the end of the test here, e.g., navigate to a results screen
            }
        }.start();
    }

    private void updateTimerUI() {
        // Convert milliseconds to hh:mm:ss
        int hours = (int) (remainingTimeInMillis / (1000 * 60 * 60));
        int minutes = (int) ((remainingTimeInMillis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((remainingTimeInMillis % (1000 * 60)) / 1000);

        // Format and set the timer text
        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        testTimerTextView.setText(timeFormatted);

        // Update progress
        int progress = (int) (((remainingTimeInMillis) * 100) / TIMER_DURATION);
        runOnUiThread(() -> testProgressTimer.setProgress(progress));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer to prevent memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
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


    private void handleNextButtonClick() {

        int currentPosition = ((LinearLayoutManager) testMcqRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int nextPosition = currentPosition + 1;

        if (nextPosition < adapter.getItemCount()) {
            testMcqRecyclerView.smoothScrollToPosition(nextPosition);
        }


        updateButtonVisibility(nextPosition);
        
    }

    private void handlePreviousButtonClick() {

        int currentPosition = ((LinearLayoutManager) testMcqRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int previousPosition = currentPosition - 1;

        if (previousPosition >= 0) {
            testMcqRecyclerView.smoothScrollToPosition(previousPosition);
        }

        updateButtonVisibility(previousPosition);
        
    }

    private void updateButtonVisibility(int position) {
        // Set previous button visibility based on the position
        testPreviousButton.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

        // Check if we are at the last position
        if (position == adapter.getItemCount() - 1) {
            // Set the text based on ReviewMode only once

            // Assign the submit listener only once at the end
            if (testNextButton.getTag() == null || !testNextButton.getTag().equals("submit")) {
                testNextButton.setOnClickListener(v -> submitQuiz());
                testNextButton.setTag("submit"); // Tag to track the listener type
            }
        } else {
            // Set the text to "Next"
            testNextButton.setText("Next");

            // Assign the next button listener only once
            if (testNextButton.getTag() == null || !testNextButton.getTag().equals("next")) {
                testNextButton.setOnClickListener(v -> handleNextButtonClick());
                testNextButton.setTag("next"); // Tag to track the listener type
            }
        }
    }

    private void submitQuiz() {
    }

}
