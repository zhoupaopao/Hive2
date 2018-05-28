package com.hivee2.mvp.model.bean;

/**
 * Created by 狄飞 on 2016/10/10.
 */
public class TokenBean {

    /**
     * SonUserToken : dWkGRkjX6nN1S6s8e30Rzcn8Paa5GV5zsUXVLUUsYDhakg5LFfBQ4Jlw05RA3tNHn8pqr6Q+BTIeJ6GbzEkeCTE2yEY1iPmI2LOABQ7H/+E0oDMrdyBM5fGDK7ke44ME3c1FDnHy/uu7xkTCG+1sgGy10bvBnc4Ujcs8wkONVgs=
     * System : null
     * Response_Customer : {"CustomerName":"人人聚财重庆一店","CustomerType":true,"ContactPerson":"","MobilePhone":"","Address":"","BLng":null,"BLat":null,"Result":0,"ErrorMsg":null}
     * Result : 0
     * ErrorMsg : null
     */

    private String SonUserToken;
    private Object System;
    /**
     * CustomerName : 人人聚财重庆一店
     * CustomerType : true
     * ContactPerson :
     * MobilePhone :
     * Address :
     * BLng : null
     * BLat : null
     * Result : 0
     * ErrorMsg : null
     */

    private ResponseCustomerBean Response_Customer;
    private int Result;
    private Object ErrorMsg;

    public String getSonUserToken() {
        return SonUserToken;
    }

    public void setSonUserToken(String SonUserToken) {
        this.SonUserToken = SonUserToken;
    }

    public Object getSystem() {
        return System;
    }

    public void setSystem(Object System) {
        this.System = System;
    }

    public ResponseCustomerBean getResponse_Customer() {
        return Response_Customer;
    }

    public void setResponse_Customer(ResponseCustomerBean Response_Customer) {
        this.Response_Customer = Response_Customer;
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

    public static class ResponseCustomerBean {
        private String CustomerName;
        private boolean CustomerType;
        private String ContactPerson;
        private String MobilePhone;
        private String Address;
        private Object BLng;
        private Object BLat;
        private int Result;
        private Object ErrorMsg;

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public boolean isCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(boolean CustomerType) {
            this.CustomerType = CustomerType;
        }

        public String getContactPerson() {
            return ContactPerson;
        }

        public void setContactPerson(String ContactPerson) {
            this.ContactPerson = ContactPerson;
        }

        public String getMobilePhone() {
            return MobilePhone;
        }

        public void setMobilePhone(String MobilePhone) {
            this.MobilePhone = MobilePhone;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
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
}
