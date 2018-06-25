package tz.co.hosannahighertech.kasukumuvi.data.models.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.models.db
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 11:03.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    public Flowable<List<Movie>> getMovies();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    public Single<Movie> getMovie(int movieId);

    @Query("SELECT * FROM movies WHERE title LIKE :title")
    public Flowable<List<Movie>> searchMovies(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(Movie... entities);

    @Delete
    public void delete(Movie entity);

    @Delete
    public void deleteMultiple(Movie... entities);
}
