package test.gtconline.pojo.response;

import com.google.gson.annotations.SerializedName;

public class UdiseDataResponse {

    @SerializedName("DistrictName")
    String DistrictName;

    @SerializedName("DistrictCode")
    String districtCode;

    @SerializedName("BlockCode")
    String blockCode;

    @SerializedName("BlockName")
    String blockName;

    @SerializedName("SchoolName")
    String schoolName;

    @SerializedName("VillageCode")
    String villageCode;

    @SerializedName("VillageName")
    String villageName;

    @SerializedName("Error")
    String error;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }
}
