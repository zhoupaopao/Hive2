package com.hivee2.mvp.model.bean;

/**
 * Created by 狄飞 on 2016/10/28.
 */
public class JpushBean {
    /**
     * CarNumber : 沪A00001
     * PositionTime : 2016-10-28 12:07:03
     * PledgerName : zhaoyun
     * InternalNum : S1173
     * Lat : 31.34642332
     * Lng : 119.2213331
     * AlarmType : 按键复位报警
     */

    private String CarNumber;
    private String PositionTime;
    private String PledgerName;
    private String InternalNum;
    private String Lat;
    private String Lng;
    private String AlarmType;

    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String CarNumber) {
        this.CarNumber = CarNumber;
    }

    public String getPositionTime() {
        return PositionTime;
    }

    public void setPositionTime(String PositionTime) {
        this.PositionTime = PositionTime;
    }

    public String getPledgerName() {
        return PledgerName;
    }

    public void setPledgerName(String PledgerName) {
        this.PledgerName = PledgerName;
    }

    public String getInternalNum() {
        return InternalNum;
    }

    public void setInternalNum(String InternalNum) {
        this.InternalNum = InternalNum;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String Lat) {
        this.Lat = Lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String Lng) {
        this.Lng = Lng;
    }

    public String getAlarmType() {
        return AlarmType;
    }

    public void setAlarmType(String AlarmType) {
        this.AlarmType = AlarmType;
    }
}
