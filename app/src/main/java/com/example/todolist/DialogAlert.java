package com.example.todolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.databinding.DialogBinding;

import java.util.ArrayList;
import java.util.List;

public class DialogAlert extends Dialog {
    private DialogBinding dialogBinding;
    private int position;
    private Item item;
    Callback callback;
    interface Callback{
        void confirm(int position);
    }
    public DialogAlert(@NonNull Context context,Item item, int position, Callback callback) {
        super(context);
        this.position = position;
        this.callback = callback;
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogBinding = DialogBinding.inflate(getLayoutInflater());
        setContentView(dialogBinding.getRoot());
        dialogBinding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.confirm(position);
                dismiss();
            }
        });
        dialogBinding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}