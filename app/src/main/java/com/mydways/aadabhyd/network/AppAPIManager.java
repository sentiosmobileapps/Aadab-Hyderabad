package com.mydways.aadabhyd.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.mydways.aadabhyd.utilities.AppUtils;
import com.squareup.otto.Bus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Kishore Adda on 31/3/18.
 */
public class AppAPIManager<T> implements Callback<T> {
    private AppAPIServices apiServices = null;
    private Call<T> mRequestCall;
    private Bus mEventBus;
    private int mAPIId;

    public AppAPIManager() {
        getClient();
    }

    public AppAPIServices getApiServices() {
        return apiServices;
    }

    private AppAPIServices getClient() {
        if (apiServices == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("contentType",
                            "application/json").build();
                    return chain.proceed(request);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppUtils.BASE_URL + AppUtils.SERVICE_PROVIDER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();
            apiServices = retrofit.create(AppAPIServices.class);
        }
        return apiServices;
    }


    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull retrofit2.Response<T> response) {
        Log.e("Response Body:\n", new Gson().toJson(response));
        if (response.isSuccessful()) {
            BaseAPIResponse<T> baseApiResponse = (BaseAPIResponse<T>) response.body();

            if (Integer.parseInt(baseApiResponse.statusCode) == 200) {
                Log.e("Response Body:\n", new Gson().toJson(baseApiResponse));
                mEventBus.post(new OnAPIResponseSuccess(mAPIId, baseApiResponse));
            } else {
                mEventBus.post(new OnAPIResponseError(mAPIId, new AadabHydException(Integer.parseInt
                        (baseApiResponse.statusCode),
                        baseApiResponse.message)));
            }

        } else {
            Log.e("Execption : ", response.raw().toString());
            mEventBus.post(new OnAPIResponseError(mAPIId, new AadabHydException(response.code(),
                    response.message())));
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Log.e("Execption : ", t.getMessage());
        mEventBus.post(new OnAPIResponseError(mAPIId, new AadabHydException(t.getMessage())));
    }

    public void enqueue(Context context, Call<T> call, Bus bus, int apiId) {
        if (AppUtils.isNetworkAvailable(context)) {
            mAPIId = apiId;
            mEventBus = bus;
            mRequestCall = call;
            mRequestCall.enqueue(this);
        } else {
            bus.post(new OnAPIResponseError(apiId, new AadabHydException(AppUtils.AppErrorCode
                    .NO_INTERNET)));
        }
    }
}
