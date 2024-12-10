package com.example.learnsphere2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.Admin.AdminMainActivity;
import com.example.learnsphere2.User.UserMainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserName, edPassword;
    private Button btnLogin, btnSignUp;
    private TextView ForgetPass;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findView();}

    private void findView() {

        db = new DataBase(this);

        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        ForgetPass = findViewById(R.id.tvForgetPass);

        btnLogin.setOnClickListener(view -> {

            db.open();

            String userName = edUserName.getText().toString();
            String password = edPassword.getText().toString();
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValidLogin = db.loginUser(userName, password);
                if (userName.equals("admin") & password.equals("12345678")) {
                    Toast.makeText(LoginActivity.this, "Admin Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                    finish();
                } else {
                    if (isValidLogin) {
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(LoginActivity.this, UserMainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
                    }}}

        });

        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        ForgetPass.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });}}