package test.testing.pojo.request;

import com.google.gson.annotations.SerializedName;

public class UploadBody {
    @SerializedName("DTActivity")
    String dtActivity;

    @SerializedName("NOStudentPledge")
    String noStudentPledge;

    @SerializedName("MobileNo")
    String mobileNo;

    @SerializedName("IsActive")
    String isActive;

    @SerializedName("CreatedBy")
    String createdBy;

    public UploadBody(String dtActivity, String noStudentPledge, String mobileNo, String isActive, String createdBy) {
        this.dtActivity = dtActivity;
        this.noStudentPledge = noStudentPledge;
        this.mobileNo = mobileNo;
        this.isActive = isActive;
        this.createdBy = createdBy;
    }

    public String getDtActivity() {
        return dtActivity;
    }

    public void setDtActivity(String dtActivity) {
        this.dtActivity = dtActivity;
    }

    public String getNoStudentPledge() {
        return noStudentPledge;
    }

    public void setNoStudentPledge(String noStudentPledge) {
        this.noStudentPledge = noStudentPledge;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
