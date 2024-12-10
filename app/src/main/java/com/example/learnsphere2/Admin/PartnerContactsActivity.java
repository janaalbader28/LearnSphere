package com.example.learnsphere2.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnsphere2.R;

public class PartnerContactsActivity extends AppCompatActivity {

    private FrameLayout frameBack ;
    private CardView cardCourera, cardSdaia, cardTiwaiq, cardAudacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_partner_contacts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findView();}

    private void findView() {

        frameBack = findViewById(R.id.frameBack);
        cardCourera = findViewById(R.id.cardCourera);
        cardSdaia = findViewById(R.id.cardSdaia);
        cardTiwaiq = findViewById(R.id.cardTiwaiq);
        cardAudacity = findViewById(R.id.cardAudacity);

        frameBack.setOnClickListener(v -> finish());

        cardCourera.setOnClickListener(v -> openCoureraContact());

        cardSdaia.setOnClickListener(v -> openSdaiaContact());

        cardTiwaiq.setOnClickListener(v -> openTiwaiqContact());

        cardAudacity.setOnClickListener(v -> openAudacityContact());}

    private void openCoureraContact() {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://coursera.com"));
        startActivity(websiteIntent);
         Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+1234567890"));
         startActivity(phoneIntent);

         Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:contact@coursera.com"));
         startActivity(emailIntent);
    }

    private void openSdaiaContact() {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sdaia.gov.sa"));
        startActivity(websiteIntent);

         Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+966112233445"));
         startActivity(phoneIntent);

         Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:contact@sdaia.gov.sa"));
         startActivity(emailIntent);
    }

    private void openTiwaiqContact() {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tuwaiq.edu.sa"));
        startActivity(websiteIntent);

         Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+966234567890"));
         startActivity(phoneIntent);

         Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:contact@tuwaiq.sa"));
         startActivity(emailIntent);
    }

    private void openAudacityContact() {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://audacityteam.org"));
        startActivity(websiteIntent);

         Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+1-800-555-5555"));
         startActivity(phoneIntent);

         Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@audacityteam.org"));
         startActivity(emailIntent);
    }

}
