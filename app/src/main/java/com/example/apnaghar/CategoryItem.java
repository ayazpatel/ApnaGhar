package com.example.apnaghar;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryItem implements Parcelable {
    private String id;
    private String BS_Type;
    private String Landmark;
    private String Price;
    private String BS_For;
    private String Image1;

    public CategoryItem(String id, String BS_Type, String Landmark, String Price, String BS_For, String Image1) {
        this.id = id;
        this.BS_Type = BS_Type;
        this.Landmark = Landmark;
        this.Price = Price;
        this.BS_For = BS_For;
        this.Image1 = Image1;
    }

    protected CategoryItem(Parcel in) {
        id = in.readString();
        BS_Type = in.readString();
        Landmark = in.readString();
        Price = in.readString();
        BS_For = in.readString();
        Image1 = in.readString();
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(BS_Type);
        parcel.writeString(Landmark);
        parcel.writeString(Price);
        parcel.writeString(BS_For);
        parcel.writeString(Image1);
    }
}
