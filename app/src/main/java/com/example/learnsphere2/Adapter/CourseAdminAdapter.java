package com.example.learnsphere2.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learnsphere2.Classes.Course;
import com.example.learnsphere2.interfaces.OnItemDeleteListener;
import com.example.learnsphere2.interfaces.OnItemEditListener;
import com.example.learnsphere2.R;
import java.util.ArrayList;

public class CourseAdminAdapter extends RecyclerView.Adapter<CourseAdminAdapter.Viewholder> {
    ArrayList<Course> items;
    Context context;

    private OnItemDeleteListener mOnItemDeleteListener;
    private OnItemEditListener mOnItemEditListener;

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        mOnItemDeleteListener = onItemDeleteListener;}

    public void setOnItemEditListener(OnItemEditListener onItemEditListener) {
        mOnItemEditListener = onItemEditListener;}
    public CourseAdminAdapter(ArrayList<Course> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CourseAdminAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        context = parent.getContext();
        return new Viewholder(inflator);}

    @Override
    public void onBindViewHolder(@NonNull CourseAdminAdapter.Viewholder holder, int position) {
        Course data = items.get(position);

        holder.tvCourseTitle.setText(data.getCourseTitle());
        holder.tvCourseInfo.setText(data.getCourseInfo());
        holder.tvCourseDate.setText(data.getCourseDate());
        holder.tvLocation.setText(data.getLocation());

        Bitmap bitmapImgCourse = BitmapFactory.decodeByteArray(data.getImgCourse(), 0, data.getImgCourse().length);
        holder.imgCourse.setImageBitmap(bitmapImgCourse);

        holder.imgDelete.setOnClickListener(v -> {
            mOnItemDeleteListener.onClick(data, v, position);});

        holder.imgUpdate.setOnClickListener(v -> {
            mOnItemEditListener.onClick(data, v, position);});}


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