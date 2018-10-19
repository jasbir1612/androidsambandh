package test.gtconline.pojo.response;

import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {

    @SerializedName("Status")
    String status;

    @SerializedName("Message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
