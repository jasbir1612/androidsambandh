package test.gtconline.pojo.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOtpResponse implements Parcelable {

    @SerializedName("AppUID")
    @Expose
    private String appUID;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("SMSID")
    @Expose
    private String sMSID;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("VallidTill")
    @Expose
    private String vallidTill;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    protected SendOtpResponse(Parcel in) {
        appUID = in.readString();
        mobile = in.readString();
        sMSID = in.readString();
        oTP = in.readString();
        vallidTill = in.readString();
        status = in.readString();
        message = in.readString();
    }

    public static final Creator<SendOtpResponse> CREATOR = new Creator<SendOtpResponse>() {
        @Override
        public SendOtpResponse createFromParcel(Parcel in) {
            return new SendOtpResponse(in);
        }

        @Override
        public SendOtpResponse[] newArray(int size) {
            return new SendOtpResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appUID);
        dest.writeString(mobile);
        dest.writeString(sMSID);
        dest.writeString(oTP);
        dest.writeString(vallidTill);
        dest.writeString(status);
        dest.writeString(message);
    }

    public String getAppUID() {
        return appUID;
    }

    public String getMobile() {
        return mobile;
    }

    public String getsMSID() {
        return sMSID;
    }

    public String getoTP() {
        return oTP;
    }

    public String getVallidTill() {
        return vallidTill;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
