package com.example.learnsphere2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnsphere2.DB.DataBase;

import java.util.UUID;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FrameLayout frameBack;
    private EditText edEmail;
    private Button btnSendPass;

    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findView();}

    private void findView() {

        db = new DataBase(this);

        frameBack = findViewById(R.id.frameBack);
        edEmail = findViewById(R.id.edEmail);
        btnSendPass = findViewById(R.id.btnSendPass);

        frameBack.setOnClickListener(view -> {
            finish();
        });

        btnSendPass.setOnClickListener(view -> {
            db.open();

            String email = edEmail.getText().toString().trim();
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkEmailExists(email)) {
                String newPassword = generateNewPassword();
                if (db.updatePassword(email, newPassword)) {
                    sendEmail(email, newPassword);
                    Toast.makeText(ForgotPasswordActivity.this, "New password has been sent to your email", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
            }
        });}

    private String generateNewPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void sendEmail(String recipientEmail, String newPassword) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your New Password");
        intent.putExtra(Intent.EXTRA_TEXT, "Your New Password is: " + newPassword);
        try {
            startActivity(Intent.createChooser(intent, "Send Email With..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No Email Clients Installed.", Toast.LENGTH_SHORT).show();
        }}}