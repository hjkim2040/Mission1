package com.sc.mission1;

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
    private String xSwifiMgrNo;
    private String xSwifiWrdofc;
    private String xSwifiMainNm;
    private String xSwifiAdres1;
    private String xSwifiAdres2;
    private String xSwifiInstlFloor;
    private String xSwifiInstlTy;
    private String xSwifiInstlMby;
    private String xSwifiSvcSe;
    private String xSwifiCmcwr;
    private String xSwifiCnstcYear;
    private String xSwifiInoutDoor;
    private String xSwifiRemars3;
    private String lat;
    private String lnt;
    private String workDttm;

}
