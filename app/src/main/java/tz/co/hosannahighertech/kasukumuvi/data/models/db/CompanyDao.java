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
 * Created at 12/06/2018 12:32.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@Dao
public interface CompanyDao {
    @Query("SELECT * FROM companies")
    public Flowable<List<Company>> getCompanies();

    @Query("SELECT * FROM companies WHERE id = :movieId")
    public Single<Company> getCompany(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Company entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCompanies(Company... entities);

    @Delete
    public void delete(Company entity);

    @Delete
    public void deleteMultiple(Company... entities);
}
