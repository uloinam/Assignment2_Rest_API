package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class Detail_Fruit_Actitvity extends AppCompatActivity {
    ImageView image_product, btn_update;
    Button btn_addShopCart;
    Fruits_Model fruitsModel;
    TextView tv_Name_Product, tv_Price_Product,tv_Brand_Product,tv_Dimensions_Product,tv_Descrption_Product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_fruit_actitvity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_addShopCart = findViewById(R.id.btn_add_Shop_Cart);
        image_product = findViewById(R.id.image_product);
        tv_Name_Product = findViewById(R.id.tv_Name_Product);
        tv_Price_Product = findViewById(R.id.tv_Price_Product);
        tv_Brand_Product = findViewById(R.id.tv_Brand_Product);
        tv_Dimensions_Product = findViewById(R.id.tv_Dimensions_Product);
        tv_Descrption_Product = findViewById(R.id.tv_Descrption_Product);
        btn_update = findViewById(R.id.btn_update);
        Intent intent = getIntent();
        if(intent != null){
//            Distributor_Model distributorModel = (Dí)intent.getSerializableExtra("Distributor", Distributor_Model.class);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                Distributor_Model distributorModel = intent.getSerializableExtra("Distributor", Distributor_Model.class);
                 fruitsModel = intent.getSerializableExtra("fruit", Fruits_Model.class);
                String url = fruitsModel.getImage().get(0);
                String newUrl = url.replace("localhost", " 192.168.1.54");
                for (String list : fruitsModel.getImage()){
                    String new_url = list.replace("localhost", " 192.168.1.54");
                    Log.d("zzzzzz", "onClick: "+new_url);
                }
                Glide.with(Detail_Fruit_Actitvity.this)
                        .load(newUrl)
                        .into(image_product);
                tv_Name_Product.setText("Tên sản phẩm: "+fruitsModel.getName());
                tv_Brand_Product.setText("Số lượng: "+fruitsModel.getQuantity());
                tv_Price_Product.setText("Giá: "+fruitsModel.getPrice());
                tv_Descrption_Product.setText("Nhà phân phối: "+fruitsModel.getDistributorModel().getName());
            }
        }
        btn_addShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Detail_Fruit_Actitvity.this, Address_Activity.class));
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Detail_Fruit_Actitvity.this, Update_Fruit.class);
                intent.putExtra("fruit", fruitsModel);
                startActivity(intent);
            }
        });
    }
}