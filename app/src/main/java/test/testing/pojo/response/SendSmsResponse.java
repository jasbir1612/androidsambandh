package test.testing.pojo.response;

import com.google.gson.annotations.SerializedName;

public class SendSmsResponse {

    @SerializedName("Name")
    String name;

    @SerializedName("MobileNo")
    String mobileNo;

    @SerializedName("Message")
    String message;

    @SerializedName("Status")
    String status;

    public SendSmsResponse() {
    }

    public SendSmsResponse(String name, String mobileNo, String message, String status) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.message = message;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
