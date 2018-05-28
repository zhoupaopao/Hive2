package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/10/25.
 */
public class GetDeviceBean {

    /**
     * DeviceID : 2156241730
     * DataList : [{"clock":"00:00","repeatmode":1}]
     * Result : 0
     * ErrorMsg : null
     */

    private String DeviceID;
    private int Result;
    private Object ErrorMsg;
//    private String cansetclock;
//    private String cansetweek;
//    private String isweek;
    /**
     * clock : 00:00
     * repeatmode : 1
     */

    private List<DataListBean> DataList;

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

//    public String getcansetclock() {
//        return cansetclock;
//    }
//
//    public void setcansetclock(String cansetclock) {
//        this.cansetclock = cansetclock;
//    }
//
//    public String getcansetweek() {
//        return cansetweek;
//    }
//
//    public void setcansetweek(String cansetweek) {
//        this.cansetweek = cansetweek;
//    }
//
//    public String getisweek() {
//        return isweek;
//    }
//
//    public void setisweek(String isweek) {
//        this.isweek = isweek;
//    }

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
        private String clock;
        private int repeatmode;
        private int weekday;

        public String getClock() {
            return clock;
        }

        public void setClock(String clock) {
            this.clock = clock;
        }

        public int getweekday() {
            return weekday;
        }

        public void setweekday(int weekday) {
            this.weekday = weekday;
        }

        public int getRepeatmode() {
            return repeatmode;
        }

        public void setRepeatmode(int repeatmode) {
            this.repeatmode = repeatmode;
        }
    }
}
