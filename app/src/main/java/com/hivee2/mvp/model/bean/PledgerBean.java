package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/21.
 */
public class PledgerBean extends BaseApiResponse{


    /**
     * ID : 3d036bf5ab1b4b3ebe734280cade3eb3
     * UserID : c0a0cfb7b2504c649fcc116a77b54c09
     * PledgerName : sadasd
     * Address :
     * ContactName :
     * ContactPhone :
     * PledgerImage :
     * IDCardType : 身份证
     * IDCardNumber :
     * IDCardScanning :
     * Remak :
     * CreatorID : c0a0cfb7b2504c649fcc116a77b54c09
     * CreateTime : /Date(1469790322750)/
     * UpdatorID : c0a0cfb7b2504c649fcc116a77b54c09
     * UpdateTime : /Date(1469790322750)/
     * State : true
     */

    private List<DataListBean> DataList;

    public List<DataListBean> getDataList() {
        return DataList;
    }

    public void setDataList(List<DataListBean> DataList) {
        this.DataList = DataList;
    }

    public static class DataListBean {
        private String ID;
        private String UserID;
        private String PledgerName;
        private String Address;
        private String ContactName;
        private String ContactPhone;
        private String PledgerImage;
        private String IDCardType;
        private String IDCardNumber;
        private String IDCardScanning;
        private String Remak;
        private String CreatorID;
        private String CreateTime;
        private String UpdatorID;
        private String UpdateTime;
        private boolean State;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getPledgerName() {
            return PledgerName;
        }

        public void setPledgerName(String PledgerName) {
            this.PledgerName = PledgerName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getContactName() {
            return ContactName;
        }

        public void setContactName(String ContactName) {
            this.ContactName = ContactName;
        }

        public String getContactPhone() {
            return ContactPhone;
        }

        public void setContactPhone(String ContactPhone) {
            this.ContactPhone = ContactPhone;
        }

        public String getPledgerImage() {
            return PledgerImage;
        }

        public void setPledgerImage(String PledgerImage) {
            this.PledgerImage = PledgerImage;
        }

        public String getIDCardType() {
            return IDCardType;
        }

        public void setIDCardType(String IDCardType) {
            this.IDCardType = IDCardType;
        }

        public String getIDCardNumber() {
            return IDCardNumber;
        }

        public void setIDCardNumber(String IDCardNumber) {
            this.IDCardNumber = IDCardNumber;
        }

        public String getIDCardScanning() {
            return IDCardScanning;
        }

        public void setIDCardScanning(String IDCardScanning) {
            this.IDCardScanning = IDCardScanning;
        }

        public String getRemak() {
            return Remak;
        }

        public void setRemak(String Remak) {
            this.Remak = Remak;
        }

        public String getCreatorID() {
            return CreatorID;
        }

        public void setCreatorID(String CreatorID) {
            this.CreatorID = CreatorID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdatorID() {
            return UpdatorID;
        }

        public void setUpdatorID(String UpdatorID) {
            this.UpdatorID = UpdatorID;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public boolean isState() {
            return State;
        }

        public void setState(boolean State) {
            this.State = State;
        }
    }
}
