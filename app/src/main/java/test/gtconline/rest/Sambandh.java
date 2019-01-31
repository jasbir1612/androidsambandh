package test.gtconline.rest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

public interface Sambandh {

    @GET("Sambandh/GetDistrictData")
    Call<List<DistrictDataResponse>> getDistrictData(@Query("StateCode") long code);

    @GET("Sambandh/GetBlockData")
    Call<List<BlockDataResponse>> getBlockData(@Query("DistrictCode") long code);

    @GET("Sambandh/GetVillageData")
    Call<List<VillageDataResponse>> getVillageData(@Query("BlockCode") long code);
    @POST("Sambandh/SendOTP")
    Call<List<SendOtpResponse>> sendOtp(@Query("MobileNo") String phoneNumber);

    @POST("Sambandh/SendSMS")
    @FormUrlEncoded
    Call<SendSmsResponse> sendSms(@Body SendSmsBody body);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/Register")
    Call<List<RegisterResponse>> register(@Body RegisterBody body);

    @GET("Sambandh/GetSchoolData")
    Call<List<SchoolDataResponse>> getSchoolData(@Query("DistrictCode") String districtCode,
                                                 @Query("BlockCode") String blockCode,
                                                 @Query("VillageCode") String villageCode,
                                                 @Query("UDiceCode") String udiceCode);

    @POST("Sambandh/VerifyOTP")
    Call<List<VerifyOtpResponse>> verifyOtp(@Query("MobileNo") String mobileNo, @Query("OTP") String otp, @Query("AppUID") String appUid);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/Upload")
    Call<List<UploadResponse>> upload(@Body UploadBody body);

    @GET("Sambandh/GetSchoolAPiData")
    Call<List<UdiseDataResponse>> getSchoolNameUsingUdise(@Query("UDiceCode") String udiseCode, @Query("SA") String SA);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/UploadFile")
    Call<List<NewUploadResponse>> newUpload(@Body NewUploadBody body);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/ErrorCause")
    Call<List<ErrorMessage>> uploadErrorMessage(@Body ErrorMessage errorMessage);

    @GET("Sambandh/GetAppLatestVersion")
    Call<AppVersionResponse> getLatestVersion();

    @Headers({"Accept: application/json","Content-Type: application/json"})
    @POST("Sambandh/InsertPFLApiData")
    Call<List<RegisterResponse>> insertPflData(@Body RegisterBody body);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/InsertPFLApiPhoto")
    Call<List<NewUploadResponse>> insertPhotoApi(@Body NewUploadBody body);

    @GET("Sambandh/DownloadLinks")
    Call<DownloadLinksResponse> getDownloadLinks();

    @GET("Sambandh/GetDownloadAPiLinks")
    Call<DownloadLinksResponse> GetDownloadAPiLinks(@Query("SA") String SA);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/InsertDownloadAPiLink")
    Call<DownloadApiInfoResponse> insertDownloadApiLink(@Body DownloadApiInfoBody body);


    @GET("Sambandh/GetMobileRegister")
    Call<List<CheckRegisteredResponse>> checkRegistered(@Query("MobileNo") String mobileNo);
}
