package com.manguitostudios.primeblend.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manguitodeveloper01 on 10/8/15.
 */
public class Product implements Parcelable {
    public String product_id;
    public String name;
    public String description;
    public String price;
    public String currency;
    public String pdf;
    public String image;
    public String thumbnail;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Product(){}

    public Product(String product_id, String name, String description, String price,
                   String currency, String pdf, String image, String thumbnail) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.pdf = pdf;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(currency);
        dest.writeString(pdf);
        dest.writeString(image);
        dest.writeString(thumbnail);
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product(Parcel in) {

        ReadFromParcel(in);
    }

    private void ReadFromParcel(Parcel in) {
        product_id = in.readString();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        currency = in.readString();
        pdf = in.readString();
        image = in.readString();
        thumbnail = in.readString();
    }
}