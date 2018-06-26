package tz.co.hosannahighertech.kasukumuvi.injection.components;

import dagger.Component;
import tz.co.hosannahighertech.kasukumuvi.data.repos.MoviesRepo;
import tz.co.hosannahighertech.kasukumuvi.injection.modules.AppContextModule;
import tz.co.hosannahighertech.kasukumuvi.injection.modules.DatabaseModule;
import tz.co.hosannahighertech.kasukumuvi.injection.modules.NetworkModule;
import tz.co.hosannahighertech.kasukumuvi.injection.scopes.MovieApplicationScope;
import tz.co.hosannahighertech.kasukumuvi.viewmodel.MovieViewModel;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.injection.components
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 26/06/2018 18:07.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@MovieApplicationScope
@Component(modules = {NetworkModule.class, DatabaseModule.class, AppContextModule.class})
public interface RepositoryComponent {
    MoviesRepo getRepo();

    public void inject(MovieViewModel app);
}
