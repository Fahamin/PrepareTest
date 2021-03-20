package com.fahamin.transcomtest.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favModel")
public class FavModel {


    @PrimaryKey
    public int id;
    @ColumnInfo(name = "pname")
    public String product_name;
    @ColumnInfo(name = "quan")
    public String total_quantity;
    @ColumnInfo(name = "rate")
    public String price_rate;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "t")
    public String time;
    @ColumnInfo(name = "sn")
    public String supplierName;
    @ColumnInfo(name = "sp")
    public String supplierPhone;
    @ColumnInfo(name = "im")
    public String product_image;


    public FavModel() {
    }

    public FavModel(int id, String product_name, String total_quantity, String price_rate, String date, String time, String supplierName, String supplierPhone, String product_image) {
        this.id = id;
        this.product_name = product_name;
        this.total_quantity = total_quantity;
        this.price_rate = price_rate;
        this.date = date;
        this.time = time;
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.product_image = product_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getproduct_name() {
        return product_name;
    }

    public void setproduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String gettotal_quantity() {
        return total_quantity;
    }

    public void settotal_quantity(String total_quantity) {
        this.total_quantity = total_quantity;
    }

    public String getPrice_rate() {
        return price_rate;
    }

    public void setPrice_rate(String price_rate) {
        this.price_rate = price_rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}