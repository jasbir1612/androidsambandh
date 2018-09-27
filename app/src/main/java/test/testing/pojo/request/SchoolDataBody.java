package test.testing.pojo.request;

import com.google.gson.annotations.SerializedName;

public class SchoolDataBody {

    @SerializedName("UDiceCode")
    String uDiceCode;

    @SerializedName("DistrictCode")
    String districtCode;

    @SerializedName("BlockCode")
    String blockCode;

    @SerializedName("VillageCode")
    String villageCode;

    public SchoolDataBody() {
    }

    public SchoolDataBody(String uDiceCode, String districtCode, String blockCode, String villageCode) {
        this.uDiceCode = uDiceCode;
        this.districtCode = districtCode;
        this.blockCode = blockCode;
        this.villageCode = villageCode;
    }

    public String getuDiceCode() {
        return uDiceCode;
    }

    public void setuDiceCode(String uDiceCode) {
        this.uDiceCode = uDiceCode;
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

    public String getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(String villageCode) {
        this.villageCode = villageCode;
    }
}
