package com.hivee2.mvp.model.bean;

import java.io.Serializable;

/**
 * Created by 狄飞 on 2016/11/3.
 */
public class LoginBean  implements Serializable {



    private String UserID;
    private String UserName;
    private String Token;


    private ServerBean Server;
    /**
     * CustomerName : 万高朋
     * CustomerType : false
     * ContactPerson : 123
     * MobilePhone : 13915800752
     * Address : 东梁溪路
     * BLng : null
     * BLat : null
     * Result : 0
     * ErrorMsg : null
     */

    private ResponseCustomerBean Response_Customer;
    private int Result;
    private Object ErrorMsg;

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

    public ServerBean getServer() {
        return Server;
    }

    public void setServer(ServerBean Server) {
        this.Server = Server;
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

    public static class ServerBean implements Serializable{
        private int ID;
        private String Name;
        private String SystemServer;
        private String NCWS;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSystemServer() {
            return SystemServer;
        }

        public void setSystemServer(String SystemServer) {
            this.SystemServer = SystemServer;
        }

        public String getNCWS() {
            return NCWS;
        }

        public void setNCWS(String NCWS) {
            this.NCWS = NCWS;
        }
    }

    public static class ResponseCustomerBean implements Serializable{
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
