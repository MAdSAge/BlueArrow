package pyq.qbank.bluearrow;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

        recyclerView = findViewById(R.id.chapterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        topicList = new ArrayList<>();

        topicList.add(new chapter_model("Chapter 1",10,9));

        topicList.add(new chapter_model("Chapter 2",20,5));


        adapter = new chapter_model_adapter(topicList);
        recyclerView.setAdapter(adapter);




    }
}