package test.gtconline.pojo.request;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    String errorMessage;
    String deviceBrand;
    String deviceName;
    String deviceModel;
    String deviceId;
    String product;

    String sdkVersion;
    String releseVersion;
    String incrementalvVersion;


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
