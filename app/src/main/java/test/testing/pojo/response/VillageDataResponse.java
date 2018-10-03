package test.testing.pojo.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VillageDataResponse implements Parcelable {

    @SerializedName("VillageCode")
    @Expose
    private long villageCode;

    @SerializedName("VillageName")
    @Expose
    private String villageName;

    protected VillageDataResponse(Parcel in) {
        villageCode = in.readLong();
        villageName = in.readString();
    }

    public long getVillageCode() {
        return villageCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public static final Creator<VillageDataResponse> CREATOR = new Creator<VillageDataResponse>() {
        @Override
        public VillageDataResponse createFromParcel(Parcel in) {
            return new VillageDataResponse(in);
        }

        @Override
        public VillageDataResponse[] newArray(int size) {
            return new VillageDataResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(villageCode);
        dest.writeString(villageName);
    }
}
