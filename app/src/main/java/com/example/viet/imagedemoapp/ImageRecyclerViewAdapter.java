package com.example.viet.imagedemoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.viet.imagedemoapp.ApiClient.BASE_URL;

/**
 * Created by viet on 07/08/2017.
 */

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ImageRecyclerViewAdapter";
    private ArrayList<Image> arrImage;
    private Context context;

    public ImageRecyclerViewAdapter(ArrayList<Image> arrImage) {
        this.arrImage = arrImage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_recycler_view, parent, false);
        return new ImageViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        Image image = arrImage.get(position);
//        byte[] bytes = Base64.decode(image.getImage(), Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        imageViewHolder.ivImage.setImageBitmap(bitmap);
        Log.i(TAG, BASE_URL + image.getImage() + " ");

        Glide.with(context).load(BASE_URL + image.getImage()).into(imageViewHolder.ivImage);

        imageViewHolder.tvTitle.setText(image.getTitle());
    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
