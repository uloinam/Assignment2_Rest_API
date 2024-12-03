package com.example.myapplication.Model;

public class DistrictRequest {
    private int ProvinceID;

    public DistrictRequest(int provinceID) {
        ProvinceID = provinceID;
    }

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }
}
