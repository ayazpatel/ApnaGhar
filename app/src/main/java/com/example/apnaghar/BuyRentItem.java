package com.example.apnaghar;

public class BuyRentItem {
    private String id;
    private String BS_Type;
    private String Landmark;
    private String Price;
    private String BS_For;
    private String Image1;

    public BuyRentItem(String id, String BS_Type, String Landmark, String Price, String BS_For, String Image1) {
        this.id = id;
        this.BS_Type = BS_Type;
        this.Landmark = Landmark;
        this.Price = Price;
        this.BS_For = BS_For;
        this.Image1 = Image1;
    }

    public String getId() {
        return id;
    }

    public String getBS_Type() {
        return BS_Type;
    }

    public String getLandmark() {
        return Landmark;
    }

    public String getPrice() {
        return Price;
    }

    public String getBS_For() {
        return BS_For;
    }

    public String getImage1() {
        return Image1;
    }
}
