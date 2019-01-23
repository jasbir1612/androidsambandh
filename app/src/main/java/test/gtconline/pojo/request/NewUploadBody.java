package test.gtconline.pojo.request;

import com.google.gson.annotations.SerializedName;

public class NewUploadBody {


    @SerializedName("AppID")
    String appId;

    @SerializedName("CreatedBy")
    String createdBy;

    @SerializedName("DownloadURL1")
    String downloadUrl1;

    @SerializedName("DownloadURL2")
    String downloadUrl2;
    @SerializedName("Content1")
    String content1;

    @SerializedName("Content2")
    String content2;


    public NewUploadBody(String createdBy, String downloadUrl1, String downloadUrl2,String content1, String content2, String appId) {
        this.createdBy = createdBy;
        this.downloadUrl1 = downloadUrl1;
        this.downloadUrl2 = downloadUrl2;
        this.content1=content1;
        this.content2=content2;
        this.appId=appId;

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
