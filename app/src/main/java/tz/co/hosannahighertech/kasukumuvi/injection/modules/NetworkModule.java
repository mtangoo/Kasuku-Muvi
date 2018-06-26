package tz.co.hosannahighertech.kasukumuvi.injection.modules;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tz.co.hosannahighertech.kasukumuvi.data.api.Api;
import tz.co.hosannahighertech.kasukumuvi.data.api.EmbedApiKeyInterceptor;
import tz.co.hosannahighertech.kasukumuvi.injection.scopes.MovieApplicationScope;
import tz.co.hosannahighertech.kasukumuvi.utilities.Settings;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.injection.modules
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 26/06/2018 17:10.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

@Module
public class NetworkModule {

    @Provides
    OkHttpClient provideHttpClient(Settings settings, EmbedApiKeyInterceptor keyInterceptor)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(settings.getTimeout(), TimeUnit.MILLISECONDS);
        httpClient.writeTimeout(settings.getTimeout(), TimeUnit.MILLISECONDS);

        httpClient.addInterceptor(keyInterceptor);

        return httpClient.build();
    }

    @Provides
    Interceptor provideApiKeyInterceptor(Settings settings)
    {
        return new EmbedApiKeyInterceptor(settings);
    }

    @MovieApplicationScope
    @Provides
    Api provideRetrofit(OkHttpClient httpClient, Settings settings)
    {
        return new Retrofit.Builder()
                .baseUrl(settings.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build().create(Api.class);
    }
}
