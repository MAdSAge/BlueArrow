package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import io.objectbox.Box;

public class CustomModuleStart extends AppCompatActivity {

    Box<entityCustomModule> customModuleBox;
    entityCustomModule cm;
    List<entityMcq> Filterd_mcqs;
    TextView mainFilter,customModuleName, mcqCount, mcqSubjects, mcqChapters, mcqTags;
    Button mcqRegular, mcqTest;
    ImageView qrCode;
    ImageButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_module_solving);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        customModuleBox = boxStore.boxFor(entityCustomModule.class);
        Intent intent = getIntent();
        long id = intent.getLongExtra("Id", -1);
        cm = customModuleBox.get(id);
        intilizieUI();
        customModuleFilter filter = new customModuleFilter(cm);
        Filterd_mcqs = filter.getAllMCQS(cm.getQuestionCount());
        Box<entityCustomModuleMcq> customModuleMcqBox = boxStore.boxFor(entityCustomModuleMcq.class);
        for (entityMcq ent:Filterd_mcqs
             ) {
            customModuleMcqBox.put(new entityCustomModuleMcq(ent,cm.getCustomModuleName()));
        }


    }

    private void intilizieUI() {
        hideSystemUI();
        mainFilter = findViewById(R.id.mainFilter);
        customModuleName = findViewById(R.id.customModuleName);
        mcqCount = findViewById(R.id.mcqCount);
        mcqSubjects = findViewById(R.id.mcqSubjects);
        mcqChapters = findViewById(R.id.mcqChapters);
        mcqTags = findViewById(R.id.mcqTags);
        mcqRegular = findViewById(R.id.mcqRegular);
        mcqTest = findViewById(R.id.mcqTest);
        qrCode = findViewById(R.id.qrCode);
        shareButton = findViewById(R.id.shareButton);
        fillDataIntoUI();
        addClickListners();

    }

    private void addClickListners() {
        mcqRegular.setOnClickListener(v -> {
            Intent intent = new Intent(this, customModuleSolveActivity.class);
            intent.putExtra("customModuleName", cm.getCustomModuleName());
            intent.putExtra("ReviewMode", false);
            startActivity(intent);
            finish();

        });
    }

    private void fillDataIntoUI() {
        mainFilter.setText(cm.selectionFilter);
        customModuleName.setText(cm.getCustomModuleName());
        mcqCount.setText("MCQs: " + cm.getQuestionCount());
        mcqSubjects.setText("Subjects: " + cm.getSubjectList().size());
        mcqChapters.setText("Chapters: " + cm.getChapterList().size());
        mcqTags.setText("Tags: " + cm.getTagList().size());

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