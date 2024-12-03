package com.example.myapplication;

import java.io.Serializable;

public class Distributor_Model implements Serializable {
    private String _id;
    private String Name;

    public Distributor_Model(String _id, String name) {
        this._id = _id;
        Name = name;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = _id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
