package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class Rc_fruitUpLoad_Adapter extends RecyclerView.Adapter<Rc_fruitUpLoad_Adapter.Image_UpLoadViewHodel>{
    Context context;
    ArrayList<Uri> list;

    public Rc_fruitUpLoad_Adapter(Context context, ArrayList<Uri> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Image_UpLoadViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_fruit_upload, parent, false);
        return new Image_UpLoadViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Image_UpLoadViewHodel holder, int position) {

        Uri uri = list.get(position);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            holder.image_Fruit.setImageBitmap(bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Image_UpLoadViewHodel extends RecyclerView.ViewHolder{
        ImageView image_Fruit;
        public Image_UpLoadViewHodel(@NonNull View itemView) {
            super(itemView);

            image_Fruit = itemView.findViewById(R.id.image_fruit);
        }
    }
}
