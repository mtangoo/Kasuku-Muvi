package tz.co.hosannahighertech.kasukumuvi.data.repos;

import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import tz.co.hosannahighertech.kasukumuvi.data.api.Api;
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

    private MovieDao mLocal;
    private Api mRemote;
    private ListBoundaryCallback mListBoundaryCallback;
    private SearchBoundaryCallback mSearchCallback;

    private static PagedList.Config mConfig =
            new PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setPrefetchDistance(5)
                    .setPageSize(20).build();


    @Inject
    MoviesRepo(MovieDao local, Api remote, ListBoundaryCallback listCallback, SearchBoundaryCallback searchCallback) {
        mLocal = local;
        mRemote = remote;

        mListBoundaryCallback = listCallback;
        mSearchCallback = searchCallback;
    }


    public Flowable<PagedList<Movie>> getMovies() {
        return new RxPagedListBuilder<>(mLocal.getMovies(), mConfig)
                .setBoundaryCallback(mListBoundaryCallback)
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<PagedList<Movie>> search(String query) {
        String q = "%" + query + "%";
        return new RxPagedListBuilder<>(mLocal.searchMovies(q), mConfig)
                .setBoundaryCallback(mSearchCallback.setSearchWord(query))
                .buildFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<Movie> getMovie(final int id) {
        return Single.concat(mLocal.getMovie(id), mRemote.getMovie(id).doOnSuccess(data -> {
            mLocal.insertMovie(data);
        })).onErrorResumeNext(error -> {
            return Flowable.error(error);
        });
    }

    public Single<Integer> clearDatabase() {
        return Observable.fromCallable(() -> mLocal.deleteAll()).firstOrError();
    }
}
