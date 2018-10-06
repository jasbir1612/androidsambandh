package test.testing.pojo.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("Result")
    @Expose
    private Integer result;

    @SerializedName("AppID")
    @Expose
    private String appID;

    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("MailID")
    @Expose
    private String mailID;

    @SerializedName("MailID1")
    @Expose
    private String mailID1;

    @SerializedName("CCMailID")
    @Expose
    private String cCMailID;

    @SerializedName("BCCMailID")
    @Expose
    private String bCCMailID;

    @SerializedName("Subject")
    @Expose
    private String subject;

    @SerializedName("MailText")
    private String mailText;

    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("Message")
    String message;

    public String getMessage() {
        return message;
    }

    public Integer getResult() {
        return result;
    }

    public String getAppID() {
        return appID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getMailID() {
        return mailID;
    }

    public String getMailID1() {
        return mailID1;
    }

    public String getcCMailID() {
        return cCMailID;
    }

    public String getbCCMailID() {
        return bCCMailID;
    }

    public String getSubject() {
        return subject;
    }

    public String getMailText() {
        return mailText;
    }

    public String getDateTime() {
        return dateTime;
    }
}
