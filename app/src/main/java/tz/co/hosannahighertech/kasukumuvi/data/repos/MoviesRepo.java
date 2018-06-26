package tz.co.hosannahighertech.kasukumuvi.data.repos;

import android.util.Log;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import tz.co.hosannahighertech.kasukumuvi.data.api.Api;
import tz.co.hosannahighertech.kasukumuvi.data.models.MovieResponse;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.MovieDao;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 15:58.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MoviesRepo {
    private static volatile MoviesRepo INSTANCE;

    private MovieDao mLocal;
    private Api mRemote;

    private MoviesRepo(MovieDao local, Api remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static MoviesRepo getInstance(MovieDao local, Api remote) {
        if (INSTANCE == null) {
            synchronized (MoviesRepo.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesRepo(local, remote);
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<List<Movie>> getMovies() {
        return mLocal.getMovies()
                .take(1)
                .filter(list -> !list.isEmpty())
                .switchIfEmpty(mRemote.getPopular()
                        .doOnSuccess(data -> {
                            for (Movie m : data.getResults())
                                mLocal.insertMovie(m);
                        })
                        .map(movieResponse -> movieResponse.getResults())
                        .toFlowable());
    }

    public Flowable<Movie> getMovie(final int id) {
        return Single.concat(mLocal.getMovie(id), mRemote.getMovie(id).doOnSuccess(data -> {
            mLocal.insertMovie(data);
        })).onErrorResumeNext(error -> {
            return Flowable.error(error);
        });
    }

    public Flowable<List<Movie>> search(String query) {
        return mLocal.searchMovies("%"+query+"%").take(1)
                .filter(list -> !list.isEmpty())
                .switchIfEmpty(mRemote.searchMovies(query)
                        .map(movieResponse -> {
                            for (Movie m : movieResponse.getResults())
                                mLocal.insertMovie(m);

                            return movieResponse.getResults();
                        })
                        .toFlowable()).onErrorResumeNext((Function<Throwable, Publisher<? extends List<Movie>>>) Flowable::error);
    }
}
