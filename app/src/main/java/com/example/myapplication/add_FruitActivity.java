package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_FruitActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSIONS = 123;
    RecyclerView rc_image;
    EditText edt_Name, edt_Price, edt_Describe, edt_Quantity;
    TextView tv_Select_Image;
    Button btn_add;
    Spinner spinner;
    ImageView btn_back;
    ArrayAdapter<String> arrayAdapter;
    Distributor_Model distributorModel;
    ArrayList<Distributor_Model> list_distributor;
    Rc_fruitUpLoad_Adapter rcFruitUpLoadAdapter;
    ArrayList<Uri> list_Uri_Image;
    String id_distributor;
    ArrayList<File> list_File_Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_fruit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edt_Name = findViewById(R.id.edt_name);
        edt_Price = findViewById(R.id.edt_Price);
        edt_Describe = findViewById(R.id.edt_description);
        edt_Quantity = findViewById(R.id.edt_quantity);

        btn_add = findViewById(R.id.btn_add);

        spinner = findViewById(R.id.spinner);

        btn_back = findViewById(R.id.btn_back);
        tv_Select_Image =findViewById(R.id.select_Image);

        list_Uri_Image = new ArrayList<>();

        rc_image = findViewById(R.id.rc_fruit);
        list_distributor = new ArrayList<>();

        rc_image.setLayoutManager(new LinearLayoutManager(add_FruitActivity.this, LinearLayoutManager.HORIZONTAL, false));
        HttpRequest httpRequest = new HttpRequest();

        httpRequest.callAPI().get_list().enqueue(callLisDistributor);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, RequestBody> mapRequestBody = new HashMap<>();

                // Lấy giữu liệu edttextx
                String _name = edt_Name.getText().toString().trim();
                String _quantity = edt_Quantity.getText().toString().trim();
                String _price = edt_Price.getText().toString().trim();
                String _status = "1";
                String _description = edt_Describe.getText().toString().trim();

                mapRequestBody.put("Name", getRequestBody(_name));
                mapRequestBody.put("quantity", getRequestBody(_quantity));
                mapRequestBody.put("Price", getRequestBody(_price));
                mapRequestBody.put("Status", getRequestBody(_status));
                mapRequestBody.put("description", getRequestBody(_description));
                mapRequestBody.put("id_distributor", RequestBody.create(MediaType.parse("multipart/form-data"), id_distributor));

                ArrayList<MultipartBody.Part> ds_Multipart_Image = new ArrayList<>();

                list_File_Image.forEach(file1 -> {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file1);
                    MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", file1.getName(),requestFile);
                    ds_Multipart_Image.add(multipartBodyPart);
                });

                httpRequest.callAPI().addFruit(mapRequestBody, ds_Multipart_Image).enqueue(callAPI_Add_Fruit);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_distributor = list_distributor.get(position).getId();
                distributorModel = list_distributor.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_Multi_Image();
    }

    Callback<Reponse_Model<ArrayList<Distributor_Model>>> callLisDistributor = new Callback<Reponse_Model<ArrayList<Distributor_Model>>>() {
        @Override
        public void onResponse(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Response<Reponse_Model<ArrayList<Distributor_Model>>> response) {
            if (response.isSuccessful()){
                // Bóc tách nhỏ dữ liệu nhận được bao gồm status, messeger, data(T (biểu thị cho kiểu dữ liệu bất kì))
                if (response.body().getStatus() == 200){
                    // xét status của Respocoscos bằng 200 không


                    //nếu đúng nhận danh sách từ getData (Kiểu dữ lệu T)

                    list_distributor = response.body().getData();
                    List<String> name_distributor = new ArrayList<>();
                    for (Distributor_Model list: list_distributor){
                        name_distributor.add(list.getName());
                    }
                    arrayAdapter = new ArrayAdapter(add_FruitActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, name_distributor);
                    //setData lêm list view
                    spinner.setAdapter(arrayAdapter);
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Throwable t) {

        }
    };

    public void select_Multi_Image(){
        tv_Select_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions()){
                    requestPermissions();
                }

            }
        });
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE_PERMISSIONS);
    }

    private void openGrallery() {
        Intent intent = new Intent();
        intent.setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                .setAction(Intent.ACTION_GET_CONTENT);
        mIntent_OpenGrallery.launch(intent);
    }
    ActivityResultLauncher<Intent> mIntent_OpenGrallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent dt = result.getData();
                    Uri temp_Uri_Image = null;
                    list_File_Image = new ArrayList<>();
                    if (dt  != null){
                        int count = dt.getClipData().getItemCount();
                        for(int i = 0; i < count; i++){
                            list_Uri_Image.add(dt.getClipData().getItemAt(i).getUri());
                            temp_Uri_Image = dt.getClipData().getItemAt(i).getUri();
                            File file = createFileFormUri(temp_Uri_Image, "Image" + 1 );
                            list_File_Image.add(file);
                        }

                        rcFruitUpLoadAdapter = new Rc_fruitUpLoad_Adapter(add_FruitActivity.this, list_Uri_Image);
                        rc_image.setAdapter(rcFruitUpLoadAdapter);
                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGrallery();
            } else {
                // Quyền bị từ chối
            }
        }
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private File createFileFormUri (Uri path, String name) {
        File _file = new File(add_FruitActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = add_FruitActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " +_file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"),value);
    }

    Callback<Reponse_Model<Fruits_Model>> callAPI_Add_Fruit = new Callback<Reponse_Model<Fruits_Model>>() {
        @Override
        public void onResponse(Call<Reponse_Model<Fruits_Model>> call, Response<Reponse_Model<Fruits_Model>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    Toast.makeText(add_FruitActivity.this, "Thêm than công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<Fruits_Model>> call, Throwable t) {
            Log.d("zzzzzz", "onFailure: "+t);
        }
    };
}