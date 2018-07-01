package tz.co.hosannahighertech.kasukumuvi.ui.viewmodel;

import android.arch.paging.PagedList;

import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.models
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 13/06/2018 17:53.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */


public class ResponseDataList {

    private PagedList<Movie> mData;
    private Throwable mError;
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

    public PagedList<Movie> getData() {
        return mData;
    }

    public Throwable getError()
    {
        return mError;
    }

    public ResponseDataList setData(PagedList<Movie> data) {
        mData = data;
        return this;
    }

    public ResponseDataList setError(Throwable error) {
        mError = error;
        return this;
    }

    public ResponseDataList setStatus(Status status) {
        mStatus = status;
        return  this;
    }
}
