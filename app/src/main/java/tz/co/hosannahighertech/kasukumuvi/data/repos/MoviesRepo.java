package tz.co.hosannahighertech.kasukumuvi.data.repos;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 15:58.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MoviesRepo {
    private static volatile MoviesRepo INSTANCE;

    private MovieDataSource mLocal;
    private MovieDataSource mRemote;

    private MoviesRepo(MovieDataSource local, MovieDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static MoviesRepo getInstance(MovieDataSource local, MovieDataSource remote) {
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
        return mLocal.allMovies().take(1)
                .filter(list -> !list.isEmpty())
                .switchIfEmpty(mRemote.allMovies()
                        .doOnNext(data -> mLocal.saveAll(data))
                );
    }

    public Flowable<Movie> getMovie(final int id) {
        //return mLocal.getMovie(id).toMaybe().switchIfEmpty(mRemote.getMovie(id));
        return Single.concatArray(mLocal.getMovie(id), mRemote.getMovie(id).doOnSuccess(data -> {
            mLocal.save(data);
        })).onErrorResumeNext(error->{
            return mLocal.getMovie(id).toFlowable();
        });
    }

    public Flowable<List<Movie>> search(String query) {
        return mLocal.searchMovies(query).take(1)
                .filter(list -> !list.isEmpty())
                .switchIfEmpty(mRemote.searchMovies(query).doOnNext(data -> mLocal.saveAll(data)));
    }
}
