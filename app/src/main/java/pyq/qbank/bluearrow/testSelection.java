package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import io.objectbox.Box;
import io.objectbox.query.PropertyQuery;
import io.objectbox.query.Query;

public class testSelection extends AppCompatActivity {

    RecyclerView recyclerView;
    testModelAdapter adapter;
    List<test_details> testList;
    Spinner spin1, spin2;
    String typeOfTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Getting data from the intent
        Intent red = getIntent();
        typeOfTest = red.getStringExtra("typeOfTest");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_selection);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.test_selection_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hideSystemUI();

        //Setting up the recycler view for loading the tests----------------------------------------
        recyclerView = findViewById(R.id.test_loader_Relative_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ///loading data into the recycler view
        testList = getTestDetails(typeOfTest);
        adapter = new testModelAdapter(testList, typeOfTest);
        recyclerView.setAdapter(adapter);

        //setting up the spinners-------------------------------------------------------------------
        spin1 = findViewById(R.id.year_spinner);
        spin2 = findViewById(R.id.subject_spinner);

        // Setting years for filtering for the spinners
        List<String> years = getTestYears(typeOfTest);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(yearAdapter);

        // Set up the subject spinner with the subjects list

        // Handling spinner selection changes
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedYear = spin1.getSelectedItem().toString();
//                String selectedSubject = spin2.getSelectedItem().toString();
                adapter.spinnerFilter(selectedYear, "All");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


    }

    private List<String> getTestYears(String typeOfTest) {
        Box<test_details> testBox = boxStore.boxFor(test_details.class);

        // Total record count
        long count = testBox.count();
        Log.d("Data Check", "Total test_details count: " + count);

        // Inspect all records
        List<test_details> allDetails = testBox.getAll();
        for (test_details detail : allDetails) {
            Log.d("Data Check", "testType: " + detail.getTest_type() + ", startDatetime: " + detail.getStart_datetime());
        }

        // Verify matching testType
        Log.d("Data Check", "typeOfTest being queried: " + typeOfTest);

        // Base query to check matching records
        Query<test_details> baseQuery = testBox.query(test_details_.test_type.equal(typeOfTest)).build();
        List<test_details> results = baseQuery.find();
        Log.d("Query Check", "Number of matching records: " + results.size());

        if (results.isEmpty()) {
            Log.d("Query Check", "No records found for typeOfTest: " + typeOfTest);
            return null;
        }

        // Distinct query on startDatetime
        PropertyQuery propertyQuery = baseQuery.property(test_details_.start_datetime).distinct();
        long[] distinctStartDatetimes = propertyQuery.findLongs();
        List<String> years = new ArrayList<>();
        for (long time : distinctStartDatetimes) {
            if (!years.contains(adapter.getYearString(time))) {
                years.add(adapter.getYearString(time));
            }


        }
        return years;
    }


    private List<test_details> getTestDetails(String typeOfTest) {
        Box<test_details> testBox = boxStore.boxFor(test_details.class);

        Query pq = testBox.query(test_details_.test_type.equal(typeOfTest)).order(test_details_.start_datetime).build();
        List<test_details> red = pq.find();


        return red;
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