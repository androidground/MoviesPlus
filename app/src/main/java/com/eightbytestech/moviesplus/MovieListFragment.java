package com.eightbytestech.moviesplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eightbytestech.moviesplus.adapter.MovieAdapter;
import com.eightbytestech.moviesplus.model.Movie;
import com.eightbytestech.moviesplus.model.MoviesInfo;
import com.eightbytestech.moviesplus.utility.ApiUtility;
import com.eightbytestech.moviesplus.utility.MovieDBEndpointInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieListFragment extends Fragment {
    private static final String TAG = "#MovieListFragment: ";

    public static final String BUNDLE_KEY_FAVORITES = "faves";
    public static final String BUNDLE_KEY_MOVIE_LIST = "movie list key";

    public Context fragmentContext;

    private String tmdb_end_point;

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessage;

    private String apiKey;
    private String language;
    private String posterEndPoint;
    private String posterEndPointSize;

    private ApiUtility apiUtility;

    private MovieDBEndpointInterface movieDBEndpointInterface;

    private View mView;
    private OnMoviePosterSelectedListener mCallback;
    private List<Movie> mMovieList;

    /*whether using offline data from the Favorites Provider */
    private boolean mUsingOfflineData;
    private int pageNumber;

    /*An int to hold status: which set of movies are currently being viewed */
    private int currentMovieSet;

    /*Constant ints to indicate status */
    private static final int POPULAR_SET = 100;
    private static final int TOP_RATED_SET = 101;
    private static final int OFFLINE_SET = 102;

    public MovieListFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentContext = context;
        try {
            mCallback = (OnMoviePosterSelectedListener) context;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassCastException(context.toString()
                    + " must implement OnPosterSelectedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    /**
     * Depending on the option selected, get which set of data
     *
     * @param item The menu item selected
     * @return always false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sort_popularity) {
            fetchPopularMovies();
        } else if (item.getItemId() == R.id.menu_sort_rating) {
            fetchTopRatedMovies();
        } else if (item.getItemId() == R.id.menu_show_favorites) {
            fetchFavoritesMovies();
        }
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.i(TAG, "onCreate");
        setRetainInstance(true);

        tmdb_end_point = getResources().getString(R.string.moviedb_endpoint);

        apiKey = getResources().getString(R.string.moviedb_api_key);

        language = getResources().getString(R.string.moviedb_language);

        posterEndPoint = getResources().getString(R.string.moviedb_poster_endpoint);

        posterEndPointSize = getResources().getString(R.string.moviedb_poster_size);

        mErrorMessage = (TextView) getActivity().findViewById(R.id.error_message);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        movieAdapter = new MovieAdapter(getActivity());

        mRecyclerView.setAdapter(movieAdapter);

        ApiUtility.setMovieDbApiValues(tmdb_end_point, apiKey, language, posterEndPoint, posterEndPointSize);

        movieDBEndpointInterface = ApiUtility.MovieDbUtility.getMovieDbEndpointInterface();

        fetchPopularMovies();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.movie_list, container, false);
        mView = rootView;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mMovieList = savedInstanceState.getParcelableArrayList(BUNDLE_KEY_MOVIE_LIST);
            mUsingOfflineData = savedInstanceState.getBoolean(BUNDLE_KEY_FAVORITES);
            //populateRecyclerView();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie list key", (ArrayList<? extends Parcelable>) mMovieList);
        outState.putBoolean(BUNDLE_KEY_FAVORITES, mUsingOfflineData);
    }

    private void fetchPopularMovies() {
        Call<MoviesInfo> popularMovies = movieDBEndpointInterface.getPopularMovies(apiKey, language);
        popularMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    updateMovieAdapter(response.body().movieList);
                else
                    showErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchTopRatedMovies() {
        Call<MoviesInfo> popularMovies = movieDBEndpointInterface.getTopRatedMovies(apiKey, language);
        popularMovies.enqueue(new Callback<MoviesInfo>() {
            @Override
            public void onResponse(Call<MoviesInfo> call, Response<MoviesInfo> response) {
                if ( response.isSuccessful() )
                    updateMovieAdapter(response.body().movieList);
                else
                    showErrorMessage();
            }

            @Override
            public void onFailure(Call<MoviesInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchFavoritesMovies() {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        this.startActivity(intent);
    }

    private void updateMovieAdapter(List<Movie> movies) {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

        movieAdapter.setMovieList(movies);
    }

    private void showErrorMessage() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public interface OnMoviePosterSelectedListener {
        void onMoviePosterSelected(Movie currentMovie);
    }
}
