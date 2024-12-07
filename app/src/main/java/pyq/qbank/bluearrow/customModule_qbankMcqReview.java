package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import io.objectbox.Box;

public class customModule_qbankMcqReview extends AppCompatActivity implements fragLoader  {

    TextView title;
    Switch showAnsSwitch;
    Spinner filterSpinner;
    List<entityCustomModuleMcq> currentMcq;

    customModuleFragTwoReviewMcqFullView fragmentTwo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_module_qbank_mcq_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String chapterName = getIntent().getStringExtra("customModuleName");
        String details = getIntent().getStringExtra("details");
        boolean bookmard = getIntent().getBooleanExtra("bookmarked",false);
        setTitle(chapterName);



        title = findViewById(R.id.reviewText);
        title.setText("Review : " + chapterName);

        showAnsSwitch = findViewById(R.id.showAnsSwitch);
        showAnsSwitch.setVisibility(View.INVISIBLE);



        filterSpinner = findViewById(R.id.filterSpinner);

        Box<entityCustomModuleMcq> mcqBox = boxStore.boxFor(entityCustomModuleMcq.class);
        if (bookmard){
            currentMcq = mcqBox.query(entityCustomModuleMcq_.customModuleName.equal(chapterName).and(entityCustomModuleMcq_.bookMarked.equal(true))).build().find();
        }else {
            currentMcq = mcqBox.query(entityCustomModuleMcq_.customModuleName.equal(chapterName)).build().find();
        }

        fragmentTwo = new customModuleFragTwoReviewMcqFullView(currentMcq);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragLoader, new customModuleFragOneReviewMcqsListQuestions(currentMcq))
                    .commit();
        }

        //hide un-necessary
        hideSystemUI();

        showAnsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Find the current fragment and update the state for showing answers



            // Update the flag and notify the fragment to update the view
            fragmentTwo.setShowAnswers(isChecked);

        });
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

    public void chageFragment(int position){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragLoader, new customModuleFragTwoReviewMcqFullView(currentMcq))
                .commit();
    }

    @Override
    public void loadFrag(int position) {

        Bundle bundle = new Bundle();
        bundle.putInt("scrollPosition", position);
        fragmentTwo.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragLoader, fragmentTwo)
                .addToBackStack("fragOne")
                .commit();
        showAnsSwitch.setVisibility(View.VISIBLE);

    }
}