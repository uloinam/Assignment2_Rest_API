package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fruit_ADapter extends RecyclerView.Adapter<Fruit_ADapter.Fruit_ViewHodel> {
    private static final String TAG = "zzzzzzzzzzzzzzzz";
    Context context;
    ArrayList<Fruits_Model> list;
    HttpRequest httpRequest;
    public Fruit_ADapter(Context context, ArrayList<Fruits_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Fruit_ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fruit, parent, false);
        httpRequest = new HttpRequest();
        return new Fruit_ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Fruit_ViewHodel holder, int position) {
        Fruits_Model fruitsModel = list.get(position);
        Distributor_Model distributorModel = fruitsModel.getDistributorModel();
        holder.tv_Name.setText("Tên: "+fruitsModel.getName());
        holder.tv_Price.setText("Giá: "+fruitsModel.getPrice());
        holder.tv_distributor.setText("Nhà phân phối"+distributorModel.getName());

//        if (distributorModel != null){
//            Log.d(TAG, "onBindViewHolder: "+fruitsModel.getImage().get(1));
//            Log.d(TAG, "onBindViewHolder: "+distributorModel.getName());
//        }
        if (fruitsModel.getImage().size() > 0){
            String url = fruitsModel.getImage().get(0);
            String newUrl = url.replace("localhost", "192.168.1.54");
            Glide.with(holder.itemView.getContext())
                    .load(newUrl).fallback(R.drawable.ic_delete) // Optional
                    .into(holder.image_fruit);
        }


        // delete
        holder.btn_add_shopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm delete");
                builder.setMessage("Are you sure you want to delete?");
                Log.d(TAG, "onClick: "+fruitsModel.get_id());
                builder.setPositiveButton("yes", (dialog, which) -> {
                    httpRequest.callAPI()
                            .deleteFruits(fruitsModel.get_id())
                            .enqueue(responseFruitAPI);
                });
                builder.setNegativeButton("no", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            }
        });

        holder.line_item_fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Fruit_Actitvity.class);
                Bundle bundle = new Bundle();

                //String _id, String price, String name, String status, String description, ArrayList<String> image, Distributor_Model distributorModel
                bundle.putString("id", fruitsModel.get_id());
                bundle.putString("price", fruitsModel.getPrice());
                bundle.putString("name", fruitsModel.getName());
                bundle.putString("Status", fruitsModel.getStatus());
                bundle.putString("description", fruitsModel.getDescription());
                bundle.putStringArray("Image", fruitsModel.getImage().toArray(new String[0]));
                bundle.putSerializable("Distributor", fruitsModel.getDistributorModel());
                bundle.putSerializable("fruit", fruitsModel);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Fruit_ViewHodel extends RecyclerView.ViewHolder{
    TextView tv_Name, tv_Price, tv_distributor;
    ImageView btn_add_shopCart, image_fruit;
    LinearLayout line_item_fruit;
        public Fruit_ViewHodel(@NonNull View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_name);
            tv_Price = itemView.findViewById(R.id.tv_price);
            tv_distributor = itemView.findViewById(R.id.tv_distributor);
            image_fruit = itemView.findViewById(R.id.image_fruit);
            btn_add_shopCart = itemView.findViewById(R.id.btn_add_shopCart);
            line_item_fruit = itemView.findViewById(R.id.line_fruit);
        }
    }

    Callback<Reponse_Model<Fruits_Model>> responseFruitAPI = new Callback<Reponse_Model<Fruits_Model>>() {
        @Override
        public void onResponse(Call<Reponse_Model<Fruits_Model>> call, Response<Reponse_Model<Fruits_Model>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus()== 200) {
                    
                    Toast.makeText(context, "Xóa than công", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<Fruits_Model>> call, Throwable t) {
            Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show();
        }
    };

}
