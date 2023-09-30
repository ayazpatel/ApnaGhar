package com.example.apnaghar;

public class Buy_MyDataModel {
    private String id;
    private String BS_Type;
    private String BS_Sub_Type;
    private String BS_Sub_Type2;
    private String BS_For;
    private String landmark;
    private String image1;
    private String price;
    private String is_Featured;

    public Buy_MyDataModel(String id, String BS_Type, String BS_Sub_Type, String BS_Sub_Type2, String BS_For, String landmark, String image1, String price, String is_Featured) {
        this.id = id;
        this.BS_Type = BS_Type;
        this.BS_Sub_Type = BS_Sub_Type;
        this.BS_Sub_Type2 = BS_Sub_Type2;
        this.BS_For = BS_For;
        this.landmark = landmark;
        this.image1 = image1;
        this.price = price;
        this.is_Featured = is_Featured;
    }

    public String getId() {
        return id;
    }

    public String getBS_Type() {
        return BS_Type;
    }

    public String getBS_Sub_Type() {
        return BS_Sub_Type;
    }
    public String getBS_Sub_Type2() {
        return BS_Sub_Type2;
    }
    public String getBS_For() {
        return BS_For;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getImage1() {
        return image1;
    }

    public String getPrice() {
        return price;
    }

    public String getIs_Featured() {
        return is_Featured;
    }
}
