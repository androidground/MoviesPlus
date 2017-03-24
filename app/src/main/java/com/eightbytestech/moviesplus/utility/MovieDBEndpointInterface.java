package com.eightbytestech.moviesplus.utility;

import com.eightbytestech.moviesplus.model.MoviesInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MovieDBEndpointInterface {

    @GET ("discover/movie")
    Call<MoviesInfo> discoverMovies(
            @QueryMap(encoded = true) Map<String, String> options
    );

    @GET ("movie/popular")
    Call<MoviesInfo> getPopularMovies(
            @Query(value = "api_key", encoded = true) String apiKey,
            @Query(value = "language", encoded = true) String language
    );

    //movie/top_rated
    @GET ("movie/top_rated")
    Call<MoviesInfo> getTopRatedMovies(
            @Query(value = "api_key", encoded = true) String apiKey,
            @Query(value = "language", encoded = true) String language
    );

    //movies videos
    @GET ("movie/{id}/videos")
    Call<MoviesInfo> getMovieVideos(
            @Path("id") int id,
            @Query(value = "api_key", encoded = true) String apiKey,
            @Query(value = "language", encoded = true) String language
    );

    //movies review
    @GET ("movie/{id}/reviews")
    Call<MoviesInfo> getMovieReviews(
            @Path("id") int id,
            @Query(value = "api_key", encoded = true) String apiKey,
            @Query(value = "language", encoded = true) String language
    );

}
