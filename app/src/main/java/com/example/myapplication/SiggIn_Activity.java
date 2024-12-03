package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SiggIn_Activity extends AppCompatActivity {
    private static final String TAG = "zzzzzzzzzzzz";
    EditText edt_userName, edt_passWord, edt_name, edt_email;
    Button btn_SignUp;
    ImageView image_avatar;
    private File file;
    HttpRequest httpRequest;
    public static final Integer REQUESTCODE_TAKE_IMAGE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sigg_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edt_userName = findViewById(R.id.edt_username);
        edt_passWord = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        edt_name = findViewById(R.id.edt_name);
        image_avatar = findViewById(R.id.image_avarta);
        btn_SignUp = findViewById(R.id.btn_SignIn);
        httpRequest = new HttpRequest();

        event_choose_Image();
        event_click_SignUp();
    }


    public void event_click_SignUp(){
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_userName.getText().toString().trim();
                String password = edt_passWord.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String name = edt_name.getText().toString().trim();

                // requestbody tạo các trường part và kiểu multipart/ form-data
                RequestBody _username = RequestBody.create(MediaType.parse("multipart/form-data"), username);
                RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"), password);
                RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"), email);
                RequestBody _name = RequestBody.create(MediaType.parse("multipart/form-data"), name);

                // Upload file
                MultipartBody.Part multipart;

                // check file đã được chọn chưa
                if (file !=  null){
                    RequestBody _image = RequestBody.create(MediaType.parse("image/*"), file);
                    multipart = MultipartBody.Part.createFormData("avatar", file.getName(), _image);
                }else{
                    multipart = null;
                }

                httpRequest.callAPI().new_user(_username, _password, _email, _name, multipart).enqueue(reponse_Usermodel_callBack);
            }
        });
    }
    public void event_choose_Image() {
        image_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                getImage_In_Storage.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> getImage_In_Storage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == RESULT_OK && o.getData() != null){
                        Intent data = o.getData();
                        Uri uri_image = data.getData();

                        file = createFileFormUri(uri_image, "avatar");

                        Glide.with(image_avatar)
                                .load(uri_image)
                                .centerCrop()
                                .circleCrop()
                                .into(image_avatar);

                    }
                }
            });


    // tạo 1 file từ URI
    private File createFileFormUri (Uri path, String name) {
        File _file = new File(SiggIn_Activity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = SiggIn_Activity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();

            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    Callback<Reponse_Model<User>> reponse_Usermodel_callBack = new Callback<Reponse_Model<User>>() {
        @Override
        public void onResponse(Call<Reponse_Model<User>> call, Response<Reponse_Model<User>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    Toast.makeText(SiggIn_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(SiggIn_Activity.this, Login_Activity.class));
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<User>> call, Throwable t) {
            Log.d(TAG, "onFailure: "+t);
        }
    };

}