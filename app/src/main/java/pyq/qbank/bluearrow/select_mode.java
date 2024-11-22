package pyq.qbank.bluearrow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.objectbox.Box;

public class select_mode extends AppCompatActivity {

    private MaterialCardView cardQBank, cardGrandTest, cardMiniTest,
            cardSubjectTest, cardBookmarks, cardCustomModules;
    private Box<mcq_model> mcqBox;
    private ProgressBar progressBar; // ProgressBar for feedback during data load

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_mode);

        // Setup edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hideSystemUI();

        // Initializing UI components
        cardQBank = findViewById(R.id.goToQbank);
        cardGrandTest = findViewById(R.id.cardGrandTest);
        cardMiniTest = findViewById(R.id.cardMiniTest);
        cardSubjectTest = findViewById(R.id.cardSubjectTest);
        cardCustomModules = findViewById(R.id.cardCustomModules);
        cardBookmarks = findViewById(R.id.cardBookmarks);
        progressBar = findViewById(R.id.progressBar); // Assuming you have a ProgressBar in your layout

        // Card routes setup
        cardQBank.setOnClickListener(v -> startActivity(new Intent(select_mode.this, qbankSubjectSelection.class)));

        cardGrandTest.setOnClickListener(v->{

            Intent red = new Intent(select_mode.this, testSelection.class);
            red.putExtra("typeOfTest","grand");
            startActivity(red);

        });

        cardMiniTest.setOnClickListener(v -> startActivity(new Intent(select_mode.this, testSelection.class)));
        cardSubjectTest.setOnClickListener(v -> startActivity(new Intent(select_mode.this, testSelection.class)));
        cardBookmarks.setOnClickListener(v -> startActivity(new Intent(select_mode.this, bookmarkSelection.class)));

        // Load data (check and import if not already done)
        loadDataObjectBox(this);
    }

    private void loadDataObjectBox(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        boolean isDataImported = preferences.getBoolean("isDataImported", false); // Default to false if not set

        // If the data is not imported, proceed with data import in the background
        if (!isDataImported) {
            showLoading(true);  // Show ProgressBar while loading data

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    MyApplication app = (MyApplication) getApplicationContext();
                    DataImporter dataImporter = new DataImporter(app.getBoxStore());
                    dataImporter.importData(this); // This performs the background import

                    // After importing, set flag and notify user
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isDataImported", true);
                    editor.apply();

                    runOnUiThread(() -> {
                        showLoading(false); // Hide ProgressBar
                        Toast.makeText(context, "Data imported successfully", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        showLoading(false); // Hide ProgressBar
                        Toast.makeText(context, "Error importing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });
        }
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE); // Show ProgressBar
        } else {
            progressBar.setVisibility(View.GONE);  // Hide ProgressBar
        }
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
