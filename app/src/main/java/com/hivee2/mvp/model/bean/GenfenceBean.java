package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/8.
 */
public class GenfenceBean {

    /**
     * Count : 1
     * DataList : [{"BindGeoFence":true,"DeviceID":"2822683588","IMEI":"2822683588","InternalNum":"售后2","Model":"久劲 无线1号","CarNumber":"沪A8888811","PledgerName":"小姚12","BLng":121.51707776865234,"BLat":31.19548409041504,"Address":"上海市浦东新区东方路3558号,距临沂五村 0米","PositionTime":"2016-07-18 08:40:43"}]
     * Result : 0
     * ErrorMsg : null
     */

    private int Count;
    private int Result;
    private Object ErrorMsg;
    /**
     * BindGeoFence : true
     * DeviceID : 2822683588
     * IMEI : 2822683588
     * InternalNum : 售后2
     * Model : 久劲 无线1号
     * CarNumber : 沪A8888811
     * PledgerName : 小姚12
     * BLng : 121.51707776865234
     * BLat : 31.19548409041504
     * Address : 上海市浦东新区东方路3558号,距临沂五村 0米
     * PositionTime : 2016-07-18 08:40:43
     */

    private List<DataListBean> DataList;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public Object getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(Object ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public List<DataListBean> getDataList() {
        return DataList;
    }

    public void setDataList(List<DataListBean> DataList) {
        this.DataList = DataList;
    }

    public static class DataListBean {
        private boolean BindGeoFence;
        private String DeviceID;
        private String IMEI;
        private String InternalNum;
        private String Model;
        private String CarNumber;
        private String PledgerName;
        private double BLng;
        private double BLat;
        private String Address;
        private String PositionTime;

        public boolean isBindGeoFence() {
            return BindGeoFence;
        }

        public void setBindGeoFence(boolean BindGeoFence) {
            this.BindGeoFence = BindGeoFence;
        }

        public String getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(String DeviceID) {
            this.DeviceID = DeviceID;
        }

        public String getIMEI() {
            return IMEI;
        }

        public void setIMEI(String IMEI) {
            this.IMEI = IMEI;
        }

        public String getInternalNum() {
            return InternalNum;
        }

        public void setInternalNum(String InternalNum) {
            this.InternalNum = InternalNum;
        }

        public String getModel() {
            return Model;
        }

        public void setModel(String Model) {
            this.Model = Model;
        }

        public String getCarNumber() {
            return CarNumber;
        }

        public void setCarNumber(String CarNumber) {
            this.CarNumber = CarNumber;
        }

        public String getPledgerName() {
            return PledgerName;
        }

        public void setPledgerName(String PledgerName) {
            this.PledgerName = PledgerName;
        }

        public double getBLng() {
            return BLng;
        }

        public void setBLng(double BLng) {
            this.BLng = BLng;
        }

        public double getBLat() {
            return BLat;
        }

        public void setBLat(double BLat) {
            this.BLat = BLat;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPositionTime() {
            return PositionTime;
        }

        public void setPositionTime(String PositionTime) {
            this.PositionTime = PositionTime;
        }
    }
}
