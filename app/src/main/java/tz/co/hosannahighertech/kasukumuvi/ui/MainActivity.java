package tz.co.hosannahighertech.kasukumuvi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import tz.co.hosannahighertech.kasukumuvi.R;
import tz.co.hosannahighertech.kasukumuvi.data.adapters.MoviesAdapter;
import tz.co.hosannahighertech.kasukumuvi.ui.interfaces.ClickListener;
import tz.co.hosannahighertech.kasukumuvi.ui.viewmodel.MovieViewModel;
import tz.co.hosannahighertech.kasukumuvi.ui.viewmodel.ResponseDataList;
import tz.co.hosannahighertech.kasukumuvi.ui.viewmodel.Status;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.movieList)
    RecyclerView mMoviesList;

    @BindView(R.id.container)
    ConstraintLayout mContainerLayout;

    @BindView(R.id.delete)
    FloatingActionButton mDelete;

    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUI();

        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovies().observe(this, new Observer<ResponseDataList>() {
            @Override
            public void onChanged(@Nullable ResponseDataList resp) {
                if(resp.getStatus().equals(Status.ERROR))
                {
                    updateUi(resp.getStatus());
                    showError(resp.getError().getMessage());
                }
                else if(resp.getStatus().equals(Status.LOADING))
                {
                    updateUi(resp.getStatus());
                }
                else {
                    mAdapter.submitList(resp.getData());
                    updateUi(resp.getStatus());
                }
            }
        });

        viewModel.loadData();

        RxView.clicks(mDelete).subscribe(aVoid -> {
            ViewModelProviders.of(this).get(MovieViewModel.class).clearDatabase();
        });
    }

    private void initUI() {
        setSupportActionBar(mToolbar);

        //recyclerview
        mAdapter = new MoviesAdapter();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mMoviesList.setLayoutManager(mLayoutManager);

        mMoviesList.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration divider = new DividerItemDecoration(this, null);
        mMoviesList.addItemDecoration(divider);
        mMoviesList.setAdapter(mAdapter);

        mMoviesList.addOnItemTouchListener(new MovieTouchListerner(this, mMoviesList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int id = mAdapter.getMovieId(position);
                if (id > 0)
                {
                    Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                    intent.putExtra("MOVIE_ID", id);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void showError(String message) {
        Snackbar.make(mContainerLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.dismiss_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }

    private void updateUi(Status status) {
        switch (status)
        {
            case ERROR:
            case SUCCESS:
            {
                mProgressBar.setVisibility(View.GONE);
                break;
            }
            case LOADING:
            {
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_hint));


        RxSearchView.queryTextChanges(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(searchText -> {
                    MovieViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(MovieViewModel.class);

                    if(searchText.toString().isEmpty())
                        viewModel.loadData();
                    else
                        viewModel.search(searchText.toString().trim());

                });

        return true;
    }
}
