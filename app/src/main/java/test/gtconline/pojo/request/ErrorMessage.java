package test.gtconline.pojo.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    @SerializedName("Message")
    String errorMessage;

    @SerializedName("BrandName")
    String deviceBrand;

    @SerializedName("DeviceName")
    String deviceName;

    @SerializedName("ModalNumber")
    String deviceModel;

    @SerializedName("DeviceID")
    String deviceId;

    @SerializedName("Product")
    String product;

    @SerializedName("SDK")
    String sdkVersion;

    @SerializedName("Release")
    String releseVersion;

    @SerializedName("Incremental")
    String incrementalvVersion;

    @SerializedName("AppVersion")
    String appVersion;

    @SerializedName("DateTime")
    String dateTime;

    @SerializedName("ErrorTrace")
    String errorTrace;

    @SerializedName("MobileNo")
    String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getErrorTrace() {
        return errorTrace;
    }

    public void setErrorTrace(String errorTrace) {
        this.errorTrace = errorTrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String device) {
        this.deviceName = device;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getReleseVersion() {
        return releseVersion;
    }

    public void setReleseVersion(String releseVersion) {
        this.releseVersion = releseVersion;
    }

    public String getIncrementalvVersion() {
        return incrementalvVersion;
    }

    public void setIncrementalvVersion(String incrementalvVersion) {
        this.incrementalvVersion = incrementalvVersion;
    }
}
