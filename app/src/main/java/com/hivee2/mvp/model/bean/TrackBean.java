package com.hivee2.mvp.model.bean;

/**
 * Created by 狄飞 on 2016/8/2.
 */
public class TrackBean {

    /**
     * ID : null
     * DeviceID : 370fe0e11f854b0d8b0f1f77c63e053d
     * Lng : null
     * Lat : null
     * BLng : null
     * BLat : null
     * Address : null
     * Speed : 0
     * BL : 0
     * BS : 0
     * BSName : null
     * Type : null
     * TypeName : null
     * PositionTime : null
     * PledgerName : null
     * CarNumber : null
     * Model : null
     * IsSocket : 0
     * IsTrack : false
     * StatusInfo : null
     * IsOnline : false
     * obdField : null
     * Result : 1
     * ErrorMsg : 系统中找不到该IMEI
     */

    private Object ID;
    private String DeviceID;
    private Object Lng;
    private Object Lat;
    private Object BLng;
    private Object BLat;
    private Object Address;
    private int Speed;
    private int BL;
    private int BS;
    private Object BSName;
    private Object Type;
    private Object TypeName;
    private Object PositionTime;
    private Object PledgerName;
    private Object CarNumber;
    private Object Model;
    private int IsSocket;
    private boolean IsTrack;
    private Object StatusInfo;
    private boolean IsOnline;
    private Object obdField;
    private int Result;
    private String ErrorMsg;
    private int TrackMode;
    private boolean IsFastTrack;

    public Object getID() {
        return ID;
    }

    public void setID(Object ID) {
        this.ID = ID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    public Object getLng() {
        return Lng;
    }

    public void setLng(Object Lng) {
        this.Lng = Lng;
    }

    public Object getLat() {
        return Lat;
    }

    public void setLat(Object Lat) {
        this.Lat = Lat;
    }

    public Object getBLng() {
        return BLng;
    }

    public void setBLng(Object BLng) {
        this.BLng = BLng;
    }

    public Object getBLat() {
        return BLat;
    }

    public void setBLat(Object BLat) {
        this.BLat = BLat;
    }

    public Object getAddress() {
        return Address;
    }

    public void setAddress(Object Address) {
        this.Address = Address;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int Speed) {
        this.Speed = Speed;
    }

    public int getBL() {
        return BL;
    }

    public void setBL(int BL) {
        this.BL = BL;
    }

    public int getBS() {
        return BS;
    }

    public void setBS(int BS) {
        this.BS = BS;
    }

    public Object getBSName() {
        return BSName;
    }

    public void setBSName(Object BSName) {
        this.BSName = BSName;
    }

    public Object getType() {
        return Type;
    }

    public void setType(Object Type) {
        this.Type = Type;
    }

    public Object getTypeName() {
        return TypeName;
    }

    public void setTypeName(Object TypeName) {
        this.TypeName = TypeName;
    }

    public Object getPositionTime() {
        return PositionTime;
    }

    public void setPositionTime(Object PositionTime) {
        this.PositionTime = PositionTime;
    }

    public Object getPledgerName() {
        return PledgerName;
    }

    public void setPledgerName(Object PledgerName) {
        this.PledgerName = PledgerName;
    }

    public Object getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(Object CarNumber) {
        this.CarNumber = CarNumber;
    }

    public Object getModel() {
        return Model;
    }

    public void setModel(Object Model) {
        this.Model = Model;
    }

    public int getIsSocket() {
        return IsSocket;
    }

    public void setIsSocket(int IsSocket) {
        this.IsSocket = IsSocket;
    }

    public boolean isIsTrack() {
        return IsTrack;
    }

    public void setIsTrack(boolean IsTrack) {
        this.IsTrack = IsTrack;
    }

    public Object getStatusInfo() {
        return StatusInfo;
    }

    public void setStatusInfo(Object StatusInfo) {
        this.StatusInfo = StatusInfo;
    }

    public boolean isIsOnline() {
        return IsOnline;
    }

    public void setIsOnline(boolean IsOnline) {
        this.IsOnline = IsOnline;
    }

    public Object getObdField() {
        return obdField;
    }

    public void setObdField(Object obdField) {
        this.obdField = obdField;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }
    public void setTrackMode (int TrackMode){
        this.TrackMode = TrackMode;
    }
    //追踪模式的编号
    public int getTrackMode()
    {return TrackMode;}
    public void setIsFastTrack (boolean IsFastTrack){
        this.IsFastTrack = IsFastTrack;
    }
    public boolean getIsFastTrack()
    {
        return IsFastTrack;
    }//是否快速追踪

}
