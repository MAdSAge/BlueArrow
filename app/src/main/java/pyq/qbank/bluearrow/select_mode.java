package pyq.qbank.bluearrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class select_mode extends AppCompatActivity {

    private MaterialCardView cardQBank, cardGrandTest, cardMiniTest,
            cardSubjectTest, cardBookmarks, cardCustomModules;

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

        //tagging the Id of cards--------------------------------------------------------------
        cardQBank = findViewById(R.id.goToQbank);
        cardGrandTest = findViewById(R.id.cardGrandTest);
        cardMiniTest = findViewById(R.id.cardMiniTest);
        cardSubjectTest = findViewById(R.id.cardSubjectTest);
        cardCustomModules = findViewById(R.id.cardCustomModules);


        //Qbank card route
        cardQBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_mode.this, qbankSubjectSelection.class);
                startActivity(intent);

            }
        });

        //Grand Test card route
        cardGrandTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_mode.this, testSelection.class);
                startActivity(intent);}
        });

        //Custom Module card route
        cardCustomModules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_mode.this, customModuleSelection.class);
                startActivity(intent);}});

        //Mini Test card route
        cardMiniTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_mode.this, testSelection.class);
                startActivity(intent);}});

        //Subject Test card route
        cardSubjectTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(select_mode.this, testSelection.class);
                startActivity(intent);}});







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