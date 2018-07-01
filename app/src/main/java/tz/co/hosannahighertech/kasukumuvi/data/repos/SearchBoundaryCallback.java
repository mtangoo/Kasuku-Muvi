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

public class SearchBoundaryCallback extends BaseBoundaryCallback {
    String mSearch = "";
    private boolean mNoMoreResults;

    @Inject
    public SearchBoundaryCallback(MovieDao local, Api remote) {
        super(local, remote);
    }

    public SearchBoundaryCallback setSearchWord(String sw) {
        //if search key have changed, reset page number
        if (!mSearch.equals(sw)) {
            setPage(0);
            mNoMoreResults = false;
        }

        mSearch = sw;
        return this;
    }

    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     */
    @Override
    public void onZeroItemsLoaded() {
        searchAndSave();
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
        searchAndSave();
    }

    private void searchAndSave() {
        if (mNoMoreResults)
            return;

        nextPage(); //search results in the next page

        mRemote.searchMovies(mSearch, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(data -> {
                            mLocal.insertMovies(data.getResults());
                            mPage = data.getPage();

                            if (data.getTotalResults() == 0)
                                mNoMoreResults = true;
                        },
                        throwable -> {
                            Log.e("SEARCH_MOVIE_ERROR", throwable.getMessage());
                        });
    }
}
