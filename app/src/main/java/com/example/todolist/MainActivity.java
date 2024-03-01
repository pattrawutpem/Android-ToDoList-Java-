package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int selectposition = -1;
    private List<Item> items = new ArrayList<Item>();
    private OkHttpClient client = new OkHttpClient();
    private String url = "https://jsonplaceholder.typicode.com/photos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Request request = new Request.Builder().url(url).build();
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectposition == -1) {
                    items.add(new Item(binding.text1.getText().toString(), binding.text2.getText().toString(), R.drawable.ic_launcher_background));
                    binding.recycleview.getAdapter().notifyDataSetChanged();
                } else {
                    items.set(selectposition, new Item(binding.text1.getText().toString(), binding.text2.getText().toString(), R.drawable.ic_launcher_background));
                    binding.recycleview.getAdapter().notifyDataSetChanged();
                    selectposition = -1;
                    binding.add.setText("ADD");
                }
                binding.text1.setText("");
                binding.text2.setText("");
            }
        });
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleview.setAdapter(new MyAdapter(getApplicationContext(), items, new MyAdapter.Callback() {
            @Override
            public void onEdit(Item item, int position) {
                selectposition = position;
                binding.text1.setText(item.text1);
                binding.text2.setText(item.text2);
                binding.add.setText("Update");
            }

            @Override
            public void onDelete(Item item, int position) {
//                alertLog(position);
                DialogAlert dialogAlert = new DialogAlert(MainActivity.this, item, position, new DialogAlert.Callback() {
                    @Override
                    public void confirm(int position) {
                        items.remove(position);
                        binding.recycleview.getAdapter().notifyDataSetChanged();
                    }
                });
                dialogAlert.show();
            }
        }
        ));

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseData = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(responseData);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                int img = jsonObject.getInt("albumId");
                                items.add(new Item(id, title, img));
                            }
                            binding.recycleview.getAdapter().notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Failed to parse JSON data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    //alert delete
    public void alertLog(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Are you suru?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                items.remove(position);
                binding.recycleview.getAdapter().notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cencal", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }

    public void postApi(){
        RequestBody requestBody = new FormBody.Builder().add("","").build();
        Request request = new Request.Builder().url("url").post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                binding.text1.setText(response.body().toString());
            }
        });
    }
}