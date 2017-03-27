package com.eightbytestech.moviesplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Genres implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    public Genres(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genres(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static Creator<Genres> CREATOR = new Creator<Genres>() {

        @Override
        public Genres createFromParcel(Parcel source) {
            return new Genres(source);
        }

        @Override
        public Genres[] newArray(int size) {
            return new Genres[size];
        }
    };
}
