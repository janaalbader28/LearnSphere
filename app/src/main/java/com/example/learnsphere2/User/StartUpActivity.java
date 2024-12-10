package com.example.learnsphere2.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnsphere2.R;

public class StartUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        Button step1Link = findViewById(R.id.step1Link);
        Button step2Link = findViewById(R.id.step2Link);
        Button step3Link = findViewById(R.id.step3Link);
        Button step4Link = findViewById(R.id.step4Link);

        findViewById(R.id.back).setOnClickListener(v -> finish());

        step1Link.setOnClickListener(v -> openLink("https://visualstudio.microsoft.com/downloads/"));

        step2Link.setOnClickListener(v -> openLink("https://github.com/"));

        step3Link.setOnClickListener(v -> openLink("https://www.neuroworx.io/programming/"));

        step4Link.setOnClickListener(v -> openLink("https://roadmap.sh/"));}

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);}}