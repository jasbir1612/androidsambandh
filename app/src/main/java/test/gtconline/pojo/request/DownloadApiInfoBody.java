package test.gtconline.pojo.request;

import com.google.gson.annotations.SerializedName;

public class DownloadApiInfoBody {

    @SerializedName("MobileNo")
    String mobileNo;

    @SerializedName("SA")
    String SA;

    @SerializedName("Type")
    String type;

    public DownloadApiInfoBody(String mobileno,String type,String SA)
    {
        this.mobileNo=mobileno;
        this.type=type;
        this.SA=SA;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSA() {
        return SA;
    }

    public void setSA(String SA) {
        this.SA = SA;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
