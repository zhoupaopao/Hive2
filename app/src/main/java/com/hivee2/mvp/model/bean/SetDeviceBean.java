package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/10/25.
 */
public class SetDeviceBean {

    /**
     * DeviceID : 2156113714
     * DataList : []
     * Result : 0
     * ErrorMsg : 设置成功
     */

    private String DeviceID;
    private int Result;
    private String ErrorMsg;
    private List<?> DataList;

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
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

    public List<?> getDataList() {
        return DataList;
    }

    public void setDataList(List<?> DataList) {
        this.DataList = DataList;
    }
}
