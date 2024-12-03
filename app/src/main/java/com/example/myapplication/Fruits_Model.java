package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Fruits_Model implements Serializable {
    private String _id, Price, Name, Status, description, quantity;
    private ArrayList<String> Image;

    @SerializedName("id_distributor")
    private Distributor_Model distributorModel;
    private String createdAt,updatedAt;

    public Fruits_Model(String _id, String price, String name, String status, String description, ArrayList<String> image, Distributor_Model distributorModel,String quantity, String createdAt, String updatedAt) {
        this._id = _id;
        Price = price;
        Name = name;
        Status = status;
        this.description = description;
        Image = image;
        this.distributorModel = distributorModel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImage() {
        return Image;
    }

    public void setImage(ArrayList<String> image) {
        Image = image;
    }

    public Distributor_Model getDistributorModel() {
        return distributorModel;
    }

    public void setDistributorModel(Distributor_Model distributorModel) {
        this.distributorModel = distributorModel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
