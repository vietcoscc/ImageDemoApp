package com.example.viet.imagedemoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostActivity extends AppCompatActivity {
    private static final int RC_CODE = 1;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.btnSelect)
    Button btnSelect;
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.btnUpload)
    Button btnUpload;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, RC_CODE);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTitle.getText().toString().isEmpty() || mBitmap == null) {
                    Toast.makeText(PostActivity.this, "Content must not empty ", Toast.LENGTH_SHORT).show();
                }
                String title = edtTitle.getText().toString().trim();
                String image = stringFromBitmap();
                Retrofit retrofit = ApiClient.getApiClient();
                Call<Image> call = retrofit.create(ApiInterface.class).uploadImage(image, title);
                call.enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(Call<Image> call, Response<Image> response) {
                        Image image = response.body();
                        Toast.makeText(PostActivity.this, image.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Image> call, Throwable t) {
                        Toast.makeText(PostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CODE && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "Chosen", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivImage.setImageBitmap(mBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String stringFromBitmap() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
