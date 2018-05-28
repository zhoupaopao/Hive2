package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/25.
 */
public class CarMessage_Bean {

    /**
     * DataList : [{"carInfo":{"ID":"802a47d5618742cba083c2e68b3723d0","UsedAge":"0","CarBrand":"未选车品牌","CarCategory":"1","CarNumber":"7","CarValue":0,"CarVersion":"请先选择车系","Color":"","Mileage":0,"Remark":"","State":true,"UserID":"c0a0cfb7b2504c649fcc116a77b54c09","VIN":"","Erector":"","ErectPlace":"","CreatorID":"c0a0cfb7b2504c649fcc116a77b54c09","UpdatorID":"c0a0cfb7b2504c649fcc116a77b54c09"},"PledgeTime":"/Date(1471881600000)/","RepayTime":"/Date(1471881600000)/","ImageInfo":[]}]
     * Result : 0
     * ErrorMsg : null
     */

    private int Result;
    private Object ErrorMsg;
    /**
     * carInfo : {"ID":"802a47d5618742cba083c2e68b3723d0","UsedAge":"0","CarBrand":"未选车品牌","CarCategory":"1","CarNumber":"7","CarValue":0,"CarVersion":"请先选择车系","Color":"","Mileage":0,"Remark":"","State":true,"UserID":"c0a0cfb7b2504c649fcc116a77b54c09","VIN":"","Erector":"","ErectPlace":"","CreatorID":"c0a0cfb7b2504c649fcc116a77b54c09","UpdatorID":"c0a0cfb7b2504c649fcc116a77b54c09"}
     * PledgeTime : /Date(1471881600000)/
     * RepayTime : /Date(1471881600000)/
     * ImageInfo : []
     */

    private List<DataListBean> DataList;

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

    public List<DataListBean> getDataList() {
        return DataList;
    }

    public void setDataList(List<DataListBean> DataList) {
        this.DataList = DataList;
    }

    public static class DataListBean {
        /**
         * ID : 802a47d5618742cba083c2e68b3723d0
         * UsedAge : 0
         * CarBrand : 未选车品牌
         * CarCategory : 1
         * CarNumber : 7
         * CarValue : 0.0
         * CarVersion : 请先选择车系
         * Color :
         * Mileage : 0.0
         * Remark :
         * State : true
         * UserID : c0a0cfb7b2504c649fcc116a77b54c09
         * VIN :
         * Erector :
         * ErectPlace :
         * CreatorID : c0a0cfb7b2504c649fcc116a77b54c09
         * UpdatorID : c0a0cfb7b2504c649fcc116a77b54c09
         */

        private CarInfoBean carInfo;
        private String PledgeTime;
        private String RepayTime;
        private List<?> ImageInfo;

        public CarInfoBean getCarInfo() {
            return carInfo;
        }

        public void setCarInfo(CarInfoBean carInfo) {
            this.carInfo = carInfo;
        }

        public String getPledgeTime() {
            return PledgeTime;
        }

        public void setPledgeTime(String PledgeTime) {
            this.PledgeTime = PledgeTime;
        }

        public String getRepayTime() {
            return RepayTime;
        }

        public void setRepayTime(String RepayTime) {
            this.RepayTime = RepayTime;
        }

        public List<?> getImageInfo() {
            return ImageInfo;
        }

        public void setImageInfo(List<?> ImageInfo) {
            this.ImageInfo = ImageInfo;
        }

        public static class CarInfoBean {
            private String ID;
            private String UsedAge;
            private String CarBrand;
            private String CarCategory;
            private String CarNumber;
            private double CarValue;
            private String CarVersion;
            private String Color;
            private double Mileage;
            private String Remark;
            private boolean State;
            private String UserID;
            private String VIN;
            private String Erector;
            private String ErectPlace;
            private String CreatorID;
            private String UpdatorID;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getUsedAge() {
                return UsedAge;
            }

            public void setUsedAge(String UsedAge) {
                this.UsedAge = UsedAge;
            }

            public String getCarBrand() {
                return CarBrand;
            }

            public void setCarBrand(String CarBrand) {
                this.CarBrand = CarBrand;
            }

            public String getCarCategory() {
                return CarCategory;
            }

            public void setCarCategory(String CarCategory) {
                this.CarCategory = CarCategory;
            }

            public String getCarNumber() {
                return CarNumber;
            }

            public void setCarNumber(String CarNumber) {
                this.CarNumber = CarNumber;
            }

            public double getCarValue() {
                return CarValue;
            }

            public void setCarValue(double CarValue) {
                this.CarValue = CarValue;
            }

            public String getCarVersion() {
                return CarVersion;
            }

            public void setCarVersion(String CarVersion) {
                this.CarVersion = CarVersion;
            }

            public String getColor() {
                return Color;
            }

            public void setColor(String Color) {
                this.Color = Color;
            }

            public double getMileage() {
                return Mileage;
            }

            public void setMileage(double Mileage) {
                this.Mileage = Mileage;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public boolean isState() {
                return State;
            }

            public void setState(boolean State) {
                this.State = State;
            }

            public String getUserID() {
                return UserID;
            }

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public String getVIN() {
                return VIN;
            }

            public void setVIN(String VIN) {
                this.VIN = VIN;
            }

            public String getErector() {
                return Erector;
            }

            public void setErector(String Erector) {
                this.Erector = Erector;
            }

            public String getErectPlace() {
                return ErectPlace;
            }

            public void setErectPlace(String ErectPlace) {
                this.ErectPlace = ErectPlace;
            }

            public String getCreatorID() {
                return CreatorID;
            }

            public void setCreatorID(String CreatorID) {
                this.CreatorID = CreatorID;
            }

            public String getUpdatorID() {
                return UpdatorID;
            }

            public void setUpdatorID(String UpdatorID) {
                this.UpdatorID = UpdatorID;
            }
        }
    }
}
