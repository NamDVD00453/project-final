package com.vinamine.mc.rest;

import android.graphics.Bitmap;

public class VoucherForView {
    private String id;
    private String name;
    private String store;
    private Bitmap icon;
    private Bitmap image;
    private String percent;

    public VoucherForView(String id, String name, String store, Bitmap icon, Bitmap image, String percent) {
        this.id = id;
        this.name = name;
        this.store = store;
        this.icon = icon;
        this.image = image;
        this.percent = percent;
    }

    public VoucherForView() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
