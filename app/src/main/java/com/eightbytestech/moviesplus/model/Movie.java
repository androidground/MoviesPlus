package com.eightbytestech.moviesplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Home on 01/02/2017.
 */

public class Movie implements Parcelable {

    @SerializedName("id")
    public int id;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("title")
    public String title;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("genres")
    public List<GenresBean> genres;


    public Movie(int id, String overview, String releaseDate, String posterPath, String backdropPath, long popularity, String title, long voteAverage, int voteCount, List<GenresBean> genres) {

        this.id = id;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = "http://image.tmdb.org/t/p/w500" + posterPath;
        this.backdropPath = "http://image.tmdb.org/t/p/w500" + backdropPath;
        this.popularity = popularity;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString("http://image.tmdb.org/t/p/w500" + posterPath);
        dest.writeString("http://image.tmdb.org/t/p/w500" + backdropPath);
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
        dest.writeArray(genres.toArray());
    }

    public Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
        this.posterPath = parcel.readString();
        this.backdropPath = parcel.readString();
        this.popularity = parcel.readFloat();
        this.title = parcel.readString();
        this.voteAverage = parcel.readFloat();
        this.voteCount = parcel.readInt();
        this.genres = parcel.readArrayList()
    }


    public static Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static class GenresBean {
        private int id;
        private String name;

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
    }

}
