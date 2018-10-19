package test.gtconline.pojo.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictDataResponse implements Parcelable {

    @SerializedName("DistrictCode")
    @Expose
    private long districtCode;

    @SerializedName("DistrictName")
    @Expose
    private String districtName;

    protected DistrictDataResponse(Parcel in) {
        districtCode = in.readLong();
        districtName = in.readString();
    }

    public long getDistrictCode() {
        return districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(districtCode);
        dest.writeString(districtName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DistrictDataResponse> CREATOR = new Creator<DistrictDataResponse>() {
        @Override
        public DistrictDataResponse createFromParcel(Parcel in) {
            return new DistrictDataResponse(in);
        }

        @Override
        public DistrictDataResponse[] newArray(int size) {
            return new DistrictDataResponse[size];
        }
    };
}
