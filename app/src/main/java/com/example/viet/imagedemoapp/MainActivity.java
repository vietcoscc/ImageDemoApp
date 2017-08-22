package com.example.viet.imagedemoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnPost)
    Button btnPost;
    private ImageRecyclerViewAdapter mAdapter;
    private ArrayList<Image> mArrImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new ImageRecyclerViewAdapter(mArrImage);
        recyclerView.setAdapter(mAdapter);
        Retrofit retrofit = ApiClient.getApiClient();
        Call<ArrayList<Image>> call = retrofit.create(ApiInterface.class).getImage("5");
        call.enqueue(new Callback<ArrayList<Image>>() {
            @Override
            public void onResponse(Call<ArrayList<Image>> call, Response<ArrayList<Image>> response) {
                ArrayList<Image> images = response.body();
                mArrImage.clear();
                mArrImage.addAll(images);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Image>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Call<String> call2 = retrofit.create(ApiInterface.class).deleteImage("5", "upload/vietcc.png");
        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MainActivity.this, "Response", Toast.LENGTH_SHORT).show();
                Log.i(TAG, response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG,t.getMessage());
            }
        });

    }

}
