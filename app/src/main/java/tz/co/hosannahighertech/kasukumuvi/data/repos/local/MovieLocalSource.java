package tz.co.hosannahighertech.kasukumuvi.data.repos.local;

import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.MovieDao;
import tz.co.hosannahighertech.kasukumuvi.data.repos.MovieDataSource;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 13:24.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MovieLocalSource implements MovieDataSource {

    MovieDao mDao;

    public MovieLocalSource(MovieDao dao) {
        mDao = dao;
    }

    @Override
    public Flowable<List<Movie>> allMovies() {
        return mDao.getMovies().doOnNext(new Consumer<List<Movie>>() {
            @Override
            public void accept(List<Movie> movieEntities) throws Exception {
                Log.d("ROOM DATA", "Fetched "+movieEntities.size()+" Movie Entities");
            }
        });
    }

    @Override
    public Flowable<List<Movie>> searchMovies(String query) {
        query = "%"+query+"%";
        return mDao.searchMovies(query);
    }

    @Override
    public Single<Movie> getMovie(int id) {
        return mDao.getMovie(id);
    }

    @Override
    public void save(Movie entity) {
        mDao.insertMovie(entity);
    }

    @Override
    public void saveAll(List<Movie> movieEntities) {
        Movie[] items = movieEntities.toArray(new Movie[movieEntities.size()]);
        mDao.insertMovies(items);
        Log.d("ROOM DATA", "Saved "+movieEntities.size()+" Movie Entities");
    }

    @Override
    public void deleteAll(List<Movie> movieEntities) {
        Movie[] items = movieEntities.toArray(new Movie[movieEntities.size()]);
        mDao.deleteMultiple(items);
    }
}
