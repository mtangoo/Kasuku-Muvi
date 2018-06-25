package tz.co.hosannahighertech.kasukumuvi.data.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.api
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 12/06/2018 14:51.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class WebService {
    private static volatile Api INSTANCE;

    public static Api getApi() {
        if (INSTANCE == null) {
            synchronized (Api.class) {
                if (INSTANCE == null) {
                    //Intercept and add API Key
                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    httpClient.addInterceptor(logging);
                    httpClient.readTimeout(30000, TimeUnit.MILLISECONDS);
                    httpClient.writeTimeout(30000, TimeUnit.MILLISECONDS);

                    httpClient.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            HttpUrl originalHttpUrl = original.url();

                            HttpUrl url = originalHttpUrl.newBuilder()

                                    .addQueryParameter("api_key", "a338b4593e956c0b9bd3098aae1547cd")
                                    .build();

                            // Request customization: add request headers
                            Request.Builder requestBuilder = original.newBuilder().url(url);

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    });

                    INSTANCE = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(httpClient.build())
                            .build()
                            .create(Api.class);
                }
            }
        }
        return INSTANCE;
    }
}
