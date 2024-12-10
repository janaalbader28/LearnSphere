package com.example.learnsphere2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.Classes.User;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameBack;
    private EditText edFullName, edEmail, edBirthday, edUserName, edPassword;
    private Spinner spSkill;
    private Button btnSignUp;
    private DatePickerDialog datePickerDialog;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findView();
        showDatePickerDialog();
        setAdapterSpinnerSkill();
    }

    private void findView() {

        db = new DataBase(this);
        frameBack = findViewById(R.id.frameBack);
        edFullName = findViewById(R.id.edFullName);
        edEmail = findViewById(R.id.edEmail);
        edBirthday = findViewById(R.id.edBirthday);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        spSkill = findViewById(R.id.spSkill);
        btnSignUp = findViewById(R.id.btnSignUp);

        frameBack.setOnClickListener(view -> {
            finish();
        });
        edBirthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.show();
                edBirthday.clearFocus();
            }
        });

        btnSignUp.setOnClickListener(view -> {

            db.open();

            String fullName = edFullName.getText().toString();
            String email = edEmail.getText().toString();
            String birthday = edBirthday.getText().toString();
            String userName = edUserName.getText().toString();
            String password = edPassword.getText().toString();
            String skill = spSkill.getSelectedItem().toString();

            if (fullName.isEmpty() || email.isEmpty() || birthday.isEmpty() || userName.isEmpty() || password.isEmpty() || skill.equals("Select your skill level")) {
                Toast.makeText(RegisterActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password Must be at Least 8 Characters Long", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean checkUserName = db.checkUserName(userName);
                if (!checkUserName) {
                    User user = new User(fullName, email, birthday, userName, skill);

                    boolean insert = db.insertUSER(user, password);
                    if (insert) {
                        Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "This User Name Already Exists..", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(RegisterActivity.this, (datePicker, year1, month1, day1) -> {
            String selectedDate = (month1 + 1) + "/" + day1 + "/" + year1;
            edBirthday.setText(selectedDate);
        }, year, month, day);
    }


    private void setAdapterSpinnerSkill() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.skill_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSkill.setAdapter(adapter);
    }}