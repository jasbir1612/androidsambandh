package test.testing.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import test.testing.pojo.request.RegisterBody;
import test.testing.pojo.request.SendSmsBody;
import test.testing.pojo.response.BlockDataResponse;
import test.testing.pojo.response.DistrictDataResponse;
import test.testing.pojo.response.RegisterResponse;
import test.testing.pojo.response.SchoolDataResponse;
import test.testing.pojo.response.SendOtpResponse;
import test.testing.pojo.response.SendSmsResponse;
import test.testing.pojo.response.VillageDataResponse;

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
}
