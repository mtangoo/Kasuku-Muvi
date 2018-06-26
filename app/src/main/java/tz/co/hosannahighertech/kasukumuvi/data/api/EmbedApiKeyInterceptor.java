package tz.co.hosannahighertech.kasukumuvi.data.api;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tz.co.hosannahighertech.kasukumuvi.utilities.Settings;

/**
 * @package tz.co.hosannahighertech.kasukumuvi.data.api
 * Created by Stefano D. Mtangoo <stefano@hosannahighertech.co.tz> on
 * Created at 26/06/2018 17:54.
 * Copyright (c) 2018, Hosanna Higher Technologies Co. Ltd
 * This Code is Provided under Hosanna HTCL Licensing Conditions.
 */

public class EmbedApiKeyInterceptor implements Interceptor {

    Settings mSettings;

    @Inject
    public EmbedApiKeyInterceptor(Settings settings) {
        mSettings = settings;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", mSettings.getApiKey())
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder().url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
