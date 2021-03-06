package test.gtconline.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import test.gtconline.pojo.request.NewUploadBody;
import test.gtconline.pojo.request.RegisterBody;
import test.gtconline.pojo.request.SendSmsBody;
import test.gtconline.pojo.request.UploadBody;
import test.gtconline.pojo.response.BlockDataResponse;
import test.gtconline.pojo.response.CheckRegisteredResponse;
import test.gtconline.pojo.response.DistrictDataResponse;
import test.gtconline.pojo.response.NewUploadResponse;
import test.gtconline.pojo.response.RegisterResponse;
import test.gtconline.pojo.response.SchoolDataResponse;
import test.gtconline.pojo.response.SendOtpResponse;
import test.gtconline.pojo.response.SendSmsResponse;
import test.gtconline.pojo.response.UploadResponse;
import test.gtconline.pojo.response.VerifyOtpResponse;
import test.gtconline.pojo.response.VillageDataResponse;

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
    Call<List<SchoolDataResponse>> getSchoolData(@Query("DistrictCode") long districtCode,
                                                 @Query("BlockCode") long blockCode,
                                                 @Query("VillageCode") long villageCode,
                                                 @Query("UDiceCode") String udiceCode);

    @POST("Sambandh/VerifyOTP")
    Call<List<VerifyOtpResponse>> verifyOtp(@Query("MobileNo") String mobileNo, @Query("OTP") String otp, @Query("AppUID") String appUid);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/Upload")
    Call<List<UploadResponse>> upload(@Body UploadBody body);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Sambandh/UploadFile")
    Call<List<NewUploadResponse>> newUpload(@Body NewUploadBody body);

    @GET("Sambandh/GetMobileRegister")
    Call<List<CheckRegisteredResponse>> checkRegistered(@Query("MobileNo") String mobileNo);
}
