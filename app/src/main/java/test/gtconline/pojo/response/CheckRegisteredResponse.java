package test.gtconline.pojo.response;

import com.google.gson.annotations.SerializedName;

public class CheckRegisteredResponse {

    @SerializedName("Message")
    String message;

    public String getMessage() {
        return message;
    }
}
