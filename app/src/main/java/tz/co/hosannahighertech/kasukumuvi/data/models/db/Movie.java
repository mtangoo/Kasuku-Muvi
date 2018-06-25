package tz.co.hosannahighertech.kasukumuvi.data.models.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.models.db
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 11/06/2018 18:50.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */


@Entity(tableName = "movies")
public class Movie {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    public int id;

    @SerializedName("adult")
    @Expose
    public boolean adult;

    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;

    @SerializedName("budget")
    @Expose
    public int budget;

    @SerializedName("homepage")
    @Expose
    public String homepage;

    @SerializedName("original_title")
    @Expose
    public String originalTitle;

    @SerializedName("overview")
    @Expose
    public String overview;

    @SerializedName("popularity")
    @Expose
    public Float popularity;

    @SerializedName("poster_path")
    @Expose
    public String posterPath;

    @SerializedName("production_companies")
    @Expose
    @Ignore
    public List<Company> productionCompanies = null;

    @SerializedName("release_date")
    @Expose
    public String releaseDate;

    @SerializedName("revenue")
    @Expose
    public int revenue;

    @SerializedName("runtime")
    @Expose
    public int runtime;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("video")
    @Expose
    public boolean video;

    @SerializedName("vote_average")
    @Expose
    public Float voteAverage;

    @SerializedName("vote_count")
    @Expose
    public int voteCount;


    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setProductionCompanies(List<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
