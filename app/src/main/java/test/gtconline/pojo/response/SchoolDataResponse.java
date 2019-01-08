package test.gtconline.pojo.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolDataResponse implements Parcelable {

    @SerializedName("Rowid")
    @Expose
    private Double rowid;
    @SerializedName("District Name")
    @Expose
    private String districtName;
    @SerializedName("Block Name")
    @Expose
    private String blockName;
    @SerializedName("Village Name")
    @Expose
    private String villageName;
    @SerializedName("School Name")
    @Expose
    private String schoolName;
    @SerializedName("School Code")
    @Expose
    private Double schoolCode;
    @SerializedName("School Category")
    @Expose
    private String schoolCategory;
    @SerializedName("School Management")
    @Expose
    private String schoolManagement;
    @SerializedName("DistSchoolCode")
    private String distSchoolCode;
    @SerializedName("Activity")
    @Expose
    private String activity;

    protected SchoolDataResponse(Parcel in) {
        if (in.readByte() == 0) {
            rowid = null;
        } else {
            rowid = in.readDouble();
        }
        districtName = in.readString();
        blockName = in.readString();
        villageName = in.readString();
        schoolName = in.readString();
        if (in.readByte() == 0) {
            schoolCode = null;
        } else {
            schoolCode = in.readDouble();
        }
        schoolCategory = in.readString();
        schoolManagement = in.readString();
        distSchoolCode = in.readString();
        activity = in.readString();
    }

    public static final Creator<SchoolDataResponse> CREATOR = new Creator<SchoolDataResponse>() {
        @Override
        public SchoolDataResponse createFromParcel(Parcel in) {
            return new SchoolDataResponse(in);
        }

        @Override
        public SchoolDataResponse[] newArray(int size) {
            return new SchoolDataResponse[size];
        }
    };

    public Double getRowid() {
        return rowid;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getBlockName() {
        return blockName;
    }

    public String getVillageName() {
        return villageName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public Double getSchoolCode() {
        return schoolCode;
    }

    public String getSchoolCategory() {
        return schoolCategory;
    }

    public String getSchoolManagement() {
        return schoolManagement;
    }

    public String getActivity() {
        return activity;
    }

    public String getDistSchoolCode() {
        return distSchoolCode;
    }

    public void setDistSchoolCode(String distSchoolCode) {
        this.distSchoolCode = distSchoolCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rowid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rowid);
        }
        dest.writeString(districtName);
        dest.writeString(blockName);
        dest.writeString(villageName);
        dest.writeString(schoolName);
        if (schoolCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(schoolCode);
        }
        dest.writeString(schoolCategory);
        dest.writeString(schoolManagement);
        dest.writeString(distSchoolCode);
        dest.writeString(activity);
    }
}
