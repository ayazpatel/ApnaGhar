package com.example.apnaghar;

public class MyBuyingItem {
    private String id;
    private String bsType;
    private String createdAt;
    private String price;
    private String image;
    private boolean isSold;

    public MyBuyingItem(String id, String bsType, String createdAt, String price, String image) {
        this.id = id;
        this.bsType = bsType;
        this.createdAt = createdAt;
        this.price = price;
        this.image = image;
        this.isSold = false;
    }
    public void setSold(boolean sold) {
        isSold = sold;
    }

    public boolean isSold() {
        return isSold;
    }

    public String getId() {
        return id;
    }

    public String getBsType() {
        return bsType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
