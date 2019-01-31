package test.gtconline.rest;

import android.content.Context;
import android.content.Intent;
import android.renderscript.ScriptC;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.gtconline.R;
import test.gtconline.pojo.request.DownloadApiInfoBody;
import test.gtconline.pojo.request.ErrorMessage;
import test.gtconline.pojo.request.NewUploadBody;
import test.gtconline.pojo.request.RegisterBody;
import test.gtconline.pojo.request.SendSmsBody;
import test.gtconline.pojo.request.UploadBody;
import test.gtconline.pojo.response.AppVersionResponse;
import test.gtconline.pojo.response.BlockDataResponse;
import test.gtconline.pojo.response.CheckRegisteredResponse;
import test.gtconline.pojo.response.DistrictDataResponse;
import test.gtconline.pojo.response.DownloadApiInfoResponse;
import test.gtconline.pojo.response.DownloadLinksResponse;
import test.gtconline.pojo.response.NewUploadResponse;
import test.gtconline.pojo.response.RegisterResponse;
import test.gtconline.pojo.response.SchoolDataResponse;
import test.gtconline.pojo.response.SendOtpResponse;
import test.gtconline.pojo.response.SendSmsResponse;
import test.gtconline.pojo.response.UdiseDataResponse;
import test.gtconline.pojo.response.UploadResponse;
import test.gtconline.pojo.response.VerifyOtpResponse;
import test.gtconline.pojo.response.VillageDataResponse;
import test.gtconline.ui.RegisterActivity;

/**
 * Created by rajdeep1008 on 17/12/17.
 */

public class ApiService {

    public static final String BASE_URL = "http://sambandhhealthapiv2.sambandhhealth.org/api/";

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

    public void getSchoolData(String districtCode, String blockCode, String villageCode, String udiceCode, final ResponseCallback<List<SchoolDataResponse>> callback) {
        Call<List<SchoolDataResponse>> call = sambandhApi.getSchoolData(districtCode, blockCode, villageCode, udiceCode);
        call.enqueue(new Callback<List<SchoolDataResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<SchoolDataResponse>> call, @NonNull Response<List<SchoolDataResponse>> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    Log.d("err1", String.valueOf(response.body()));
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

    public void getDistrictData(long code, final ResponseCallback<List<DistrictDataResponse>> callback) {
        Call<List<DistrictDataResponse>> call = sambandhApi.getDistrictData(code);
        call.enqueue(new Callback<List<DistrictDataResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictDataResponse>> call, Response<List<DistrictDataResponse>> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(new ArrayList<DistrictDataResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<DistrictDataResponse>> call, Throwable t) {
                Log.d("err1", String.valueOf(t.getCause()));
                callback.failure(new ArrayList<DistrictDataResponse>());
            }
        });
    }

    public void getBlockData(long code, final ResponseCallback<List<BlockDataResponse>> callback) {
        Call<List<BlockDataResponse>> call = sambandhApi.getBlockData(code);
        call.enqueue(new Callback<List<BlockDataResponse>>() {
            @Override
            public void onResponse(Call<List<BlockDataResponse>> call, Response<List<BlockDataResponse>> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(new ArrayList<BlockDataResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<BlockDataResponse>> call, Throwable t) {
                callback.failure(new ArrayList<BlockDataResponse>());
            }
        });
    }

    public void getVillageData(long code, final ResponseCallback<List<VillageDataResponse>> callback) {
        Call<List<VillageDataResponse>> call = sambandhApi.getVillageData(code);
        call.enqueue(new Callback<List<VillageDataResponse>>() {
            @Override
            public void onResponse(Call<List<VillageDataResponse>> call, Response<List<VillageDataResponse>> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(new ArrayList<VillageDataResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<VillageDataResponse>> call, Throwable t) {
                callback.failure(new ArrayList<VillageDataResponse>());
            }
        });
    }

    public void register(RegisterBody jsonBody, final ResponseCallback<List<RegisterResponse>> callback) {
        Call<List<RegisterResponse>> call = sambandhApi.register(jsonBody);
        call.enqueue(new Callback<List<RegisterResponse>>() {
            @Override
            public void onResponse(Call<List<RegisterResponse>> call, Response<List<RegisterResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().get(0) != null) {
                            if (response.body().get(0).getMessage() != null && !response.body().get(0).getMessage().equalsIgnoreCase("")) {
                                callback.failure(new ArrayList<RegisterResponse>());
                            } else {
                                if (response.body().get(0).getResult() == 1) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(new ArrayList<RegisterResponse>());
                                }
                            }
                        } else {
                            callback.failure(new ArrayList<RegisterResponse>());
                        }
                    } else {
                        callback.failure(new ArrayList<RegisterResponse>());
                    }
                } else {
                    callback.failure(new ArrayList<RegisterResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<RegisterResponse>> call, Throwable t) {
                callback.failure(new ArrayList<RegisterResponse>());

            }
        });
    }

    public void getSmsData(SendSmsBody body, final ResponseCallback<SendSmsResponse> callback) {
        Call<SendSmsResponse> call = sambandhApi.sendSms(body);
        call.enqueue(new Callback<SendSmsResponse>() {
            @Override
            public void onResponse(Call<SendSmsResponse> call, Response<SendSmsResponse> response) {
                if (response.isSuccessful()) {
                    callback.success(response.body());
                } else {
                    callback.failure(new SendSmsResponse());
                }
            }

            @Override
            public void onFailure(Call<SendSmsResponse> call, Throwable t) {
                callback.failure(new SendSmsResponse());
            }
        });
    }

    public void sendOtp(String mobileNumber, final ResponseCallback<List<SendOtpResponse>> callback) {
        Call<List<SendOtpResponse>> call = sambandhApi.sendOtp(mobileNumber);
        call.enqueue(new Callback<List<SendOtpResponse>>() {
            @Override
            public void onResponse(Call<List<SendOtpResponse>> call, Response<List<SendOtpResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0).getStatus().equalsIgnoreCase("Success")) {
                        callback.success(response.body());
                    } else {
                        callback.failure(new ArrayList<SendOtpResponse>());
                    }
                } else {
                    callback.failure(new ArrayList<SendOtpResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<SendOtpResponse>> call, Throwable t) {
                callback.failure(new ArrayList<SendOtpResponse>());
            }
        });
    }

    public void uploadErrorMessage(ErrorMessage errorMessage, final ResponseCallback<List<ErrorMessage>> callback)
    {
        Call<List<ErrorMessage>> call=sambandhApi.uploadErrorMessage(errorMessage);
        call.enqueue(new Callback<List<ErrorMessage>>() {
            @Override
            public void onResponse(Call<List<ErrorMessage>> call, Response<List<ErrorMessage>> response) {
                if(response.isSuccessful())
                {
                    callback.success(response.body());
                }
                else
                {
                    callback.failure(new ArrayList<ErrorMessage>());
                }
            }

            @Override
            public void onFailure(Call<List<ErrorMessage>> call, Throwable t) {

                callback.failure(new ArrayList<ErrorMessage>());
            }
        });

    }

    public void getLatestAppVersion(final ResponseCallback<AppVersionResponse> callback)
    {
        Call<AppVersionResponse> call=sambandhApi.getLatestVersion();
        call.enqueue(new Callback<AppVersionResponse>() {
            @Override
            public void onResponse(Call<AppVersionResponse> call, Response<AppVersionResponse> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<AppVersionResponse> call, Throwable t) {

            }
        });
    }

    public void insertPflApi(NewUploadBody newUploadBody, final ResponseCallback<List<NewUploadResponse>> callback){
        Call<List<NewUploadResponse>> call=sambandhApi.insertPhotoApi(newUploadBody);
        call.enqueue(new Callback<List<NewUploadResponse>>() {
            @Override
            public void onResponse(Call<List<NewUploadResponse>> call, Response<List<NewUploadResponse>> response) {
                if(response.isSuccessful())
                {
                    if (response.body() != null) {
                        if (response.body().get(0) != null) {
                            if (!response.body().get(0).getMessage().equalsIgnoreCase("")) {
                                callback.failure(new ArrayList<NewUploadResponse>());
                            } else {
                                if (response.body().get(0).getResult() == 1) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(new ArrayList<NewUploadResponse>());
                                }
                            }
                        } else {
                            callback.failure(new ArrayList<NewUploadResponse>());
                        }
                    } else {
                        callback.failure(new ArrayList<NewUploadResponse>());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NewUploadResponse>> call, Throwable t) {

                callback.failure(new ArrayList<NewUploadResponse>());
            }
        });
    }
    public void insertPflData(RegisterBody  registerBody,final ResponseCallback <List<RegisterResponse>> callback)
    {
        Call<List<RegisterResponse>> call=sambandhApi.insertPflData(registerBody);
        call.enqueue(new Callback<List<RegisterResponse>>() {
            @Override
            public void onResponse(Call<List<RegisterResponse>> call, Response<List<RegisterResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().get(0) != null) {
                            if (response.body().get(0).getMessage() != null && !response.body().get(0).getMessage().equalsIgnoreCase("")) {
                            //    callback.failure(new ArrayList<RegisterResponse>());
                            } else {
                                if (response.body().get(0).getResult() == 1) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(new ArrayList<RegisterResponse>());
                                }
                            }
                        } else {
                            callback.failure(new ArrayList<RegisterResponse>());
                        }
                    } else {
                        callback.failure(new ArrayList<RegisterResponse>());
                    }
                } else {
                    callback.failure(new ArrayList<RegisterResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<RegisterResponse>> call, Throwable t) {

            }
        });
    }

    public void GetDownloadAPiLinks(String SA,final ResponseCallback<DownloadLinksResponse> callback)
    {
        Call<DownloadLinksResponse> call=sambandhApi.GetDownloadAPiLinks(SA);
        call.enqueue(new Callback<DownloadLinksResponse>() {
            @Override
            public void onResponse(Call<DownloadLinksResponse> call, Response<DownloadLinksResponse> response) {
                if (response.isSuccessful())
                {
                    callback.success(response.body());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<DownloadLinksResponse> call, Throwable t) {

            }


        });
    }


    public void insertDownloadLinkApi(DownloadApiInfoBody body, final ResponseCallback<DownloadApiInfoResponse> callback)
    {
        Call<DownloadApiInfoResponse> call=sambandhApi.insertDownloadApiLink(body);
        call.enqueue(new Callback<DownloadApiInfoResponse>() {
            @Override
            public void onResponse(Call<DownloadApiInfoResponse> call, Response<DownloadApiInfoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                                callback.success(response.body());
                            } else{

                            }
                        }

            }

            @Override
            public void onFailure(Call<DownloadApiInfoResponse> call, Throwable t) {

            }
        });




        }




    public void getDownloadLinks(final ResponseCallback<DownloadLinksResponse> callback)
    {
        Call<DownloadLinksResponse> call=sambandhApi.getDownloadLinks();
        call.enqueue(new Callback<DownloadLinksResponse>() {
            @Override
            public void onResponse(Call<DownloadLinksResponse> call, Response<DownloadLinksResponse> response) {
                if (response.isSuccessful())
                {
                    callback.success(response.body());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<DownloadLinksResponse> call, Throwable t) {

            }


        });
    }

    public void getSchoolNameUsingUdise(String SA, String udiseCode, final ResponseCallback<List<UdiseDataResponse>> callback)
    {
        Call<List<UdiseDataResponse>> call=sambandhApi.getSchoolNameUsingUdise(udiseCode,SA);
        call.enqueue(new Callback<List<UdiseDataResponse>>() {


            @Override
            public void onResponse(Call<List<UdiseDataResponse>> call, Response<List<UdiseDataResponse>> response) {

             if(response.isSuccessful()) {
                 if(response.body().get(0).getError()!=null) {
                     if (Integer.parseInt(response.body().get(0).getError()) == 402) {
                         callback.failure(response.body());
                     }
                 }
                 callback.success(response.body());

             }
            else {
                 callback.failure(new ArrayList<UdiseDataResponse>());
             }
            }

            @Override
            public void onFailure(Call<List<UdiseDataResponse>> call, Throwable t) {

                callback.failure(new ArrayList<UdiseDataResponse>());
            }
        });
        }



    public void verifyOtp(String mobileNo, String otp, String appUid, final ResponseCallback<List<VerifyOtpResponse>> callback) {
        Call<List<VerifyOtpResponse>> call = sambandhApi.verifyOtp(mobileNo, otp, appUid);
        call.enqueue(new Callback<List<VerifyOtpResponse>>() {
            @Override
            public void onResponse(Call<List<VerifyOtpResponse>> call, Response<List<VerifyOtpResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() == 0) {
                        callback.failure(new ArrayList<VerifyOtpResponse>());
                        return;
                    }
                    if (response.body().get(0) != null) {
                        if (response.body().get(0).getStatus() != null && response.body().get(0).getStatus().equalsIgnoreCase("Success")) {
                            callback.success(response.body());
                        } else {
                            callback.failure(new ArrayList<VerifyOtpResponse>());
                        }
                    } else {
                        callback.failure(new ArrayList<VerifyOtpResponse>());
                    }
                } else {
                    callback.failure(new ArrayList<VerifyOtpResponse>());
                }
            }

            @Override
            public void onFailure(Call<List<VerifyOtpResponse>> call, Throwable t) {
                callback.failure(new ArrayList<VerifyOtpResponse>());
            }
        });
    }

    public void upload(UploadBody body, final ResponseCallback<List<UploadResponse>> callback) {
        Call<List<UploadResponse>> call = sambandhApi.upload(body);
        call.enqueue(new Callback<List<UploadResponse>>() {
            @Override
            public void onResponse(Call<List<UploadResponse>> call, Response<List<UploadResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().get(0) != null) {
                            if (!response.body().get(0).getMessage().equalsIgnoreCase("")) {
                                callback.failure(new ArrayList<UploadResponse>());
                            } else {
                                if (response.body().get(0).getResult() == 1) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(new ArrayList<UploadResponse>());
                                }
                            }
                        } else {
                            callback.failure(new ArrayList<UploadResponse>());
                        }
                    } else {
                        callback.failure(new ArrayList<UploadResponse>());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UploadResponse>> call, Throwable t) {
                callback.failure(new ArrayList<UploadResponse>());
            }
        });
    }

    public void newUpload(NewUploadBody body, final ResponseCallback<List<NewUploadResponse>> callback) {
        Call<List<NewUploadResponse>> call = sambandhApi.newUpload(body);
        call.enqueue(new Callback<List<NewUploadResponse>>() {
            @Override
            public void onResponse(Call<List<NewUploadResponse>> call, Response<List<NewUploadResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().get(0) != null) {
                            if (!response.body().get(0).getMessage().equalsIgnoreCase("")) {
                                callback.failure(new ArrayList<NewUploadResponse>());
                            } else {
                                if (response.body().get(0).getResult() == 1) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(new ArrayList<NewUploadResponse>());
                                }
                            }
                        } else {
                            callback.failure(new ArrayList<NewUploadResponse>());
                        }
                    } else {
                        callback.failure(new ArrayList<NewUploadResponse>());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NewUploadResponse>> call, Throwable t) {
                callback.failure(new ArrayList<NewUploadResponse>());
            }
        });
    }

    public void checkRegistered(String mobileNo, final ResponseCallback<List<CheckRegisteredResponse>> callback) {
        Call<List<CheckRegisteredResponse>> call = sambandhApi.checkRegistered(mobileNo);
        call.enqueue(new Callback<List<CheckRegisteredResponse>>() {
            @Override
            public void onResponse(Call<List<CheckRegisteredResponse>> call, Response<List<CheckRegisteredResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().get(0) != null) {
                        if (response.body().get(0).getMessage().equalsIgnoreCase("Mobile No Register.")) {
                            callback.failure(new ArrayList<CheckRegisteredResponse>());
                        } else {
                            callback.success(new ArrayList<CheckRegisteredResponse>());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CheckRegisteredResponse>> call, Throwable t) {
                callback.failure(new ArrayList<CheckRegisteredResponse>());
            }
        });
    }
}
