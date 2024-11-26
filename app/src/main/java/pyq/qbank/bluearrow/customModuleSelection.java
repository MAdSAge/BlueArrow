package pyq.qbank.bluearrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class customModuleSelection extends AppCompatActivity implements customModuleSubjectsAdapter.transferSubjectData,customModuleSubjectsAdapter.loadChapterRecycler, customModuleTagsAdapter.transferTagData, customModuleChapterAdapter.transferChapterData {


    //UI elements
    RecyclerView tagRecyclerView, subjectRecyclerView, chapterRecyclerView;
    LinearLayout linearLayout, hiddenLayout;
    TextView textView,chapterRecylerTextView;
    Button nextButton,hiddenChapterAcceptButton;
    CheckBox checkboxAllMcqs, checkboxQbankMcqs, checkboxBookmarkedMcqs, checkboxTestMcqs, checkboxMistakeMcqs;

    //Adapters
    customModuleTagsAdapter tagAdapter;
    customModuleSubjectsAdapter subjectAdapter;
    customModuleChapterAdapter chapterAdapter;

    //Data passed to adapters
    List<String> tagsList, subjectList;

    //Data received from adapters
    List<String> recivedTagList, recivedSubjectList;
    Map<String,List<String>> receivedChapterList;
    String subjectNameForRecycler;
    List<String> checkedCheckboxes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.custom_module_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.customModuleRView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Initializing the UI Elements--------------------------------------------------------------
        textView = findViewById(R.id.tagTextView);
        linearLayout = findViewById(R.id.leftLinearLayout);
        hiddenLayout = findViewById(R.id.hiddenLinearLayout);
        tagRecyclerView = findViewById(R.id.tagRecylerView);
        subjectRecyclerView = findViewById(R.id.recyclerViewSubjects);
        nextButton = findViewById(R.id.customModuleNext);

        checkboxAllMcqs = findViewById(R.id.checkboxAllMcqs);
        checkboxQbankMcqs = findViewById(R.id.checkboxQbankMcqs);
        checkboxBookmarkedMcqs = findViewById(R.id.checkboxBookmarkedMcqs);
        checkboxTestMcqs = findViewById(R.id.checkboxTestMcqs);

        checkboxMistakeMcqs = findViewById(R.id.checkboxMistakeMcqs);
        hiddenChapterAcceptButton = findViewById(R.id.hiddenChapterAcceptButton);

        //Initializing the Received List-------------------------------------------------------------

        receivedChapterList = new HashMap<>();
        recivedTagList = new ArrayList<>();
        recivedSubjectList = new ArrayList<>();
        checkedCheckboxes = new ArrayList<>();


        //remove un-wanted Ui elements and initial state--------------------------------------------
        hideSystemUI();

        //Tag Recycler View Setup-------------------------------------------------------------------
        tagRecyclerView.setLayoutManager(new FlexboxLayoutManager(this));

        tagsList = new ArrayList<>();
        tagsList.add("All");
        tagsList.add("GRAND");
        tagsList.add("INICET");
        tagsList.add("MOK");
        tagsList.add("Images");
        tagsList.add("PYQ");

        tagAdapter = new customModuleTagsAdapter(tagsList, this);
        tagRecyclerView.setAdapter(tagAdapter);

        //subject view setup------------------------------------------------------------------------
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        subjectList = new ArrayList<>();
        subjectList.add("All");
        subjectList.add("Anatomy");
        subjectList.add("Physiology");
        subjectList.add("Biochemistry");
        subjectList.add("Pathology");
        subjectList.add("Microbiology");

        subjectAdapter = new customModuleSubjectsAdapter(subjectList, this, this);
        subjectRecyclerView.setAdapter(subjectAdapter);

        //Chapter Recycler View Setup---------------------------------------------------------------
        //this view is gone state default

        chapterRecylerTextView = findViewById(R.id.hiddenTextView);
        chapterRecyclerView = findViewById(R.id.hiddenChapterRecyclerView);
        chapterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding the button click functionality to start the test-----------------------------------
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the test
                getAllSelectedChoices();
            }
        });

        hiddenChapterAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hiddenLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

            }
        });

        ///interface





    }




    private void getAllSelectedChoices() {

        Map<String,List<String>> red = new HashMap<>();
        red.put("tags",recivedTagList);

        getChekBoxStringIfCheked();
        red.put("checkboxes",checkedCheckboxes);

        List<String> subjects = recivedSubjectList;

        Set<String> test = receivedChapterList.keySet();

        for (String t:test
             ) {
            if(subjects.contains(t)){
                subjects.remove(t);
            }

        }
        red.put("subjects",subjects);
        for (Map.Entry<String, List<String>> entry : receivedChapterList.entrySet()) {
            red.put(entry.getKey(), entry.getValue());
        }

        System.out.println(red);

        Intent intent = new Intent(customModuleSelection.this, qbank_mcq_solving.class);
        intent.putExtra("data",(Serializable) red);

        // TODO: generate a query string to pass the activity insted of data after completing the Data base set UP
        startActivity(intent);

        
        

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


    //Interfaces implementations--------------------------------------------------------------------
    //Receive the selected subjects
    @Override
    public void transferSubjects(List<String> red) {
        recivedSubjectList = red;
    }

    //Receive the data from the Tag Selected
    @Override
    public void transferTags(List<String> selectedTags) {
        recivedTagList = selectedTags;

    }

    //Receive the data : selected chapters
    @Override
    public void transferChapters(String red, List<String> data) {
        receivedChapterList.put(red,data);
//        chapterAdapter.notifyDataSetChanged();
    }

    //display the chapter selection layout on subject button click
    @Override
    public void loadChapterRecycler(String subjectName) {
        //load the chapter recycler view with data
        subjectNameForRecycler = subjectName;


        linearLayout.setVisibility(View.GONE);
        hiddenLayout.setVisibility(View.VISIBLE);
        chapterRecylerTextView.setText(subjectName);



        List<String> chapterList = new ArrayList<>();
        chapterList.add("anatomy");
        chapterList.add("Biocheistry");
        chapterList.add("redme");
        chapterList.add("weel spun");
        chapterList.add("sell");
        chapterList.add("road show");

        chapterAdapter = new customModuleChapterAdapter(chapterList, this, subjectNameForRecycler,receivedChapterList);
        chapterRecyclerView.setAdapter(chapterAdapter);

        chapterAdapter.notifyDataSetChanged();
    }


    private String randomString(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }
        String randomText = sb.toString();
        return randomText;
    }

    private void getChekBoxStringIfCheked(){

        if(checkboxAllMcqs.isChecked()){
            checkedCheckboxes.add("All");
        }
        if(checkboxQbankMcqs.isChecked()){
            checkedCheckboxes.add("Qbank");

        }
        if(checkboxBookmarkedMcqs.isChecked()) {
            checkedCheckboxes.add("Bookmarked");
        }
        if(checkboxTestMcqs.isChecked()){
            checkedCheckboxes.add("Test");
        }
        if(checkboxMistakeMcqs.isChecked()){
            checkedCheckboxes.add("Mistake");
        }


    }



}