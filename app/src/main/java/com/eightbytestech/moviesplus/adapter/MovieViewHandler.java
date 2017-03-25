package com.eightbytestech.moviesplus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eightbytestech.moviesplus.R;

/**
 * Created by Home on 01/02/2017.
 */

public class MovieViewHandler extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView movieTitleView;
    TextView movieGenreView;

    public MovieViewHandler(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        movieTitleView = (TextView) itemView.findViewById(R.id.movieTitleView);
        movieGenreView = (TextView) itemView.findViewById(R.id.movieGenreView);
    }
}
