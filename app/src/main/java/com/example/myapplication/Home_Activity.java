package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Activity extends AppCompatActivity {
    private static final String TAG = "zzzzzzz";
    RecyclerView recyclerView;
 FloatingActionButton btn_add;
 HttpRequest httpRequest;
 ArrayList<Fruits_Model> list_fruit;
 Fruit_ADapter fruitADapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.lv_fruit);
        btn_add = findViewById(R.id.btn_add);

        httpRequest = new HttpRequest();
        list_fruit = new ArrayList<>();

        httpRequest.callAPI().get_all_listFruit().enqueue(callistFruit);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, add_FruitActivity.class));
            }
        });
    }



    Callback<Reponse_Model<ArrayList<Fruits_Model>>> callistFruit  = new Callback<Reponse_Model<ArrayList<Fruits_Model>>>() {
        @Override
        public void onResponse(Call<Reponse_Model<ArrayList<Fruits_Model>>> call, Response<Reponse_Model<ArrayList<Fruits_Model>>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    list_fruit = response.body().getData();
                    setDataLv();
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<ArrayList<Fruits_Model>>> call, Throwable t) {
            httpRequest.callAPI().get_all_listFruit().enqueue(callistFruit);
            Toast.makeText(Home_Activity.this, ""+t, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onFailure: "+t);
        }
    };

    public void setDataLv(){
        fruitADapter = new Fruit_ADapter(this, list_fruit);
        recyclerView.setAdapter(fruitADapter);
    }



}