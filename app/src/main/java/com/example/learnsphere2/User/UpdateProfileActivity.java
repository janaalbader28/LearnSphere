package com.example.learnsphere2.User;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.learnsphere2.R;

import java.util.Calendar;

public class UpdateProfileActivity extends AppCompatActivity {

    private FrameLayout frameBack;
    private EditText edFullName, edEmail, edBirthday, edUserName;
    private Spinner spSkill;
    private Button btnSave, cancel;
    private String skill , user_name;
    private int idUser;
    private DataBase db;
    private SharedPreferences sharedPreferences;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});
        findView();
        showDatePickerDialog();
        loadData();
        setAdapterSkill();
    }

    private void findView() {

        db = new DataBase(this);
        sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getInt("id_user", -1);
        user_name = sharedPreferences.getString("user_name", "");

        frameBack = findViewById(R.id.frameBack);
        edFullName = findViewById(R.id.name);
        edEmail = findViewById(R.id.useremail);
        edBirthday = findViewById(R.id.birthday);
        edUserName = findViewById(R.id.username);
        spSkill = findViewById(R.id.skill_spinner);
        btnSave = findViewById(R.id.Save);
        cancel = findViewById(R.id.Cancle);

        edBirthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.show();
                edBirthday.clearFocus();
            }
        });
        btnSave.setOnClickListener(view -> {
            db.open();
            String fullName = edFullName.getText().toString();
            String email = edEmail.getText().toString();
            String birthday = edBirthday.getText().toString();
            String userName = edUserName.getText().toString();
            skill = spSkill.getSelectedItem().toString();

            if (fullName.isEmpty() || email.isEmpty() || birthday.isEmpty() || userName.isEmpty()) {
                Toast.makeText(UpdateProfileActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(UpdateProfileActivity.this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User(fullName, email, birthday, userName, skill);

                if (!user_name.equals(userName)){
                    boolean checkUserName = db.checkUserName(userName);
                    if (!checkUserName) {
                        boolean insert = db.updateUser(user, String.valueOf(idUser));
                        if (insert) {
                            Toast.makeText(UpdateProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                            writeOnPreferenceUserName(userName);}
                         else {
                            Toast.makeText(UpdateProfileActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();}}
                     else {
                        Toast.makeText(UpdateProfileActivity.this, "This User Name Already Exists..", Toast.LENGTH_SHORT).show();}}
                else {
                    boolean insert = db.updateUser(user, String.valueOf(idUser));
                    if (insert) {
                        Toast.makeText(UpdateProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                        writeOnPreferenceUserName(userName);}
                     else {
                        Toast.makeText(UpdateProfileActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();
                    }}}});
        cancel.setOnClickListener(v -> {
            finish();});
        frameBack.setOnClickListener(v -> {
            finish();});}

    private void loadData() {
        db.open();
        User user = db.getUserData(idUser + "");
        if (user != null) {
            edFullName.setText(user.getFullName());
            edEmail.setText(user.getEmail());
            edBirthday.setText(user.getBirthday());
            edUserName.setText(user.getUserName());
            skill = user.getSkill();}

        else {
            Toast.makeText(this, "Failed to load user data!", Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(false);}}

    private void setAdapterSkill() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.skill_levels,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSkill.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(skill);
        spSkill.setSelection(spinnerPosition);}

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this, (datePicker, year1, month1, day1) -> {
            String selectedDate = (month1 + 1) + "/" + day1 + "/" + year1;
            edBirthday.setText(selectedDate);
        }, year, month, day);}
    private void writeOnPreferenceUserName(String UserName) {
        sharedPreferences.edit()
                .putString("user_name", UserName)
                .apply();}}
