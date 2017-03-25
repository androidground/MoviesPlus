package com.eightbytestech.moviesplus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eightbytestech.moviesplus.model.Movie;
import com.eightbytestech.moviesplus.utility.ApiUtility;
import com.eightbytestech.moviesplus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Home on 01/02/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHandler> {

    List<Movie> movieList;
    private LayoutInflater mLayouInflater;
    private Context mContext;

    public MovieAdapter(Context context) {
        mContext = context;
        mLayouInflater = LayoutInflater.from(context);
    }

    public void setMovieList(List<Movie> movies) {
        if ( movies != null ) {
            this.movieList = new ArrayList<>();
            this.movieList.addAll(movies);
            notifyDataSetChanged();
        }
    }

    @Override
    public MovieViewHandler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayouInflater.inflate(R.layout.movie_row, parent, false);
        final MovieViewHandler movieViewHandler = new MovieViewHandler(view);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int moviePosition = movieViewHandler.getAdapterPosition();
                /*Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movieList.get(moviePosition));
                mContext.startActivity(intent);*/
            }
        });
        return movieViewHandler;
    }

    @Override
    public void onBindViewHolder(MovieViewHandler holder, int position) {
        Movie movie = movieList.get(position);
        String imagePath = ApiUtility.MovieDbUtility.getCompletePhotoUrl(movie.posterPath);
        Picasso.with(mContext)
                //.load("http://image.tmdb.org/t/p/w185" + movie.posterPath)
                .load(imagePath)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if ( movieList == null ) {
            return 0;
        } else {
            return movieList.size();
        }
    }
}
