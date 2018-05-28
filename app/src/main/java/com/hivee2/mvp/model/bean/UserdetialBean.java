package com.hivee2.mvp.model.bean;

/**
 * Created by 狄飞 on 2016/7/22.
 */
public class UserdetialBean extends BaseApiResponse{

    /**
     * UserID : c0a0cfb7b2504c649fcc116a77b54c09
     * UserName : wgp
     * ParentID : null
     * SonID : null
     * Level : 0
     * Response_Customer : {"CustomerName":"万高朋","CustomerType":true,"ContactPerson":"123","MobilePhone":"13915800752","Address":"东梁溪路","BLng":120.314502,"BLat":31.587966,"Result":0,"ErrorMsg":null}
     * TempQty : 116
     * AleaQty : 46
     * Result : 0
     * ErrorMsg : null
     */

    private String UserID;
    private String UserName;
    private Object ParentID;
    private Object SonID;
    private int Level;
    /**
     * CustomerName : 万高朋
     * CustomerType : true
     * ContactPerson : 123
     * MobilePhone : 13915800752
     * Address : 东梁溪路
     * BLng : 120.314502
     * BLat : 31.587966
     * Result : 0
     * ErrorMsg : null
     */

    private ResponseCustomerBean Response_Customer;
    private int TempQty;
    private int AleaQty;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public Object getParentID() {
        return ParentID;
    }

    public void setParentID(Object ParentID) {
        this.ParentID = ParentID;
    }

    public Object getSonID() {
        return SonID;
    }

    public void setSonID(Object SonID) {
        this.SonID = SonID;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public ResponseCustomerBean getResponse_Customer() {
        return Response_Customer;
    }

    public void setResponse_Customer(ResponseCustomerBean Response_Customer) {
        this.Response_Customer = Response_Customer;
    }

    public int getTempQty() {
        return TempQty;
    }

    public void setTempQty(int TempQty) {
        this.TempQty = TempQty;
    }

    public int getAleaQty() {
        return AleaQty;
    }

    public void setAleaQty(int AleaQty) {
        this.AleaQty = AleaQty;
    }

    public static class ResponseCustomerBean {
        private String CustomerName;
        private boolean CustomerType;
        private String ContactPerson;
        private String MobilePhone;
        private String Address;
        private double BLng;
        private double BLat;
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
