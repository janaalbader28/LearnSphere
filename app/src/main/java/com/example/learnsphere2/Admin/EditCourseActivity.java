package com.example.learnsphere2.Admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learnsphere2.DB.DataBase;
import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class EditCourseActivity extends AppCompatActivity {

    private FrameLayout frameBack;
    private ImageView imgCourse;
    private EditText edCourseTitle, edCourseInfo, edCourseDate, edCourseUrl;
    private Spinner spLocation;
    private Button btnEditCourse;

    private byte[] img_Course = null;
    private String location;
    private static final int PICK_IMAGE = 1888;
    private DatePickerDialog datePickerDialog;
    private DataBase db;
    private Course dataCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;});

        findView();
        showDatePickerDialog();
        setAdapterLocation();
        setDataCourse();}

    private void findView() {
        db = new DataBase(this);
        dataCourse = (Course) getIntent().getSerializableExtra("dataCourse");

        frameBack = findViewById(R.id.frameBack);
        imgCourse = findViewById(R.id.imgCourse);
        edCourseTitle = findViewById(R.id.edCourseTitle);
        edCourseInfo = findViewById(R.id.edCourseInfo);
        edCourseDate = findViewById(R.id.edCourseDate);
        edCourseUrl = findViewById(R.id.edCourseUrl);
        spLocation = findViewById(R.id.spLocation);
        btnEditCourse = findViewById(R.id.btnEditCourse);

        frameBack.setOnClickListener(view -> finish());

        imgCourse.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE);});

        edCourseDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                datePickerDialog.show();
                edCourseDate.clearFocus();}});

        btnEditCourse.setOnClickListener(view -> {
            String courseTitle = edCourseTitle.getText().toString();
            String courseInfo = edCourseInfo.getText().toString();
            String courseDate = edCourseDate.getText().toString();
            String courseUrl = edCourseUrl.getText().toString();
            location = spLocation.getSelectedItem().toString();

            if (courseTitle.isEmpty() || courseInfo.isEmpty() || courseDate.isEmpty() || courseUrl.isEmpty()) {
                Toast.makeText(EditCourseActivity.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
            } else {
                Course data = new Course(img_Course, courseTitle, courseInfo, courseDate, courseUrl, location);
                new UpdateCourseTask().execute(data);}});}

    private void setDataCourse() {
        location = dataCourse.getLocation();
        img_Course = dataCourse.getImgCourse();

        Bitmap bitmapImgCourse = BitmapFactory.decodeByteArray(dataCourse.getImgCourse(), 0, dataCourse.getImgCourse().length);
        imgCourse.setImageBitmap(bitmapImgCourse);

        edCourseTitle.setText(dataCourse.getCourseTitle());
        edCourseInfo.setText(dataCourse.getCourseInfo());
        edCourseDate.setText(dataCourse.getCourseDate());
        edCourseUrl.setText(dataCourse.getCourseUrl());}

    private void setAdapterLocation() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.location,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(dataCourse.getLocation());
        spLocation.setSelection(spinnerPosition);}

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(EditCourseActivity.this, (datePicker, year1, month1, day1) -> {
            String selectedDate = (month1 + 1) + "/" + day1 + "/" + year1;
            edCourseDate.setText(selectedDate);
        }, year, month, day);}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            imgCourse.setImageURI(imageUri);
            InputStream iStream;
            try {
                iStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);}
            try {
                img_Course = getBytes(iStream);
            } catch (IOException e) {
                throw new RuntimeException(e);}}
        super.onActivityResult(requestCode, resultCode, data);}

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);}
        return byteBuffer.toByteArray();}

    private class UpdateCourseTask extends AsyncTask<Course, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();}

        @Override
        protected Boolean doInBackground(Course... courses) {
            db.open();
            Course data = courses[0];
            boolean isUpdatedCourse = db.updateCourse(data, dataCourse.getId() + "");
            db.close();
            return isUpdatedCourse;}

        @Override
        protected void onPostExecute(Boolean isUpdatedCourse) {
            super.onPostExecute(isUpdatedCourse);
            if (isUpdatedCourse) {
                Toast.makeText(EditCourseActivity.this, "The course is updated successfully", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();}
            else {
                Toast.makeText(EditCourseActivity.this, "Error Edit Course!!", Toast.LENGTH_SHORT).show();}}}}
