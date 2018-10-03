package test.testing.pojo.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockDataResponse implements Parcelable {

    @SerializedName("BlockCode")
    @Expose
    private long blockCode;

    @SerializedName("BlockName")
    @Expose
    private String blockName;

    protected BlockDataResponse(Parcel in) {
        blockCode = in.readLong();
        blockName = in.readString();
    }

    public long getBlockCode() {
        return blockCode;
    }

    public String getBlockName() {
        return blockName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(blockCode);
        dest.writeString(blockName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BlockDataResponse> CREATOR = new Creator<BlockDataResponse>() {
        @Override
        public BlockDataResponse createFromParcel(Parcel in) {
            return new BlockDataResponse(in);
        }

        @Override
        public BlockDataResponse[] newArray(int size) {
            return new BlockDataResponse[size];
        }
    };
}
