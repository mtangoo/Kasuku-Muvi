package tz.co.hosannahighertech.kasukumuvi.data.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import tz.co.hosannahighertech.kasukumuvi.R;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.adapters
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 17:04.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MoviesAdapter extends PagedListAdapter<Movie, MoviesAdapter.MoviesViewHolder> {
    public static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.title == newItem.title && oldItem.releaseDate == newItem.releaseDate;
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.overview == newItem.overview;
        }
    };

    public MoviesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie movie = getItem(position);
        if(movie!=null)
        {
            holder.getTitle().setText(movie.title);
            holder.getRelease().setText(String.format("Released on %s", movie.releaseDate));
            holder.getVoteCount().setText(String.format("Total votes %d", movie.voteCount));
            holder.getRatings().setRating(movie.voteAverage/2);

            String imageUrl = "https://image.tmdb.org/t/p/w200/"+movie.backdropPath;
            Picasso.get().load(imageUrl).resize(150, 130).centerCrop().into(holder.getPreview());
        }
        else {
            holder.getTitle().setText("");
            holder.getRelease().setText("");
            holder.getVoteCount().setText("");
            holder.getRatings().setRating(0);
            holder.getPreview().setImageBitmap(null);
        }
    }

    public int getMovieId(int position) {
        Movie movie = getItem(position);
        if (movie != null)
            return movie.id;

        return -1;
    }

    //Viewholder

    static class MoviesViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.title) TextView mTitle;
        @BindView(R.id.votes) TextView mVoteCount;
        @BindView(R.id.release) TextView mRelease;
        @BindView(R.id.ratings) RatingBar mRatings;
        @BindView(R.id.preview) ImageView mPreview;

        public MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getVoteCount() {
            return mVoteCount;
        }

        public TextView getRelease() {
            return mRelease;
        }

        public RatingBar getRatings() {
            return mRatings;
        }

        public ImageView getPreview() {
            return mPreview;
        }
    }
}
