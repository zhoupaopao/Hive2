package com.hivee2.mvp.model.bean;

import java.io.Serializable;

/*

 */
public class UserBean extends BaseApiResponse implements Serializable{


    /**
     * UserID : c0a0cfb7b2504c649fcc116a77b54c09
     * UserName : wgp
     * Token : m2foye45dYGNJ2pPGyEaiGpjzTcpqI3KQ4t+wLPflK/mwUfkc5fglG5uoKzA6NCXcypxtbneQtUPWWaM31c7QvWhKHd6/U72wP67vPd1EfJrUVy5muYyoxXvmBG3wS5ChSsqgtCHKEzTuv/jCpVb0Y8ICV4YcDXcc/hXTBFOzMg=
     * System : 2
     * Response_Customer : {"CustomerName":"万高朋","CustomerType":true,"ContactPerson":"123","MobilePhone":"13915800752","Address":"东梁溪路","BLng":0,"BLat":0,"Result":0,"ErrorMsg":null}
     * TempQty : 0
     * AleaQty : 0
     */

    private String UserID;
    private String UserName;
    private String Token;
    private String System;
    /**
     * CustomerName : 万高朋
     * CustomerType : true
     * ContactPerson : 123
     * MobilePhone : 13915800752
     * Address : 东梁溪路
     * BLng : 0
     * BLat : 0
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

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getSystem() {
        return System;
    }

    public void setSystem(String System) {
        this.System = System;
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


    public static class ResponseCustomerBean implements Serializable{
        private String CustomerName;
        private boolean CustomerType;
        private String ContactPerson;
        private String MobilePhone;
        private String Address;
        private int BLng;
        private int BLat;


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

        public int getBLng() {
            return BLng;
        }

        public void setBLng(int BLng) {
            this.BLng = BLng;
        }

        public int getBLat() {
            return BLat;
        }

        public void setBLat(int BLat) {
            this.BLat = BLat;
        }


    }
}
