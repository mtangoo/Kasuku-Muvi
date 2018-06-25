package tz.co.hosannahighertech.kasukumuvi.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.models
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 14:59.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class MovieResponse {

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("results")
    @Expose
    public List<Movie> results = null;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }
}