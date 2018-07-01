package tz.co.hosannahighertech.kasukumuvi.data.repos;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import tz.co.hosannahighertech.kasukumuvi.data.api.Api;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.MovieDao;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 27/06/2018 21:40.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class ListBoundaryCallback extends BaseBoundaryCallback {


    @Inject
    public ListBoundaryCallback(MovieDao local, Api remote) {
        super(local, remote);
    }

    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     */
    @Override
    public void onZeroItemsLoaded() {
        mPage = 1;
        loadAndSave();
    }

    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within Config.prefetchDistance of it.
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */
    @Override
    public void onItemAtEndLoaded(@NonNull Movie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);

        nextPage();
        loadAndSave();
    }

    private void loadAndSave() {
        mRemote.getTopRated(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(data -> {
                    mPage = data.getPage();
                    mLocal.insertMovies(data.getResults());
                }, throwable -> {
                    Log.e("ZERO_ITEM_POPULAR_ERROR", throwable.getMessage());
                });
    }
}
