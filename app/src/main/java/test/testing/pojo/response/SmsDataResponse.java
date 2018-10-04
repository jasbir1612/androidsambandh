package test.testing.pojo.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by VictoriousAura on 04/10/18.
 */

public class SmsDataResponse implements Parcelable {

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("MobileNo")
    @Expose
    private int mobileNo;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Status")
    @Expose
    private int status;

    protected  SmsDataResponse(Parcel in) {

        name = in.readString();
        message=in.readString();

        if (in.readByte() == 0) {
            mobileNo = 0;
        } else {
            mobileNo = in.readInt();
        }

        if (in.readByte() == 0) {
            status = 0;
        } else {
            status = in.readInt();
        }

    }

    public static final Creator<SmsDataResponse> CREATOR = new Creator<SmsDataResponse>() {
        @Override
        public SmsDataResponse createFromParcel(Parcel in) {
            return new SmsDataResponse(in);
        }

        @Override
        public SmsDataResponse[] newArray(int size) {
            return new SmsDataResponse[size];
        }
    };

    public String getUserName() {
        return name;
    }

    public Integer getMobileNo() {
        return mobileNo;
    }

    public String getOTpMessage() { return message; }

    public Integer getStatus() { return  status;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        if (mobileNo == 0) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mobileNo);
        }
        dest.writeString(name);
        dest.writeString(message);

        if (status == 0) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(status);
        }

        Log.d("dest", dest.toString());
    }

}
