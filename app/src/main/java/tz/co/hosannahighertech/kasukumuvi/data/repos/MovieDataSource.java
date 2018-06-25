package tz.co.hosannahighertech.kasukumuvi.data.repos;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import tz.co.hosannahighertech.kasukumuvi.data.models.db.Movie;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.repos
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 13:05.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public interface MovieDataSource {
    public Flowable<List<Movie>> allMovies();
    public Flowable<List<Movie>> searchMovies(String query);
    public Single<Movie> getMovie(int id);
    public void save(Movie entity);

    void saveAll(List<Movie> movieEntities);

    void deleteAll(List<Movie> movieEntities);
}
