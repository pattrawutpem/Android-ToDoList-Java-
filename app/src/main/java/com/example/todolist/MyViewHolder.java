package com.example.todolist;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView text1View,text2View;
    Button Delete,Edit;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        text1View = itemView.findViewById(R.id.text1);
        text2View = itemView.findViewById(R.id.text2);
        Delete = itemView.findViewById(R.id.delete);
        Edit = itemView.findViewById(R.id.edit);
    }
}
