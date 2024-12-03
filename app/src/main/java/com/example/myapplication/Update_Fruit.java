package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Fruit extends AppCompatActivity {
    private Fruits_Model fruit;
    private String id ;
    private HttpRequest httpRequest;
    private String id_Distributor;
    private ArrayList<Distributor_Model> distributorArrayList;
    private ArrayList<File> ds_image;
    EditText edName, edPrice, edQuantity, edDescription, edStatus;
    ImageView image, image_Back;
    Spinner spDistributor;
    Button btn_Update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_fruit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_Update = findViewById(R.id.btn_register);
        image = findViewById(R.id.avatar);
        spDistributor = findViewById(R.id.sp_distributor);
        Intent intent = getIntent();
        fruit = (Fruits_Model) intent.getSerializableExtra("fruit");
        image_Back = findViewById(R.id.btn_back);
        id = fruit.get_id();
        edName = findViewById(R.id.ed_name);
        edPrice = findViewById(R.id.ed_price);
        edQuantity = findViewById(R.id.ed_quantity);
        edDescription = findViewById(R.id.ed_description);
        edStatus = findViewById(R.id.ed_status);
        httpRequest = new HttpRequest();
        String url = fruit.getImage().get(0);
        ds_image = new ArrayList<>();
        String newUrl = url.replace("localhost", "10.0.2.2");
        Glide.with(this)
                .load(newUrl)
                .into(image);
        edName.setText(fruit.getName());
        edPrice.setText(""+fruit.getPrice());
        edQuantity.setText(""+fruit.getQuantity());
        edDescription.setText(""+fruit.getDescription());
        edStatus.setText(""+fruit.getStatus());
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        configSpinner();
        image_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, RequestBody> mapRequestBody = new HashMap<>();
                String _name = edName.getText().toString().trim();
                String _quantity = edQuantity.getText().toString().trim();
                String _price = edPrice.getText().toString().trim();
                String _status = edStatus.getText().toString().trim();
                String _description = edDescription.getText().toString().trim();

                mapRequestBody.put("name", getRequestBody(_name));
                mapRequestBody.put("quantity", getRequestBody(_quantity));
                mapRequestBody.put("price", getRequestBody(_price));
                mapRequestBody.put("status", getRequestBody(_status));
                mapRequestBody.put("description", getRequestBody(_description));
                mapRequestBody.put("id_distributor", getRequestBody(id_Distributor));
                ArrayList<MultipartBody.Part> _ds_image = new ArrayList<>();
//
                // Kiểm tra xem người dùng đã chọn ảnh mới hay không
                if (ds_image.isEmpty()) {
//                    // Nếu không có ảnh mới, thêm các ảnh cũ vào danh sách
                    for (String imagePath : fruit.getImage()) {

//                        File imageFile = new File(imagePath);
//                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
//                        MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
//                        _ds_image.add(multipartBodyPart);
                    }
                    Log.e("aaaaaa", "onClick: Khoon co anh moi" );
                } else {
                    Log.e("aaaaaa", "onClick:  co anh moi" );

                    // Nếu có ảnh mới, thêm các ảnh mới vào danh sách
                    ds_image.forEach(file1 -> {
                        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file1);
                        MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", file1.getName(), requestFile);
                        _ds_image.add(multipartBodyPart);
                    });
                }

                // Gửi yêu cầu cập nhật lên server
                httpRequest.callAPI().updateFruitWithFileImage(mapRequestBody,
                        fruit.get_id(), _ds_image).enqueue(responseFruit);


            }

        });
    }
    Callback<Reponse_Model<Fruits_Model>> responseFruit = new Callback<Reponse_Model<Fruits_Model>>() {
        @Override
        public void onResponse(Call<Reponse_Model<Fruits_Model>> call, Response<Reponse_Model<Fruits_Model>> response) {
            if (response.isSuccessful()) {
                Log.d("123123", "onResponse: " + response.body().getStatus());
                if (response.body().getStatus()==200) {
                    Toast.makeText(Update_Fruit.this, "Sửa thành công thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<Fruits_Model>> call, Throwable t) {
            Log.e("zzzzzzzzzz", "onFailure: "+t.getMessage());
        }
    };

    private RequestBody getRequestBody(String value) {
    return RequestBody.create(MediaType.parse("multipart/form-data"),value);
}
    private void chooseImage() {
//        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getImage.launch(intent);
//        }else {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//
//        }
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        ds_image.clear();
                        Intent data = o.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();

                                File file = createFileFormUri(imageUri, "image" + i);
                                ds_image.add(file);
                            }


                        } else if (data.getData() != null) {
                            // Trường hợp chỉ chọn một hình ảnh
                            Uri imageUri = data.getData();
                            // Thực hiện các xử lý với imageUri
                            File file = createFileFormUri(imageUri, "image" );
                            ds_image.add(file);

                        }
                        Glide.with(Update_Fruit.this)
                                .load(ds_image.get(0))
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(image);
                    }
                }
            });
    private File createFileFormUri (Uri path, String name) {
        File _file = new File(Update_Fruit.this.getCacheDir(), name + ".png");
        try {
            InputStream in = Update_Fruit.this.getContentResolver().openInputStream(path);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    Callback<Reponse_Model<ArrayList<Distributor_Model>>> distributor = new Callback<Reponse_Model<ArrayList<Distributor_Model>>>() {
        @Override
        public void onResponse(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Response<Reponse_Model<ArrayList<Distributor_Model>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    distributorArrayList = response.body().getData();
                    String[] items = new String[distributorArrayList.size()];

                    for (int i = 0; i< distributorArrayList.size(); i++) {
                        items[i] = distributorArrayList.get(i).getName();
                    }
                    ArrayAdapter<String> adapterSpin = new ArrayAdapter<>(Update_Fruit.this, android.R.layout.simple_spinner_item, items);
                    adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spDistributor.setAdapter(adapterSpin);
                }
            }
        }

        @Override
        public void onFailure(Call<Reponse_Model<ArrayList<Distributor_Model>>> call, Throwable t) {
            Log.d("123123", "onItemSelected: " + t);
        }
    };

    private void configSpinner() {
        httpRequest.callAPI().get_list().enqueue(distributor);
        spDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                id_Distributor = distributorArrayList.get(position).getId();
                Log.d("123123", "onItemSelected: " + id_Distributor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDistributor.setSelection(0);
    }
}