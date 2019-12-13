package com.android.feedreader.feeds.api;

import android.util.Log;

import com.android.feedreader.feeds.app.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.feedreader.feeds.app.AppConstants.CLOSE;
import static com.android.feedreader.feeds.app.AppConstants.CONNECTION;

public class FlowSDKApiClient {
    private ApiService apiService;
    private static final int RETROFIT_CONNECTION_TIMEOUT_SECONDS = 30;
    public static final int API_RESPONSE_STATUS_CODE = 200;

    private FlowSDKApiClient(Builder builder) {
        this.apiService = builder.apiService;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public static class Builder {
        private String baseUrl;
        private ApiService apiService;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public FlowSDKApiClient create() {
            OkHttpClient okHttpClient = getOkhttp3Client();
            apiService = getRetrofit(baseUrl, okHttpClient).create(ApiService.class);
            return new FlowSDKApiClient(this);
        }


        private Retrofit getRetrofit(String baseUrl, OkHttpClient httpAuthClient) {
            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpAuthClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        private HttpLoggingInterceptor getLoggingInterceptor() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Log.i(AppConstants.APP_TAG, message));
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return interceptor;
        }

        @SuppressWarnings("SpellCheckingInspection")
        private OkHttpClient getOkhttp3Client() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .retryOnConnectionFailure(true)
                    .cache(null)
                    .addInterceptor(getLoggingInterceptor())
                    .connectTimeout(RETROFIT_CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(RETROFIT_CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .addNetworkInterceptor(chain -> {
                        Request request = chain.request().newBuilder().addHeader(CONNECTION, CLOSE).build();
                        return chain.proceed(request);
                    })
                    .readTimeout(RETROFIT_CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return builder.build();
        }
    }
}
