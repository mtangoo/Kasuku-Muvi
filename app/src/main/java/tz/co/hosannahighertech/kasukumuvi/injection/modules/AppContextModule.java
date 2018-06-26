package tz.co.hosannahighertech.kasukumuvi.injection.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import tz.co.hosannahighertech.kasukumuvi.injection.scopes.MovieApplicationScope;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.injection.modules
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 26/06/2018 19:02.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@Module
public class AppContextModule {

    private Context mContext;

    public AppContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @MovieApplicationScope
    Context provideContext()
    {
        return mContext;
    }
}
