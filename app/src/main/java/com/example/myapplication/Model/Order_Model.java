package com.example.myapplication.Model;

public class Order_Model {
    String order_code, _id, id_user;

    public Order_Model(String order_code, String _id, String id_user) {
        this.order_code = order_code;
        this._id = _id;
        this.id_user = id_user;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
