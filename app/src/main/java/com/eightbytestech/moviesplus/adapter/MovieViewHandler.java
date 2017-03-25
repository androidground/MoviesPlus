package com.eightbytestech.moviesplus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eightbytestech.moviesplus.R;

/**
 * Created by Home on 01/02/2017.
 */

public class MovieViewHandler extends RecyclerView.ViewHolder {

    ImageView imageView;

    public MovieViewHandler(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
