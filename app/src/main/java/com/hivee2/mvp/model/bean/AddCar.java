package com.hivee2.mvp.model.bean;

/**
 * Created by 狄飞 on 2016/8/19.
 */
public class AddCar {

    /**
     * CarInfoID : 30817541495b4aba8f4476ffe904e43a
     * PledgeCarID : 2c0a77e21f1d4469983ad7f8a367bc6d
     * Result : 0
     * ErrorMsg : null
     */

    private String CarInfoID;
    private String PledgeCarID;
    private int Result;
    private Object ErrorMsg;

    public String getCarInfoID() {
        return CarInfoID;
    }

    public void setCarInfoID(String CarInfoID) {
        this.CarInfoID = CarInfoID;
    }

    public String getPledgeCarID() {
        return PledgeCarID;
    }

    public void setPledgeCarID(String PledgeCarID) {
        this.PledgeCarID = PledgeCarID;
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
}
