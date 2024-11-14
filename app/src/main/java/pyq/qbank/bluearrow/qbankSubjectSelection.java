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


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        topicList = new ArrayList<>();
        topicList.add(new subject_model(45, R.drawable.creativity, "maths"));
        topicList.add(new subject_model(23,R.drawable.test,"physics"));

        adapter = new subject_model_adapter(topicList);
        recyclerView.setAdapter(adapter);
    }
}