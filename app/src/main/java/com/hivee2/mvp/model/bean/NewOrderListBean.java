package com.hivee2.mvp.model.bean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2018/3/15.
 */

public class NewOrderListBean {
    private boolean success;
    private int code;
    private String message;
    private int count;
    private ArrayList<NewOrderBean>list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<NewOrderBean> getList() {
        return list;
    }

    public void setList(ArrayList<NewOrderBean> list) {
        this.list = list;
    }

    public  static  class NewOrderBean{
        private String pledgername;
        private String createtime;
        private String installaddress;
        private String statusname;
        private String id;
        private String carvin;
        private ArrayList<NewSBBean>deviceg;

        public String getPledgername() {
            return pledgername;
        }

        public void setPledgername(String pledgername) {
            this.pledgername = pledgername;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getInstalladdress() {
            return installaddress;
        }

        public void setInstalladdress(String installaddress) {
            this.installaddress = installaddress;
        }

        public String getStatusname() {
            return statusname;
        }

        public void setStatusname(String statusname) {
            this.statusname = statusname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCarvin() {
            return carvin;
        }

        public void setCarvin(String carvin) {
            this.carvin = carvin;
        }

        public ArrayList<NewSBBean> getDeviceg() {
            return deviceg;
        }

        public void setDeviceg(ArrayList<NewSBBean> deviceg) {
            this.deviceg = deviceg;
        }

        public  static  class NewSBBean{
            private int count;
            private String type;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
