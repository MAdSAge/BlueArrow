package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class qbankChapterSelection extends AppCompatActivity {


    Box<entityChapter> chapterBox;
    private RecyclerView recyclerView;
    private qbnakChapter_model_adapter adapter;
    private List<entityChapter> topicList;
    private TabLayout tabLayout;
    private String SubjectName;
    private Spinner topicSpinner;
    private NestedScrollView ns;
    private ProgressBar ps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //intial UI set up--------------------------------------------------------------------------
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qbank_chapter_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chapterSelection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hideSystemUI();
        //Initializing teh order box-----------------------------------------------------------------
        chapterBox = boxStore.boxFor(entityChapter.class);

        ns = findViewById(R.id.chapterSelection);

        //Getting data from the intent--------------------------------------------------------------
        Intent intent = getIntent();
        SubjectName = intent.getStringExtra("subjectName");

        topicSpinner = findViewById(R.id.topicSpinner);

        //poress bar set up-------------------------------------------------------------------------
        ps = findViewById(R.id.scrollProgress);



        //Setting up the Tabs-----------------------------------------------------------------------
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setText("Unattempted"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adapter.setTopicList(getLessonsForTab(SubjectName, tab.getPosition()));
                adapter.notifyDataSetChanged();
                loadDataIntoSpinners();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //setting up the spinners-------------------------------------------------------------------





        //setting up the recycler views-------------------------------------------------------------
        recyclerView = findViewById(R.id.chapterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //loading data into the recycler views
        topicList = getLessonsForTab(SubjectName, 0);
        adapter = new qbnakChapter_model_adapter(topicList);
        recyclerView.setAdapter(adapter);

        loadDataIntoSpinners();

        ps.setMax(100);
        ns.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Get the total scrollable height and the current scroll position
                int scrollHeight = v.getChildAt(0).getHeight() - v.getHeight();
                int progress = (int) (((float) scrollY / scrollHeight) * ps.getMax());

                // Update the progress of the ProgressBar
                ps.setProgress(progress);
            }
        });


    }

    private void loadDataIntoSpinners() {
        // Get distinct lessons for the selected subject
        List<String> topics = adapter.getTopicList().stream()
                .map(entityChapter::getLesson)  // Extract lesson from each topic
                .distinct()                     // Ensure uniqueness
                .collect(Collectors.toList());  // Collect the results into a list

        // Set up the spinner adapter
        ArrayAdapter<String> topicsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topics);
        topicsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topicSpinner.setAdapter(topicsAdapter);

        // Set a listener for spinner item selection
        topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTopic = (String) parent.getItemAtPosition(position);

                // Scroll the RecyclerView to the position of the selected topic
                scrollTheNestedScrollViewToTopicPosition(selectedTopic);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected (optional)
            }
        });
    }

    private void scrollTheNestedScrollViewToTopicPosition(String selectedTopic) {
        // Find the position of the selected topic in the topicList
        int p = -1;
        List<entityChapter> currentList = adapter.getTopicList();

        for (int i = 0; i < currentList.size(); i++) {
            String currentTopic = currentList.get(i).getLesson();
            // Compare using .equals() to check if the topics are the same
            if (currentTopic.equals(selectedTopic)) {
                p = i;
                break;
            }
        }

        // If the topic is found, scroll to that position in the NestedScrollView
        if (p != -1) {
            // Get the view corresponding to the selected topic (assuming it's the view in the RecyclerView)
            View selectedTopicView = recyclerView.getLayoutManager().findViewByPosition(p);

            if (selectedTopicView != null) {
                // Scroll the NestedScrollView to the top of the selected topic view
                ns.smoothScrollTo(0, selectedTopicView.getTop());
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

    private List<entityChapter> getLessonsForTab(String subjectName, int position) {
        // Implement logic to retrieve the appropriate lesson data for the selected tab
        switch (position) {
            case 0:
                return loadChapters(subjectName, position);
            case 1:
                return loadChapters(subjectName, position);
            case 2:
                return loadChapters(subjectName, position);
            default:
                return new ArrayList<>();
        }
    }


    private List<entityChapter> loadChapters(String subjectName, int tabPosition) {

        Query<entityChapter> orderList = chapterBox.query(entityChapter_.subject.equal(subjectName)).build();

        switch (tabPosition) {
            case 0:
                return orderList.find();
            case 1:
                return orderList.find().stream().filter(entityChapter::isChapter_completed).collect(Collectors.toList());
            case 2:
                return orderList.find().stream().filter(entityChapter -> !entityChapter.isChapter_completed()).collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }

    }


}