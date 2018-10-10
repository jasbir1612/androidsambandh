package test.testing.pojo.response;

import com.google.gson.annotations.SerializedName;

public class CheckRegisteredResponse {

    @SerializedName("Message")
    String message;

    public String getMessage() {
        return message;
    }
}
