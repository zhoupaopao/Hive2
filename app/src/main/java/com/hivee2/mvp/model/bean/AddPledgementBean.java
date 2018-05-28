package com.hivee2.mvp.model.bean;

/**
 * Created by 狄飞 on 2016/8/12.
 */
public class AddPledgementBean {

    /**
     * PledgementID : 8fdd9b76837d4c8294f7c11707287976
     * Result : 0
     * ErrorMsg : null
     */

    private String PledgementID;
    private int Result;
    private Object ErrorMsg;

    public String getPledgementID() {
        return PledgementID;
    }

    public void setPledgementID(String PledgementID) {
        this.PledgementID = PledgementID;
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
