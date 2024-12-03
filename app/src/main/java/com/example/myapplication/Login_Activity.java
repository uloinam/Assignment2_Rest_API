package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    public static final String TAG = "zzzzzzzz";
    EditText edt_UserName, edt_Password;
    Button btn_Login;
    HttpRequest httpRequest;
    TextView tv_SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_UserName = findViewById(R.id.edt_username);
        edt_Password = findViewById(R.id.edt_password);
        btn_Login = findViewById(R.id.btn_login);
        tv_SignIn = findViewById(R.id.tv_SignIn);
        httpRequest = new HttpRequest();


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                String _username = edt_UserName.getText().toString().trim();
                String _password = edt_Password.getText().toString().trim();
                user.setUsername(_username);
                user.setPassword(_password);
                httpRequest.callAPI().login(user).enqueue(callAPI_User);
            }
        });

        tv_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, SiggIn_Activity.class));
            }
        });
    }

    Callback<Reponse_Model<User>> callAPI_User = new Callback<Reponse_Model<User>>() {
        @Override
        public void onResponse(Call<Reponse_Model<User>> call, Response<Reponse_Model<User>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_Activity.this, Home_Activity.class));
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<User>> call, Throwable t) {
            Log.d(TAG, "onFailure: "+t);
        }
    };
}