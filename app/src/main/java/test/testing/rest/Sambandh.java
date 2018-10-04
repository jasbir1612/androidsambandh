package test.testing.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import test.testing.pojo.request.SchoolDataBody;
import test.testing.pojo.request.SendSmsBody;
import test.testing.pojo.response.BlockDataResponse;
import test.testing.pojo.response.DistrictDataResponse;
import test.testing.pojo.response.SchoolDataResponse;
import test.testing.pojo.response.SmsDataResponse;
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

    @POST("Sambandh/SendSMS")
    Call<List<SmsDataResponse>> sendSms(@Body SendSmsBody body);
//
//    @POST("Sambandh/SendSMS")
//    Call<> getBlockDataQueryMethod(@Query("MobileNo") String mobileNo, @Query("Message") String message);
//
//    @POST("Sambandh/GetDesignation")
//    Call<> getDesignation();

    @POST("Sambandh/GetSchoolData")
    Call<List<SchoolDataResponse>> getSchoolData(@Body SchoolDataBody body);
}
