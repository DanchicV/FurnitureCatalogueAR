package com.dvoroncov.furniturecataloguear.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Furniture {

    public int id;
    public String name;
    public String image;
    public String modelObjUrl;
    public String modelTextureUrl;
    public String discription;
    public float price;

    public Furniture() {
    }

    public Furniture(int id, String name, String image, String modelObjUrl, String modelTextureUrl, String discription, float price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.modelObjUrl = modelObjUrl;
        this.modelTextureUrl = modelTextureUrl;
        this.discription = discription;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModelObjUrl() {
        return modelObjUrl;
    }

    public void setModelObjUrl(String modelObjUrl) {
        this.modelObjUrl = modelObjUrl;
    }

    public String getModelTextureUrl() {
        return modelTextureUrl;
    }

    public void setModelTextureUrl(String modelTextureUrl) {
        this.modelTextureUrl = modelTextureUrl;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
