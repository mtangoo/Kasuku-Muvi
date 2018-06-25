package tz.co.hosannahighertech.kasukumuvi.data.models.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.models.db
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 12:46.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@Database(entities = {Movie.class, Company.class}, version = 1, exportSchema = false)
public abstract class DatabaseManager extends RoomDatabase {
    private static volatile DatabaseManager INSTANCE;

    public abstract MovieDao movieDao();

    public abstract CompanyDao companyDao();

    public static DatabaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseManager.class, "movies.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
