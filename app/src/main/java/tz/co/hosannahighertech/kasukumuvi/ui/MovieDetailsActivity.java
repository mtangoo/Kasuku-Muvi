package tz.co.hosannahighertech.kasukumuvi.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import tz.co.hosannahighertech.kasukumuvi.R;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.ui.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.main_layout)
    ConstraintLayout mLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.preview)
    ImageView mPreview;

    @BindView(R.id.summary)
    TextView mSummary;

    @BindView(R.id.release)
    TextView mRelease;

    @BindView(R.id.budget)
    TextView mBudget;

    @BindView(R.id.votes)
    TextView mVotes;

    @BindView(R.id.ratings)
    RatingBar mRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        MovieViewModel vm = ViewModelProviders.of(this).get(MovieViewModel.class);
        vm.getMovieData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie data) {
                updateMovieDetails(data);
            }
        });

        vm.getMovieError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable error) {
                updateError(error);
            }
        });

        int id = getIntent().getIntExtra("MOVIE_ID", 0);
        vm.loadMovie(id);
    }

    private void updateError(Throwable error) {
        Snackbar.make(mLayout, getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    private void updateMovieDetails(Movie movie) {
        getSupportActionBar().setTitle(movie.title);

        mSummary.setText(movie.overview);
        mVotes.setText(String.format("Total votes %d", movie.voteCount));

        long hours = TimeUnit.MINUTES.toHours(movie.runtime);
        long remainMinute = movie.runtime - TimeUnit.HOURS.toMinutes(hours);
        String result = String.format("%02d", hours) + ":" + String.format("%02d", remainMinute);
        mRelease.setText(String.format("Released %s, Running time %s", movie.releaseDate, result));

        mBudget.setText(String.format("Budget $%.2f mil, Revenue $%.2f mil", movie.budget/1000000.0, movie.revenue/1000000.0));
        mRatings.setRating(movie.voteAverage/2);

        String imageUrl = "https://image.tmdb.org/t/p/w400/"+movie.backdropPath;
        Picasso.get().load(imageUrl).fit().centerCrop().into(mPreview);
    }

}
