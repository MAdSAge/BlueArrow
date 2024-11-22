package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class qbankChapterSelection extends AppCompatActivity {


    private RecyclerView recyclerView;
    private chapter_model_adapter adapter;
    private List<chapter_model> topicList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qbank_chapter_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chapterSelection), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hideSystemUI();

        Intent intent = getIntent();
        String SubjectName = intent.getStringExtra("subjectName");



        recyclerView = findViewById(R.id.chapterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        topicList = loadChapters(SubjectName);


        adapter = new chapter_model_adapter(topicList);
        recyclerView.setAdapter(adapter);




    }

    private List<chapter_model> loadChapters(String subjectName) {

        Box<iconOrderLoader> iBox = boxStore.boxFor(iconOrderLoader.class);
        List<iconOrderLoader> iconsWithSubject = iBox.query(iconOrderLoader_.subject.equal(subjectName))
                .build()
                .find();

        List<chapter_model> pink = new ArrayList<>();

        for(iconOrderLoader icon : iconsWithSubject){

            pink.add(new chapter_model(icon.getChapter(),getQuestionsInChapter(icon.getChapter()),0,icon.lesson));


        }
        return pink;
    }

    private int getQuestionsInChapter(String x){
        Box<mcq_model> mcqBox = boxStore.boxFor(mcq_model.class);
        List<mcq_model> red = mcqBox.query(mcq_model_.chapter.equal(x))
                .build()
                .find();
        return red.size();
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