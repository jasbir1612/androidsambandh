package test.gtconline.pojo.response;

import com.google.gson.annotations.SerializedName;

public class DownloadApiInfoResponse {

    @SerializedName("Result")
    String result;

    @SerializedName("MobileNo")
    String mobileNo;

    @SerializedName("Type")
    String type;

}
