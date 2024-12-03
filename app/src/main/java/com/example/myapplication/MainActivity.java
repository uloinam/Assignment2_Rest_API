package com.example.myapplication;

import static android.widget.Toast.makeText;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Distributor_Adapter.EvenClick {
    private static final String TAG = "zzzzzzz";
    List<Distributor_Model> list;
    API_Service apiService;
    ListView lv;
    EditText edt_search;
    Distributor_Adapter distributorAdapter;
    ProgressBar progressBar;
    HttpRequest httpRequest;
    FloatingActionButton btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        list = new ArrayList<>();
        progressBar = findViewById(R.id.progresBar);

        edt_search = findViewById(R.id.edt_seachr);
        lv = findViewById(R.id.lv_distributor);

        httpRequest = new HttpRequest();

        httpRequest.callAPI().get_list().enqueue(call_getTributor);
        progressBar.setVisibility(View.VISIBLE);

        btn_add = findViewById(R.id.btn_add);

        Click_Search();

        add_Distributor();
    }


    // set data lên listview
    public void setDataLv(){
        distributorAdapter = new Distributor_Adapter(list, this , this);
        lv.setAdapter(distributorAdapter);
    }


    // Call model Reponse kiểu dữ liệu object
    public Callback<Reponse_Model<ArrayList<Distributor_Model>>> call_getTributor = new Callback<Reponse_Model<ArrayList<Distributor_Model>>>() {
        @Override
        public void onResponse(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Response<Reponse_Model<ArrayList<Distributor_Model>>> response) {

            if (response.isSuccessful()){
                // Bóc tách nhỏ dữ liệu nhận được bao gồm status, messeger, data(T (biểu thị cho kiểu dữ liệu bất kì))
                if (response.body().getStatus() == 200){
                    // xét status của Respocoscos bằng 200 không
                    progressBar.setVisibility(View.GONE);

                    //nếu đúng nhận danh sách từ getData (Kiểu dữ lệu T)
                    list = response.body().getData();

                    //setData lêm list view
                    setDataLv();
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Throwable t) {
            makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onFailure: "+t);
        }
    };

    Callback<Reponse_Model<ArrayList<Distributor_Model>>> reponseDisTributorAPI = new Callback<Reponse_Model<ArrayList<Distributor_Model>>>() {
        @Override
        public void onResponse(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Response<Reponse_Model<ArrayList<Distributor_Model>>> response) {

            if (response.isSuccessful()){
                // Bóc tách nhỏ dữ liệu nhận được bao gồm status, messeger, data(T (biểu thị cho kiểu dữ liệu bất kì))
                if (response.body().getStatus() == 200){
                    // xét status của Respocoscos bằng 200 không
                    progressBar.setVisibility(View.GONE);

                    //nếu đúng nhận danh sách từ getData (Kiểu dữ lệu T)
                    list = response.body().getData();

                    //setData lêm list view
                    setDataLv();
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Throwable t) {
            makeText(MainActivity.this, ""+t, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onFailure: "+t);
        }
    };

    // Override interface sử lý sự kiện xóa
    @Override
    public void delete(Distributor_Model distributorModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo !")
                .setMessage("Bạn chắc chắn muốn xóa")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            Log.d(TAG, "onFailure: "+distributorModel.getName());

                        progressBar.setVisibility(View.VISIBLE);
                        // sử lý chuyền vào id muốn xóa
                        // chuyền enqueue sự kiện sử lý sự kiện call

                        httpRequest.callAPI().delete(distributorModel.getId()).enqueue(reponseDisTributorAPI);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public void update(Distributor_Model distributorModel) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_add, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        EditText edt_name = view.findViewById(R.id.edt_name);
        Button btn_update = view.findViewById(R.id.btn_add);

        edt_name.setText(distributorModel.getName());

        builder.setView(view);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString().trim();
                
                httpRequest.callAPI().update(distributorModel.getId(), new Distributor_Model(distributorModel.getId(), name))
                        .enqueue(reponseDisTributorAPI);

            }
        });
        builder.show();
    }

    public void Click_Search(){
        edt_search.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableLeftIndex = 0; // Index for left drawable
                Drawable leftDrawable = edt_search.getCompoundDrawables()[drawableLeftIndex];
                if (leftDrawable != null) {
                    // Calculate drawable bounds
                    int drawableWidth = leftDrawable.getBounds().width();
                    if (event.getX() <= (edt_search.getPaddingLeft() + drawableWidth)) {
                        String key = edt_search.getText().toString().trim();

                        httpRequest.callAPI().get_list_search(key).enqueue(reponseDisTributorAPI);
                        return true;
                    }
                }
            }
            return false;
        });
    }

    public void add_Distributor(){
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_add, null, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                Button btn_config = view.findViewById(R.id.btn_add);
                EditText edt_name = view.findViewById(R.id.edt_name);
                builder.setView(view);
                Dialog dialog = builder.create();

                btn_config.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edt_name.getText().toString().trim();
                        if(!name.isEmpty()){
                            httpRequest.callAPI().add(new Distributor_Model("", name))
                                    .enqueue(reponseDisTributorAPI);
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });
    }
}