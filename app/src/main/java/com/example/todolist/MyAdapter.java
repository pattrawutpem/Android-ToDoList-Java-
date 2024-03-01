package com.example.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.security.auth.callback.Callback;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<Item> item;
    Callback callback;


    interface Callback {
        void onEdit(Item item, int position);
        void onDelete(Item item, int position);
    }

    public MyAdapter(Context context, List<Item> item, Callback callback) {
        this.context = context;
        this.item = item;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text1View.setText(item.get(position).getText1());
        holder.text2View.setText(item.get(position).getText2());
        holder.imageView.setImageResource(item.get(position).getImg());
//        Glide.with(context).load(item.get(position).getImg()).into(holder.imageView);
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEdit(item.get(position), position);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDelete(item.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
