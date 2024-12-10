package com.example.learnsphere2.Classes;


import java.io.Serializable;

public class Course implements Serializable {

    private int id;
    private byte[] imgCourse;

    private String typeCourse, courseTitle, courseInfo, courseDate , courseUrl, location;
    public static final String TABLE_NAME = "course";
    public static final String COL_ID = "id";
    public static final String COL_TypeCourse = "typeCourse";
    public static final String COL_Image = "imgCourse";
    public static final String COL_CourseTitle = "courseTitle";
    public static final String COL_CourseInfo = "courseInfo";
    public static final String COL_CourseDate = "courseDate";
    public static final String COL_CourseUrl= "courseUrl";
    public static final String COL_Location = "location";


    public static final String CREATE_TABLE_COURSE =
            "create table " + TABLE_NAME + " (" +
                    COL_ID + " integer primary key autoincrement, " +
                    COL_TypeCourse + " text ," +
                    COL_Image + " BLOB ," +
                    COL_CourseTitle + " text ," +
                    COL_CourseInfo + " text," +
                    COL_CourseDate + " text," +
                    COL_CourseUrl + " text," +
                    COL_Location + " text " +
                    " );";

    public Course() {
    }

    public Course(byte[] imgCourse, String typeCourse, String courseTitle, String courseInfo, String courseDate, String courseUrl, String location) {
        this.imgCourse = imgCourse;
        this.typeCourse = typeCourse;
        this.courseTitle = courseTitle;
        this.courseInfo = courseInfo;
        this.courseDate = courseDate;
        this.courseUrl = courseUrl;
        this.location = location;
    }


    public Course(byte[] imgCourse, String courseTitle, String courseInfo, String courseDate, String courseUrl, String location) {
        this.imgCourse = imgCourse;
        this.courseTitle = courseTitle;
        this.courseInfo = courseInfo;
        this.courseDate = courseDate;
        this.courseUrl = courseUrl;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeCourse() {
        return typeCourse;
    }

    public void setTypeCourse(String typeCourse) {
        this.typeCourse = typeCourse;
    }

    public byte[] getImgCourse() {
        return imgCourse;
    }

    public void setImgCourse(byte[] imgCourse) {
        this.imgCourse = imgCourse;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
