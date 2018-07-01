package tz.co.hosannahighertech.kasukumuvi.data.repos;

import android.arch.paging.PagedList;

import tz.co.hosannahighertech.kasukumuvi.data.api.Api;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.MovieDao;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 30/06/2018 15:09.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

class BaseBoundaryCallback extends PagedList.BoundaryCallback<Movie> {

    Api mRemote;
    MovieDao mLocal;
    int mPage;


    public BaseBoundaryCallback(MovieDao local, Api remote) {
        mRemote = remote;
        mLocal = local;

        mPage = 0;
    }

    public void nextPage() {
        mPage++;
    }

    public void setPage(int page) {
        mPage = page;
    }
}
