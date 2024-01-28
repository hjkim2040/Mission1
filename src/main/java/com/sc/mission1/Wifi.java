package com.sc.mission1;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Wifi {
    private double distance;
    @SerializedName("X_SWIFI_MGR_NO")
    private String xSwifiMgrNo;
    @SerializedName("X_SWIFI_WRDOFC")
    private String xSwifiWrdofc;
    @SerializedName("X_SWIFI_MAIN_NM")
    private String xSwifiMainNm;
    @SerializedName("X_SWIFI_ADRES1")
    private String xSwifiAdres1;
    @SerializedName("X_SWIFI_ADRES2")
    private String xSwifiAdres2;
    @SerializedName("X_SWIFI_INSTL_FLOOR")
    private String xSwifiInstlFloor;
    @SerializedName("X_SWIFI_INSTL_TY")
    private String xSwifiInstlTy;
    @SerializedName("X_SWIFI_INSTL_MBY")
    private String xSwifiInstlMby;
    @SerializedName("X_SWIFI_SVC_SE")
    private String xSwifiSvcSe;
    @SerializedName("X_SWIFI_CMCWR")
    private String xSwifiCmcwr;
    @SerializedName("X_SWIFI_CNSTC_YEAR")
    private String xSwifiCnstcYear;
    @SerializedName("X_SWIFI_INOUT_DOOR\t")
    private String xSwifiInoutDoor;
    @SerializedName("X_SWIFI_REMARS3")
    private String xSwifiRemars3;
    @SerializedName("LAT")
    private String lat;
    @SerializedName("LNT")
    private String lnt;
    @SerializedName("WORK_DTTM")
    private String workDttm;

}
