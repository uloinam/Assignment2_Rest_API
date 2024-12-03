package com.example.myapplication;

import com.example.myapplication.Model.District;
import com.example.myapplication.Model.DistrictRequest;
import com.example.myapplication.Model.Province;
import com.example.myapplication.Model.ResponseGHN;
import com.example.myapplication.Model.Ward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Service {



    @GET("/api/list_distributor")
    Call<Reponse_Model<ArrayList<Distributor_Model>>> get_list();

    @GET("/api/seachr_distributor")
    Call<Reponse_Model<ArrayList<Distributor_Model>>> get_list_search(@Query("key") String key);

    @DELETE("/api/delete_distributor/{id}")
    Call<Reponse_Model<ArrayList<Distributor_Model>>> delete(@Path("id") String id);

    @POST("/api/add_distributor")
    Call<Reponse_Model<ArrayList<Distributor_Model>>> add(@Body Distributor_Model distributorModel);

    @PUT("/api/update_distributor/{id}")
    Call<Reponse_Model<ArrayList<Distributor_Model>>> update(@Path("id") String id,@Body Distributor_Model distributorModel);

    @Multipart
    @POST("/api/add_user")
    Call<Reponse_Model<User>> new_user(@Part("username") RequestBody username,
                                       @Part("password") RequestBody password,
                                       @Part("email") RequestBody email,
                                       @Part("name") RequestBody name,
                                       @Part MultipartBody.Part avatar);
    @POST("/api/login")
    Call<Reponse_Model<User>> login(@Body User user);

    @GET("api/get-list-fruit")
    Call<Reponse_Model<ArrayList<Fruits_Model>>> get_all_listFruit();

    @Multipart
    @POST("/api/add-fruit")
    Call<Reponse_Model<Fruits_Model>> addFruit(@PartMap Map<String, RequestBody> requestBody_Map,
                                                          @Part ArrayList<MultipartBody.Part> list_MultipartBody);
    @DELETE("/api/delete_Fruit/{id}")
    Call<Reponse_Model<Fruits_Model>> deleteFruits(@Path("id") String id);


    @Multipart
    @PUT("/api/update-fruit-by-id/{id}")
    Call<Reponse_Model<Fruits_Model>> updateFruitWithFileImage(@PartMap Map<String, RequestBody> requestBodyMap,
                                                   @Path("id") String id,
                                                   @Part ArrayList<MultipartBody.Part> ds_hinh
    );

    public static String GHN_URL = "https://online-gateway.ghn.vn";

    @GET("/shiip/public-api/master-data/province")
    Call<ResponseGHN<ArrayList<Province>>> getListProvince();

    @POST("/shiip/public-api/master-data/district")
    Call<ResponseGHN<ArrayList<District>>> getListDistrict(@Body DistrictRequest districtRequest);

    @GET("/shiip/public-api/master-data/ward")
    Call<ResponseGHN<ArrayList<Ward>>> getListWard(@Query("district_id") int district_id);

    @POST("/shiip/public-api/v2/shipping-order/create")
    Call<ResponseGHN<GHNOrder_Respone>> create_Order(@Body GHNOrder_Request ghnOrderRequest);


}
