package tz.co.hosannahighertech.kasukumuvi.data.repos.remote;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import tz.co.hosannahighertech.kasukumuvi.data.api.Api;
import tz.co.hosannahighertech.kasukumuvi.data.models.MovieResponse;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.repos.MovieDataSource;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 14:31.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MovieRemoteSource implements MovieDataSource {

    private Api mApi;

    public MovieRemoteSource(Api api)
    {
        mApi = api;
    }

    @Override
    public Flowable<List<Movie>> allMovies() {
        return mApi.getPopular().map(MovieResponse::getResults).toFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<List<Movie>> searchMovies(String query) {
        return mApi.searchMovies(query).map(data -> data.getResults());
    }

    @Override
    public Single<Movie> getMovie(int id) {
        return mApi.getMovie(id);
    }

    @Override
    public void save(Movie entity) {
        //we are currently not allowed to save back to the server
    }

    @Override
    public void saveAll(List<Movie> movieEntities) {
        //we are currently not allowed to save back to the server
    }

    @Override
    public void deleteAll(List<Movie> movieEntities) {
        //we are currently not allowed to delete back in the server
    }
}
