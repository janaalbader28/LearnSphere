
package com.example.learnsphere2.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;

import com.example.learnsphere2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactUsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.back).setOnClickListener(v -> finish());

        TextView phoneText = findViewById(R.id.textView21);
        TextView emailText = findViewById(R.id.textView67);
        TextView instaText = findViewById(R.id.textView69);

        ImageView phoneIcon = findViewById(R.id.phone);
        ImageView emailIcon = findViewById(R.id.useremail);
        ImageView instaIcon = findViewById(R.id.insta);

        phoneText.setOnClickListener(v -> dialPhoneNumber("+966 597267869"));
        phoneIcon.setOnClickListener(v -> dialPhoneNumber("+966 597267869"));

        emailText.setOnClickListener(v -> sendEmail("ContactUs@LearnSphere.com"));
        emailIcon.setOnClickListener(v -> sendEmail("ContactUs@LearnSphere.com"));

        instaText.setOnClickListener(v -> openInstagram("learnsphere.cs"));
        instaIcon.setOnClickListener(v -> openInstagram("learnsphere.cs"));


        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);}


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        LatLng location = new LatLng(26.3564625, 50.1774613);
        googleMap.addMarker(new MarkerOptions().position(location).title("LearnSphere"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

        gMap.setOnMapClickListener(latLng -> openGoogleMaps());
    }

    private void openGoogleMaps() {
        Uri locationUri = Uri.parse("https://www.google.com/maps/place/College+of+Computer+Science+and+Information+Technology+(Ladies+Section)/@26.3987669,50.0681594,12z/data=!4m20!1m13!4m12!1m4!2m2!1d50.1112348!2d26.4077024!4e1!1m6!1m2!1s0x3e49ef826c3c4529:0x126e95aa294da63c!2zQ29sbGVnZSBvZiBDb21wdXRlciBTY2llbmNlIGFuZCBJbmZvcm1hdGlvbiBUZWNobm9sb2d5IChMYWRpZXMgU2VjdGlvbiksINmC2LPZhSDYp9mE2LfYp9mE2KjYp9iq2Iwg2YXYqNmG2YkgNjUw2Iwg2YPZhNmK2Kkg2LnZhNmI2YUg2KfZhNit2KfYs9ioINmI2KrZgtmG2YrYqSDYp9mE2YXYudmE2YjZhdin2KrYjCA3NTUwIEtpbmcgRmFpc2FsIElibiBBYmQgQWwgQXppeiwgQWwgU2FmYSwgRGFtbWFtIDM0MjIx4oCt!2m2!1d50.1890136!2d26.3850426!3m5!1s0x3e49ef826c3c4529:0x126e95aa294da63c!8m2!3d26.3850426!4d50.1890136!16s%2Fg%2F11c5_8ym49?entry=ttu&g_ep=EgoyMDI0MTIwNC4wIKXMDSoASAFQAw%3D%3D");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void dialPhoneNumber(String phoneNumber) {
        try {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);}
         catch (Exception e) {
            Toast.makeText(this, "Unable to make a call", Toast.LENGTH_SHORT).show();}}


    private void sendEmail(String emailAddress) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
            startActivity(emailIntent);}
         catch (Exception e) {
            Toast.makeText(this, "Unable to send email", Toast.LENGTH_SHORT).show();
        }}

    private void openInstagram(String instagramHandle) {
        try {
            Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/" + instagramHandle));
            startActivity(instaIntent);}
        catch (Exception e) {
            Toast.makeText(this, "Unable to open Instagram", Toast.LENGTH_SHORT).show();}}}


