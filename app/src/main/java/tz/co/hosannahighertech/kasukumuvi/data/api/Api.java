package tz.co.hosannahighertech.kasukumuvi.data.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tz.co.hosannahighertech.kasukumuvi.data.models.MovieResponse;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.api
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 14:36.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public interface Api {
    @GET("/3/movie/top_rated?include_adult=false")
    Single<MovieResponse> getTopRated(@Query("page") int pageNumber);

    @GET("/3/search/movie?include_adult=false")
    Single<MovieResponse> searchMovies(@Query("query") String query, @Query("page") int pageNumber);

    @GET("/3/movie/{movie_id}")
    Single<Movie> getMovie(@Path("movie_id") int id);
}
