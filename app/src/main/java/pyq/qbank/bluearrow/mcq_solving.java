package pyq.qbank.bluearrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class mcq_solving extends AppCompatActivity {

    private RecyclerView recyclerView;
    private mcq_model_adapter adapter;
    private List<mcq_model> topicList;
    private Button previousButton, nextButton,doReview,solveNextModule;
    private boolean ReviewMode,testMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mcq_solving);

        hideSystemUI();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mcqSolving), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.mcqRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        topicList = new ArrayList<>();

        // Example MCQ instance
        recyclerView.setItemViewCacheSize(topicList.size());  // Keep 5 views in memory
        ReviewMode = getIntent().getBooleanExtra("ReviewMode",false);
        testMode = getIntent().getBooleanExtra("testMode",true);

        adapter = new mcq_model_adapter(topicList,ReviewMode,testMode);
        recyclerView.setAdapter(adapter);

        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);




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

    private void submitQuiz() {
        if(!ReviewMode){
        Toast.makeText(this, "Quiz submitted", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.result_layout);  // Switch to new layout
        doReview = findViewById(R.id.doReview);
        solveNextModule = findViewById(R.id.solveNextModule);

        solveNextModule.setOnClickListener(v -> {
            Toast.makeText(this, "Solve next module", Toast.LENGTH_SHORT).show();
            // Handle solve next module button click
            // move to the next module ?
        });

        doReview.setOnClickListener(v -> {
            Toast.makeText(this, "Review lesson", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, mcq_solving.class);
            intent.putExtra("ReviewMode",true);
            startActivity(intent);
            finish();
            // Handle review lesson button click
            // move to the reviwing the module ?
        });
        }else{
            Toast.makeText(this, "Quiz closed", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Logic for submission
    }

    private void handleNextButtonClick() {
        int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int nextPosition = currentPosition + 1;

        if (nextPosition < adapter.getItemCount()) {
            recyclerView.smoothScrollToPosition(nextPosition);
        }

        updateButtonVisibility(nextPosition);
    }

    private void handlePreviousButtonClick() {
        int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int previousPosition = currentPosition - 1;

        if (previousPosition >= 0) {
            recyclerView.smoothScrollToPosition(previousPosition);
        }

        updateButtonVisibility(previousPosition);
    }

    private void updateButtonVisibility(int position) {
        // Set previous button visibility based on the position
        previousButton.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

        // Check if we are at the last position
        if (position == adapter.getItemCount() - 1) {
            // Set the text based on ReviewMode only once
            nextButton.setText(ReviewMode ? "Close" : "Submit");

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
}
