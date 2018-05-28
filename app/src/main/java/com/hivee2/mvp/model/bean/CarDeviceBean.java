package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/9/1.
 */
public class CarDeviceBean {

    /**
     * CarDeviceIDList : ["05057388f8f14553a87093f67ae873f9"]
     * Result : 0
     * ErrorMsg : null
     */

    private int Result;
    private Object ErrorMsg;
    private List<String> CarDeviceIDList;

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

    public List<String> getCarDeviceIDList() {
        return CarDeviceIDList;
    }

    public void setCarDeviceIDList(List<String> CarDeviceIDList) {
        this.CarDeviceIDList = CarDeviceIDList;
    }
}
