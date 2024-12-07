package pyq.qbank.bluearrow;

import static pyq.qbank.bluearrow.MyApplication.boxStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class customModule extends AppCompatActivity {
    Button generateCustomModule;
    RecyclerView customModuleRecylerView;
    customModule_Adapter customModuleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_module);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        generateCustomModule = findViewById(R.id.generateCustomModule);
        generateCustomModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customModule.this, customModuleSelection.class);
                startActivity(intent);
                finish();
            }
        });

        customModuleRecylerView = findViewById(R.id.customModuleRecylerView);
        customModuleRecylerView.setLayoutManager(new LinearLayoutManager(this));

        customModuleAdapter = new customModule_Adapter(getCustomModuleList());
        customModuleRecylerView.setAdapter(customModuleAdapter);

        hideUI();






    }

    private void hideUI() {

        getSupportActionBar().hide();

        // Set the flags to hide both the status bar and navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN // Hide status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide navigation bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // Keep the UI hidden even after user interaction
        decorView.setSystemUiVisibility(uiOptions);
    }


    private List<entityCustomModule> getCustomModuleList() {
        Box<entityCustomModule> customModuleBox = boxStore.boxFor(entityCustomModule.class);
        List<entityCustomModule> customModuleList = customModuleBox.query().build().find();
        return customModuleList;

    }
}