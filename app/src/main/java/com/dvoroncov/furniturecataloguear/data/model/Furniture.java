package com.dvoroncov.furniturecataloguear.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Furniture implements Parcelable {

    private int id;
    private String name;
    private String image;
    private String modelObjUrl;
    private String modelTextureUrl;
    private String description;
    private float price;

    public Furniture() {
    }

    public Furniture(int id, String name, String image, String modelObjUrl, String modelTextureUrl, String description, float price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.modelObjUrl = modelObjUrl;
        this.modelTextureUrl = modelTextureUrl;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.modelObjUrl);
        dest.writeString(this.modelTextureUrl);
        dest.writeString(this.description);
        dest.writeFloat(this.price);
    }

    protected Furniture(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.modelObjUrl = in.readString();
        this.modelTextureUrl = in.readString();
        this.description = in.readString();
        this.price = in.readFloat();
    }

    public static final Parcelable.Creator<Furniture> CREATOR = new Parcelable.Creator<Furniture>() {
        @Override
        public Furniture createFromParcel(Parcel source) {
            return new Furniture(source);
        }

        @Override
        public Furniture[] newArray(int size) {
            return new Furniture[size];
        }
    };
}
