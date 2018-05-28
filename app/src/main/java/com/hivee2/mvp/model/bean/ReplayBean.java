package com.hivee2.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 狄飞 on 2016/8/5.
 */
public class ReplayBean extends BaseApiResponse{

    /**
     * DataList : [{"lng":117.3360595703125,"lat":31.79669189453125,"BLng":117.3481480703125,"BLat":31.80089355453125,"Speed":0,"PositionTime":"2015-12-15 09:00:22","Type":"6100","Address":"安徽省合肥市包河区繁华大道,距包河花园前进小区 97米"},{"lng":117.3360595703125,"lat":31.79669189453125,"BLng":117.3481480703125,"BLat":31.80089355453125,"Speed":0,"PositionTime":"2015-12-16 09:00:22","Type":"6100","Address":"安徽省合肥市包河区繁华大道,距包河花园前进小区 97米"}]
     * Count : 2
     * HighFrequency : false
     * Result : 0
     * ErrorMsg : 共计2条轨迹数据
     */

    private int Count;
    private boolean HighFrequency;
    /**
     * lng : 117.3360595703125
     * lat : 31.79669189453125
     * BLng : 117.3481480703125
     * BLat : 31.80089355453125
     * Speed : 0
     * PositionTime : 2015-12-15 09:00:22
     * Type : 6100
     * Address : 安徽省合肥市包河区繁华大道,距包河花园前进小区 97米
     */

    private List<DataListBean> DataList;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public boolean isHighFrequency() {
        return HighFrequency;
    }

    public void setHighFrequency(boolean HighFrequency) {
        this.HighFrequency = HighFrequency;
    }

    public List<DataListBean> getDataList() {
        return DataList;
    }

    public void setDataList(List<DataListBean> DataList) {
        this.DataList = DataList;
    }

    public static class DataListBean implements Serializable {
        private double lng;
        private double lat;
        private double BLng;
        private double BLat;
        private int Speed;
        private String PositionTime;
        private String Type;
        private String Address;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
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

        public int getSpeed() {
            return Speed;
        }

        public void setSpeed(int Speed) {
            this.Speed = Speed;
        }

        public String getPositionTime() {
            return PositionTime;
        }

        public void setPositionTime(String PositionTime) {
            this.PositionTime = PositionTime;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }
    }
}
