package com.example.learnsphere2.Adapter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.R;

import java.util.ArrayList;

public class CourseUserAdapter extends RecyclerView.Adapter<CourseUserAdapter.Viewholder> {
    ArrayList<Course> items;
    Context context;

    public CourseUserAdapter(ArrayList<Course> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CourseUserAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        context = parent.getContext();
        return new Viewholder(inflator);}

    @Override
    public void onBindViewHolder(@NonNull CourseUserAdapter.Viewholder holder, int position) {
        Course data = items.get(position);

        holder.tvCourseTitle.setText(data.getCourseTitle());
        holder.tvCourseInfo.setText(data.getCourseInfo());
        holder.tvCourseDate.setText(data.getCourseDate());
        holder.tvLocation.setText(data.getLocation());

        Bitmap bitmapImgCourse = BitmapFactory.decodeByteArray(data.getImgCourse(), 0, data.getImgCourse().length);
        holder.imgCourse.setImageBitmap(bitmapImgCourse);

        holder.imgDelete.setVisibility(View.GONE);
        holder.imgUpdate.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(view -> {
            try {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getCourseUrl()));
                view.getContext(). startActivity(myIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(view.getContext(), "No application can handle this request."
                        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                e.printStackTrace();}});}


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvCourseTitle, tvCourseInfo, tvCourseDate, tvLocation;
        ImageView imgCourse, imgDelete, imgUpdate;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvCourseTitle = itemView.findViewById(R.id.tvCourseTitle);
            tvCourseInfo = itemView.findViewById(R.id.tvCourseInfo);
            tvCourseDate = itemView.findViewById(R.id.tvCourseDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgCourse = itemView.findViewById(R.id.imgCourse);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);}}}