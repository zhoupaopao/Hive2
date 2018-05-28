package com.hivee2.mvp.model.bean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2018/3/15.
 */

public class SearchAccountBean {
    private int Result;
    private String ErrorMsg;
    private ArrayList<AccountBean>DataList;

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

    public ArrayList<AccountBean> getDataList() {
        return DataList;
    }

    public void setDataList(ArrayList<AccountBean> dataList) {
        DataList = dataList;
    }

    public  static  class AccountBean{
        private String UserID;
        private String UserName;
        private String CustomerName;

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }
    }
}
