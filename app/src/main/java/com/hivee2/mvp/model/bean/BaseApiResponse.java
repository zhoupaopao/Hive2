package com.hivee2.mvp.model.bean;

/**
 * Created by gewubin on 2016/7/14
 * email: gewubin95@qq.com
 * 每个返回json都有的参数
 */
public class BaseApiResponse{
    private int Result;
    private String ErrorMsg="";

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }
}
