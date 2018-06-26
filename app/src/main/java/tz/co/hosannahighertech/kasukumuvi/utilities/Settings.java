package tz.co.hosannahighertech.kasukumuvi.utilities;

import javax.inject.Inject;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.utilities
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 26/06/2018 17:47.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class Settings {

    @Inject
    public Settings() {
    }

    public String getApiKey()
    {
        return "a338b4593e956c0b9bd3098aae1547cd";
    }

    public String getBaseUrl()
    {
        return "https://api.themoviedb.org/";
    }

    public int getTimeout()
    {
        return  30000;
    }
}
