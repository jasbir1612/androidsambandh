package test.testing.rest;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.testing.pojo.request.SchoolDataBody;
import test.testing.pojo.response.SchoolDataResponse;

/**
 * Created by rajdeep1008 on 17/12/17.
 */

public class ApiService {

    public static final String BASE_URL = "http://sambandhhealthapi.uniso.in/api/";

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new HTTPLoggingInterceptor())
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient);

    private static Retrofit mRetrofit = builder.build();
    private Sambandh sambandhApi;

    private Retrofit initRetrofit() {
        if (mRetrofit == null) {

            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HTTPLoggingInterceptor())
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .build();

            builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient);

        }
        return mRetrofit;
    }

    public ApiService() {
        if (mRetrofit == null) {
            initRetrofit();
        }
        sambandhApi = mRetrofit.create(Sambandh.class);
    }

    public void getSchoolData(SchoolDataBody body, final ResponseCallback<List<SchoolDataResponse>> callback) {
        Call<List<SchoolDataResponse>> call = sambandhApi.getSchoolData(body);
        call.enqueue(new Callback<List<SchoolDataResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SchoolDataResponse>> call, @NonNull Response<List<SchoolDataResponse>> response) {
                if (response.isSuccessful()) {
                    ArrayList<SchoolDataResponse> resp = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        resp.add(response.body().get(i));
                        Log.d("response", resp.toString());
                    }
                    callback.success(resp);
                } else {
                    callback.failure(new ArrayList<SchoolDataResponse>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SchoolDataResponse>> call, Throwable t) {
                Log.d("lalala", t.getMessage());
                callback.failure(new ArrayList<SchoolDataResponse>());
            }
        });
    }
}
