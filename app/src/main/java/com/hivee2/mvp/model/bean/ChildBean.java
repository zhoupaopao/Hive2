package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/21.
 */
public class ChildBean extends BaseApiResponse{

    /**
     * UserID : 132c042a4fba4f9b98f8ef979d679592
     * UserName : cccccc
     * ParentID : c0a0cfb7b2504c649fcc116a77b54c09
     * SonID : 132c042a4fba4f9b98f8ef979d679592
     * Level : 0
     * Response_Customer : {"CustomerName":"cccccc","CustomerType":false,"ContactPerson":"1","MobilePhone":"","Address":null,"BLng":null,"BLat":null,"Result":0,"ErrorMsg":null}
     * TempQty : 0
     * AleaQty : 0
     * Result : 0
     * ErrorMsg : null
     */

    private List<DataListBean> DataList;

    public List<DataListBean> getDataList() {
        return DataList;
    }

    public void setDataList(List<DataListBean> DataList) {
        this.DataList = DataList;
    }

    public static class DataListBean {
        private String UserID;
        private String UserName;
        private String ParentID;
        private String SonID;
        private int Level;
        /**
         * CustomerName : cccccc
         * CustomerType : false
         * ContactPerson : 1
         * MobilePhone :
         * Address : null
         * BLng : null
         * BLat : null
         * Result : 0
         * ErrorMsg : null
         */

        private ResponseCustomerBean Response_Customer;
        private int TempQty;
        private int AleaQty;
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

        public String getParentID() {
            return ParentID;
        }

        public void setParentID(String ParentID) {
            this.ParentID = ParentID;
        }

        public String getSonID() {
            return SonID;
        }

        public void setSonID(String SonID) {
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
            private Object Address;
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

            public Object getAddress() {
                return Address;
            }

            public void setAddress(Object Address) {
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
}
