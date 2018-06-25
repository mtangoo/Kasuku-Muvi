package tz.co.hosannahighertech.kasukumuvi.data.models.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.models.db
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 10:52.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@Entity(tableName = "companies")
public class Company {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    public Integer id;

    @SerializedName("logo_path")
    @Expose
    public String logoPath;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("origin_country")
    @Expose
    public String originCountry;

    public Company(Integer id, String logoPath, String name, String originCountry) {
        this.id = id;
        this.logoPath = logoPath;
        this.name = name;
        this.originCountry = originCountry;
    }

    public Integer getId() {
        return id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getName() {
        return name;
    }

    public String getOriginCountry() {
        return originCountry;
    }
}
