package test.testing.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import test.testing.pojo.request.SchoolDataBody;
import test.testing.pojo.response.SchoolDataResponse;

/**
 * Created by rajdeep1008 on 17/12/17.
 */

public interface Sambandh {

//    @POST("Sambandh/GetDistrictData")
//    Call<> getDistrictData();
//
//    @POST("Sambandh/GetBlockData")
//    Call<> getBlockData();
//
//    @POST("Sambandh/GetVillageData")
//    Call<> getVillageData();
//
//    @POST("Sambandh/SendSMS")
//    Call<> sendSms(@Body SendSmsBody body);
//
//    @POST("Sambandh/SendSMS")
//    Call<> getBlockDataQueryMethod(@Query("MobileNo") String mobileNo, @Query("Message") String message);
//
//    @POST("Sambandh/GetDesignation")
//    Call<> getDesignation();

    @POST("Sambandh/GetSchoolData")
    Call<List<SchoolDataResponse>> getSchoolData(@Body SchoolDataBody body);
}
