package com.example.learnsphere2.DB;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.Classes.User;

import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper {
    private SQLiteDatabase database;
    private SharedPreferences sharedPreferences;
    private static final String CREATE_TABLE_USER =
            "create table " + Constants.TABLE_USER + " (" +
                    Constants.COL_ID + " integer primary key autoincrement, " +
                    Constants.COL_FullNAME + " text ," +
                    Constants.COL_Email + " text ," +
                    Constants.COL_Birthday + " text," +
                    Constants.COL_UserName + " text," +
                    Constants.COL_PASSWORD + " text," +
                    Constants.COL_Skill + " text " +
                    " );";


    public DataBase(Context context) {
        super(context, "Course.db", null, 1);
        sharedPreferences = context.getSharedPreferences("name", 0);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(Course.CREATE_TABLE_COURSE);
    }


    public void open() throws SQLException {
        database = getWritableDatabase();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME);
        onCreate(db);
    }

    interface Constants {
        String COL_ID = "id";
        String TABLE_USER = "USER";
        String COL_FullNAME = "fullName";
        String COL_Email = "email";
        String COL_Birthday = "birthday";
        String COL_UserName = "userName";
        String COL_PASSWORD = "password";
        String COL_Skill = "skill";
    }

    public boolean insertUSER(User item, String password) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(Constants.COL_FullNAME, item.getFullName());
            initialValues.put(Constants.COL_Email, item.getEmail());
            initialValues.put(Constants.COL_Birthday, item.getBirthday());
            initialValues.put(Constants.COL_UserName, item.getUserName());
            initialValues.put(Constants.COL_PASSWORD, password);
            initialValues.put(Constants.COL_Skill, item.getSkill());
            didSucceed = database.insert(Constants.TABLE_USER, null, initialValues) > 0;

        } catch (Exception e) {
        }
        return didSucceed;
    }


    public boolean checkUserName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_USER + " WHERE " + Constants.COL_UserName + " = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_USER + " WHERE " + Constants.COL_Email + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_PASSWORD, newPassword);
        int rowsAffected = db.update(Constants.TABLE_USER, values, Constants.COL_Email + " = ?", new String[]{email});
        return rowsAffected > 0;
    }


    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.TABLE_USER, new String[]{Constants.COL_ID, Constants.COL_UserName, Constants.COL_PASSWORD}, null, null, null, null, null, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            do {
                String _username = cursor.getString(1);
                String _password = cursor.getString(2);
                if (_username.equals(username) && _password.equals(password)) {
                    userId = cursor.getInt(0);
                    writeOnPreferenceId(userId);
                    writeOnPreferenceUserName(username);
                    cursor.close();
                    break;
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return userId > 0;
    }

    public boolean addCourse(Course item) {
        boolean addedSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(Course.COL_Image, item.getImgCourse());
            initialValues.put(Course.COL_TypeCourse, item.getTypeCourse());
            initialValues.put(Course.COL_CourseTitle, item.getCourseTitle());
            initialValues.put(Course.COL_CourseInfo, item.getCourseInfo());
            initialValues.put(Course.COL_CourseDate, item.getCourseDate());
            initialValues.put(Course.COL_CourseUrl, item.getCourseUrl());
            initialValues.put(Course.COL_Location, item.getLocation());
            addedSucceed = database.insert(Course.TABLE_NAME, null, initialValues) > 0;

        } catch (Exception e) {
        }
        return addedSucceed;
    }


    @SuppressLint("Range")
    public ArrayList<Course> getCourseByType(String type_course) {
        ArrayList<Course> courses = new ArrayList<>();

        Cursor cursor = database.query(Course.TABLE_NAME, null, Course.COL_TypeCourse + " LIKE '%" + type_course + "%'", null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Course courses1 = new Course();
                courses1.setId(cursor.getInt(cursor.getColumnIndex(Course.COL_ID)));
                courses1.setImgCourse(cursor.getBlob(cursor.getColumnIndex(Course.COL_Image)));
                courses1.setTypeCourse(cursor.getString(cursor.getColumnIndex(Course.COL_TypeCourse)));
                courses1.setCourseTitle(cursor.getString(cursor.getColumnIndex(Course.COL_CourseTitle)));
                courses1.setCourseInfo(cursor.getString(cursor.getColumnIndex(Course.COL_CourseInfo)));
                courses1.setCourseDate(cursor.getString(cursor.getColumnIndex(Course.COL_CourseDate)));
                courses1.setCourseUrl(cursor.getString(cursor.getColumnIndex(Course.COL_CourseUrl)));
                courses1.setLocation(cursor.getString(cursor.getColumnIndex(Course.COL_Location)));
                courses.add(courses1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courses;
    }


    public boolean deleteCourse(int id) {
        return database.delete(Course.TABLE_NAME, Course.COL_ID + "=" + id, null) > 0;
    }

    public boolean updateCourse(Course dataCourse , String id) {
        ContentValues values = new ContentValues();
        values.put(Course.COL_Image, dataCourse.getImgCourse());
        values.put(Course.COL_CourseTitle, dataCourse.getCourseTitle());
        values.put(Course.COL_CourseInfo, dataCourse.getCourseInfo());
        values.put(Course.COL_CourseDate, dataCourse.getCourseDate());
        values.put(Course.COL_CourseUrl, dataCourse.getCourseUrl());
        values.put(Course.COL_Location, dataCourse.getLocation());
        int rowsAffected = database.update(Course.TABLE_NAME, values, Course.COL_ID+" = ?", new String[]{id});
        return rowsAffected > 0;
    }
    public boolean deleteUser(int id) {
        return database.delete(Constants.TABLE_USER, Constants.COL_ID + "=" + id, null) > 0;
    }


    @SuppressLint("Range")
    public User getUserData(String idUser) {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.TABLE_USER + " WHERE " + Constants.COL_ID + " = ?", new String[]{idUser});
            if (cursor.getColumnCount() == 0) {
                return null;
            }
            User data;
            cursor.moveToFirst();
            data = new User();
            data.setId(cursor.getInt(cursor.getColumnIndex(Constants.COL_ID)));
            data.setFullName(cursor.getString(cursor.getColumnIndex(Constants.COL_FullNAME)));
            data.setEmail(cursor.getString(cursor.getColumnIndex(Constants.COL_Email)));
            data.setBirthday(cursor.getString(cursor.getColumnIndex(Constants.COL_Birthday)));
            data.setUserName(cursor.getString(cursor.getColumnIndex(Constants.COL_UserName)));
            data.setSkill(cursor.getString(cursor.getColumnIndex(Constants.COL_Skill)));
            cursor.close();
            return data;
        } catch (Exception e) {
            Log.i(TAG, "getLogin: " + e.getMessage());
        }
        return null;
    }

    public boolean updateUser(User user , String id) {
        ContentValues values = new ContentValues();
        values.put(Constants.COL_FullNAME, user.getFullName());
        values.put(Constants.COL_Email, user.getEmail());
        values.put(Constants.COL_Birthday, user.getBirthday());
        values.put(Constants.COL_UserName, user.getUserName());
        values.put(Constants.COL_Skill, user.getSkill());
        int rowsAffected = database.update(Constants.TABLE_USER, values, Constants.COL_ID+" = ?", new String[]{id});
        return rowsAffected > 0;
    }

    private void writeOnPreferenceId(int idUser) {
        sharedPreferences.edit()
                .putInt("id_user", idUser)
                .apply();
    }

    private void writeOnPreferenceUserName(String UserName) {
        sharedPreferences.edit()
                .putString("user_name", UserName)
                .apply();
    }

}
