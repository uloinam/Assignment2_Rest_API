package com.example.myapplication;

import java.util.List;

public class GHNOrder_Request {
    public int payment_type_id;
    public String note;
    public String required_note;
    public String from_name;
    public String from_phone;
    public String from_address;
    public String from_ward_name;
    public String from_district_name;
    public String from_province_name;
    public String return_phone;
    public String return_address;
    public Integer return_district_id;
    public String return_ward_code;
    public String client_order_code;
    public String to_name;
    public String to_phone;
    public String to_address;
    public String to_ward_code;
    public int to_district_id;
    public int cod_amount;
    public String content;
    public int weight;
    public int length;
    public int width;
    public int height;
    public int pick_station_id;
    public Integer deliver_station_id;
    public int insurance_value;
    public int service_id;
    public int service_type_id;
    public String coupon;
    public List<Integer> pick_shift;
    public Fruits_Model fruitsModel;

    public GHNOrder_Request(int payment_type_id, String note, String required_note, String from_name, String from_phone, String from_address, String from_ward_name, String from_district_name, String from_province_name, String return_phone, String return_address, Integer return_district_id, String return_ward_code, String client_order_code, String to_name, String to_phone, String to_address, String to_ward_code, int to_district_id, int cod_amount, String content, int weight, int length, int width, int height, int pick_station_id, Integer deliver_station_id, int insurance_value, int service_id, int service_type_id, String coupon, List<Integer> pick_shift, Fruits_Model fruitsModel) {
        this.payment_type_id = payment_type_id;
        this.note = "Vui lòng gọi khách trước khi giao hàng";
        this.required_note = "KHONGCHOXEMHANG";
        this.from_name = from_name;
        this.from_phone = from_phone;
        this.from_address = from_address;
        this.from_ward_name = from_ward_name;
        this.from_district_name = from_district_name;
        this.from_province_name = from_province_name;
        this.return_phone = "0964941802";
        this.return_address = "Lê Thanh";
        this.return_district_id = return_district_id;
        this.return_ward_code = return_ward_code;
        this.client_order_code = client_order_code;
        this.to_name = to_name;
        this.to_phone = to_phone;
        this.to_address = to_address;
        this.to_ward_code = to_ward_code;
        this.to_district_id = to_district_id;
        this.cod_amount = cod_amount;
        this.content = content;
        this.weight = 10;
        this.length = 10;
        this.width = 10;
        this.height = 10;
        this.pick_station_id = pick_station_id;
        this.deliver_station_id = deliver_station_id;
        this.insurance_value = insurance_value;
        this.service_id = 0;
        this.service_type_id = 2;
        this.coupon = coupon;
        this.pick_shift = pick_shift;
        this.fruitsModel = fruitsModel;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRequired_note() {
        return required_note;
    }

    public void setRequired_note(String required_note) {
        this.required_note = required_note;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getFrom_phone() {
        return from_phone;
    }

    public void setFrom_phone(String from_phone) {
        this.from_phone = from_phone;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getFrom_ward_name() {
        return from_ward_name;
    }

    public void setFrom_ward_name(String from_ward_name) {
        this.from_ward_name = from_ward_name;
    }

    public String getFrom_district_name() {
        return from_district_name;
    }

    public void setFrom_district_name(String from_district_name) {
        this.from_district_name = from_district_name;
    }

    public String getFrom_province_name() {
        return from_province_name;
    }

    public void setFrom_province_name(String from_province_name) {
        this.from_province_name = from_province_name;
    }

    public String getReturn_phone() {
        return return_phone;
    }

    public void setReturn_phone(String return_phone) {
        this.return_phone = return_phone;
    }

    public String getReturn_address() {
        return return_address;
    }

    public void setReturn_address(String return_address) {
        this.return_address = return_address;
    }

    public Integer getReturn_district_id() {
        return return_district_id;
    }

    public void setReturn_district_id(Integer return_district_id) {
        this.return_district_id = return_district_id;
    }

    public String getReturn_ward_code() {
        return return_ward_code;
    }

    public void setReturn_ward_code(String return_ward_code) {
        this.return_ward_code = return_ward_code;
    }

    public String getClient_order_code() {
        return client_order_code;
    }

    public void setClient_order_code(String client_order_code) {
        this.client_order_code = client_order_code;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public String getTo_phone() {
        return to_phone;
    }

    public void setTo_phone(String to_phone) {
        this.to_phone = to_phone;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTo_ward_code() {
        return to_ward_code;
    }

    public void setTo_ward_code(String to_ward_code) {
        this.to_ward_code = to_ward_code;
    }

    public int getTo_district_id() {
        return to_district_id;
    }

    public void setTo_district_id(int to_district_id) {
        this.to_district_id = to_district_id;
    }

    public int getCod_amount() {
        return cod_amount;
    }

    public void setCod_amount(int cod_amount) {
        this.cod_amount = cod_amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPick_station_id() {
        return pick_station_id;
    }

    public void setPick_station_id(int pick_station_id) {
        this.pick_station_id = pick_station_id;
    }

    public Integer getDeliver_station_id() {
        return deliver_station_id;
    }

    public void setDeliver_station_id(Integer deliver_station_id) {
        this.deliver_station_id = deliver_station_id;
    }

    public int getInsurance_value() {
        return insurance_value;
    }

    public void setInsurance_value(int insurance_value) {
        this.insurance_value = insurance_value;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(int service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public List<Integer> getPick_shift() {
        return pick_shift;
    }

    public void setPick_shift(List<Integer> pick_shift) {
        this.pick_shift = pick_shift;
    }

    public Fruits_Model getFruitsModel() {
        return fruitsModel;
    }

    public void setFruitsModel(Fruits_Model fruitsModel) {
        this.fruitsModel = fruitsModel;
    }
}
