package tz.co.hosannahighertech.kasukumuvi.viewmodel;

import java.util.List;

import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.viewmodel
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 23/06/2018 18:39.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */


public class ResponseDataSingle {

    private Movie mData;
    private Throwable mError;
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

    public Movie getData() {
        return mData;
    }

    public Throwable getError()
    {
        return mError;
    }

    public ResponseDataSingle setData(Movie data) {
        mData = data;
        return this;
    }

    public ResponseDataSingle setError(Throwable error) {
        mError = error;
        return this;
    }

    public ResponseDataSingle setStatus(Status status) {
        mStatus = status;
        return  this;
    }
}
