package com.eightbytestech.moviesplus;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.eightbytestech.moviesplus.model.Movie;


/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity implements MovieListFragment.OnMoviePosterSelectedListener {

    private final static String TAG = "#PopularMovies: ";

    public static final String INSTANCE_STATE_TAG = "heres the movie";
    private static final String MASTER_FRAGMENT_TAG = "master frag tag";
    public static final String MASTER_FRAGMENT_BUNDLE_ID = "movieFragment";
    MovieListFragment mMasterFragment;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        /*
        restore the reference to the saved fragment otherwise make a new
        fragment
         */
        if (savedInstanceState != null) {
            mMasterFragment = (MovieListFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, MASTER_FRAGMENT_BUNDLE_ID);
        } else {
            mMasterFragment = new MovieListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_list_fragment, mMasterFragment, MASTER_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onMoviePosterSelected(Movie currentMovie) {
        if (mTwoPane) {
            /*
            pass the current Movie as a parcelable extra and add a DetailFragment
            to the RHS of the ContentView
             */
            /*Bundle arguments = new Bundle();
            arguments.putParcelable(INSTANCE_STATE_TAG, currentMovie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();*/

            Toast.makeText(this, "Selected Movie is: " + currentMovie.title, Toast.LENGTH_LONG).show();

        } else {
             /*
            pass the current Movie as an extra and start a new
            DetailActivity
             */
            /*Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(INSTANCE_STATE_TAG, currentMovie);
            startActivity(intent);*/

            Toast.makeText(this, "Selected Movie is: " + currentMovie.title, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, MASTER_FRAGMENT_BUNDLE_ID, mMasterFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, MASTER_FRAGMENT_BUNDLE_ID, mMasterFragment);
    }

    /*private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }*/

    /*public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movie_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }*/
}
