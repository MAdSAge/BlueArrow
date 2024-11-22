package pyq.qbank.bluearrow;


import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;
import io.objectbox.query.PropertyQuery;

public class qbankSubjectSelection extends AppCompatActivity {

    private RecyclerView recyclerView;
    private subject_model_adapter adapter;
    private List<subject_model> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qbank_subject_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.subjectSelection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        hideSystemUI();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        topicList = getSubjects();

        adapter = new subject_model_adapter(topicList);
        recyclerView.setAdapter(adapter);
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

    private List<subject_model> getSubjects() {

        Box<mcq_model> McqBox = boxStore.boxFor(mcq_model.class);

        PropertyQuery select_distinct_subjects = McqBox.query().build().property(mcq_model_.subject);
        List<String> subjects = Arrays.asList(select_distinct_subjects.distinct().findStrings());
        System.out.println(subjects);




        // i have praticular order for these what should i do to order it CCORDIGN TO MY PREFANCE

        List<subject_model> red = new ArrayList<>();
        for(String subjectName : subjects){

            PropertyQuery get_chapter_count_in_Subject = McqBox
                    .query(mcq_model_.subject.equal(subjectName))
                    .build()
                    .property(mcq_model_.chapter);
            List<String> distinctChapters = Arrays.asList(get_chapter_count_in_Subject.distinct().findStrings());
            int chapterCount = distinctChapters.size();
            red.add(new subject_model(chapterCount,getsubjectURl(subjectName),subjectName,getSubjectOrder(subjectName)));

        }

        Collections.sort(red, (subject1, subject2) -> Integer.compare(getSubjectOrder(subject1.getSubjectName()), getSubjectOrder(subject2.getSubjectName())));


        return  red;
    }

    private int getSubjectOrder(String subjectName) {
        Box<iconOrderLoader> iconBox = boxStore.boxFor(iconOrderLoader.class);
        iconOrderLoader result = iconBox.query(iconOrderLoader_.subject.equal(subjectName))
                .build()
                .findFirst();
        return result.subject_order;



    }

    private String getsubjectURl(String subjectName) {

        Box<iconOrderLoader> iconBox = boxStore.boxFor(iconOrderLoader.class);

        iconOrderLoader result = iconBox.query(iconOrderLoader_.subject.equal(subjectName))
                .build()
                .findFirst();
        return result.getSubject_thumbnail();


    }
}