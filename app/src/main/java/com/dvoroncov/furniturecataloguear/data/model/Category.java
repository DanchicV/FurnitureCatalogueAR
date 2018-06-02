package com.dvoroncov.furniturecataloguear.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Category {

    public int id;
    public String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
