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

import java.util.List;

import io.objectbox.Box;

public class qbankSubjectSelection extends AppCompatActivity {

    private RecyclerView recyclerView;
    private qbankSubject_model_adapter adapter;
    private List<entitySubject> topicList;

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

        adapter = new qbankSubject_model_adapter(topicList);
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

    private List<entitySubject> getSubjects() {

        Box<entitySubject> McqBox = boxStore.boxFor(entitySubject.class);

        List<entitySubject> red = McqBox.query().order(entitySubject_.order).build().find();

        return  red;
    }





}