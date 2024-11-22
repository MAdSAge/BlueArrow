package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;
import io.objectbox.query.PropertyQuery;
import io.objectbox.query.Query;

public class testSelection extends AppCompatActivity {

    RecyclerView recyclerView;
    testModelAdapter adapter;
    List<testModel> testList;
    Spinner spin1, spin2;
    String typeOfTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_selection);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.test_selection_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hideSystemUI();



        recyclerView = findViewById(R.id.test_loader_Relative_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent red = getIntent();
        typeOfTest = red.getStringExtra("typeOfTest");



        testList = getTestDetails(typeOfTest);



        // Get your data from wherever you store it
// Get your data from wherever you store it
        adapter = new testModelAdapter(testList);
        recyclerView.setAdapter(adapter);

        //setting up the spinners-------------------------------------------------------------
        spin1 = findViewById(R.id.year_spinner);
        spin2 = findViewById(R.id.subject_spinner);

        // Example data for Spinners
        List<String> years = new ArrayList<>();
        years.add("All");
        years.add("2017");
        years.add("2018");
        years.add("2019");
        years.add("2020");
        years.add("2021");
        years.add("2022");
        years.add("2023");
        years.add("2024");

        List<String> subjects = new ArrayList<>();
        subjects.add("All");
        subjects.add("GRAND");
        subjects.add("INICET");
        subjects.add("MOK");


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(yearAdapter);

// Set up the subject spinner with the subjects list
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(subjectAdapter);

        // Handling spinner selection changes
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedYear = spin1.getSelectedItem().toString();
                String selectedSubject = spin2.getSelectedItem().toString();
                adapter.spinnerFilter(selectedYear, selectedSubject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedYear = spin1.getSelectedItem().toString();
                String selectedSubject = spin2.getSelectedItem().toString();
                adapter.spinnerFilter(selectedYear, selectedSubject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private List<testModel> getTestDetails(String typeOfTest) {
         Box<test_model> testBox = boxStore.boxFor(test_model.class);

         PropertyQuery pq = testBox.query(test_model_.subject.equal(typeOfTest)).build().property(test_model_.chapter_id);
         String[] red = pq.distinct().findStrings();

        List<testModel> testM = new ArrayList<>();

         for(String r:red){
             //GET TEH TEST MODEL FROM TEXT WITH CHAPTER_ID R
             Query<test_model> q = testBox.query(test_model_.chapter_id.equal(r)).build();

             testM.add(new testModel(q.findFirst().chapter,q.findFirst().time,1,typeOfTest));


         }


        return testM;
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