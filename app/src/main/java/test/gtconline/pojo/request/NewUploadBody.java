package test.gtconline.pojo.request;

import com.google.gson.annotations.SerializedName;

public class NewUploadBody {

    @SerializedName("DTActivity")
    String dtActivity;

    @SerializedName("NOStudentPledge")
    String noStudentPledge;

    @SerializedName("MobileNo")
    String mobileNo;

    @SerializedName("CreatedBy")
    String createdBy;

    @SerializedName("DownloadURL1")
    String downloadUrl1;

    @SerializedName("DownloadURL2")
    String downloadUrl2;

    public NewUploadBody(String dtActivity, String noStudentPledge, String mobileNo, String createdBy, String downloadUrl1, String downloadUrl2) {
        this.dtActivity = dtActivity;
        this.noStudentPledge = noStudentPledge;
        this.mobileNo = mobileNo;
        this.createdBy = createdBy;
        this.downloadUrl1 = downloadUrl1;
        this.downloadUrl2 = downloadUrl2;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDownloadUrl1() {
        return downloadUrl1;
    }

    public void setDownloadUrl1(String downloadUrl1) {
        this.downloadUrl1 = downloadUrl1;
    }

    public String getDownloadUrl2() {
        return downloadUrl2;
    }

    public void setDownloadUrl2(String downloadUrl2) {
        this.downloadUrl2 = downloadUrl2;
    }
}
