package pyq.qbank.bluearrow;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class customModuleSelection extends AppCompatActivity implements customModuleTagsAdapter.OnChildItemClickListener{

    RecyclerView tagRecyclerView,subjectRecyclerView;
    tagsRVAdapter tagAdapter;
    customModuleTagsAdapter subjectAdapter;
    List<String> tagsList,subjectList;
    LinearLayout linearLayout;
    private FragmentTransaction fragmentTransaction;
    TextView textView;



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

        textView = findViewById(R.id.tagTextView);




        hideSystemUI();
        //tag view setup----------------------------------------------------------------------------

        tagRecyclerView = findViewById(R.id.tagRecylerView);
        tagRecyclerView.setLayoutManager(new FlexboxLayoutManager(this));

        tagsList = new ArrayList<String>();
        tagsList.add("All");
        tagsList.add("GRAND");
        tagsList.add("INICET");
        tagsList.add("MOK");
        tagsList.add("Images");
        tagsList.add("PYQ");
        tagAdapter = new tagsRVAdapter(tagsList);
        tagRecyclerView.setAdapter(tagAdapter);

        //subject view setup------------------------------------------------------------------------
        subjectRecyclerView = findViewById(R.id.recyclerViewSubjects);
        subjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        subjectList = new ArrayList<String>();
        subjectList.add("All");
        subjectList.add("Anatomy");
        subjectList.add("Physiology");
        subjectList.add("Biochemistry");
        subjectList.add("Pathology");
        subjectList.add("Microbiology");

        subjectAdapter = new customModuleTagsAdapter(subjectList,this);
        subjectRecyclerView.setAdapter(subjectAdapter);
        //


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

    @Override
    public void onItemClicked(String data) {
        Toast.makeText(this, "if not7 working temp gone: " + data, Toast.LENGTH_SHORT).show();


        textView.setVisibility(View.GONE);
        Toast.makeText(this, "if not working temp gone: " + data, Toast.LENGTH_SHORT).show();

        if (textView != null) {
            textView.setVisibility(View.GONE);
            Toast.makeText(this, "its temp gone: " + data, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "its not gone: " + data, Toast.LENGTH_SHORT).show();

        }

//        linearLayout.setVisibility(View.GONE);


    }
}