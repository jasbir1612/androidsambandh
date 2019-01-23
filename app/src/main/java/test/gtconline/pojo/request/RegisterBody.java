package test.gtconline.pojo.request;

import com.google.gson.annotations.SerializedName;

public class RegisterBody {

    @SerializedName("MobileNo")
    String mobileNo;

    @SerializedName("SchoolType")
    String schoolType;

    @SerializedName("UserType")
    String userType;

    @SerializedName("UserName")
    String userName;

    @SerializedName("Designation")
    String designation;

    @SerializedName("EmailID")
    String emailID;

    @SerializedName("JSONData")
    String jsonData;

    @SerializedName("CreatedBy")
    String createdBy;

    @SerializedName("UdiceCode")
    String udiceCode;

    @SerializedName("DUdiceCode")
    String dudiceCode;

    @SerializedName("DTActivity")
    String dateTime;

    @SerializedName("NOStudentPledge")
    String pledge;



    public RegisterBody(String mobileNo, String schoolType, String userName, String designation, String emailID, String jsonData, String udiceCode, String dudiceCode,String dateTime,String pledge) {
        this.mobileNo = mobileNo;
        this.schoolType = schoolType;
        this.dateTime = dateTime;
        this.userName = userName;
        this.designation = designation;
        this.emailID = emailID;
        this.jsonData = jsonData;
        this.pledge = pledge;
        this.udiceCode = udiceCode;
        this.dudiceCode = dudiceCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUdiceCode() {
        return udiceCode;
    }

    public void setUdiceCode(String udiceCode) {
        this.udiceCode = udiceCode;
    }

    public String getDudiceCode() {
        return dudiceCode;
    }

    public void setDudiceCode(String dudiceCode) {
        this.dudiceCode = dudiceCode;
    }
}
