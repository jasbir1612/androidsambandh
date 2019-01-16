package test.gtconline.pojo.response;

import com.google.gson.annotations.SerializedName;

public class AppVersionResponse {

    @SerializedName("LatestVersionCode")
    String latestVersionCode;

    @SerializedName("StableVersionCode")
    String stableVersionCode;


    public String getLatestVersionCode() {
        return latestVersionCode;
    }

    public void setLatestVersionCode(String latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public String getStableVersionCode() {
        return stableVersionCode;
    }

    public void setStableVersionCode(String stableVersionCode) {
        this.stableVersionCode = stableVersionCode;
    }


}
