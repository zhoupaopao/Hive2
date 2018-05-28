package com.hivee2.mvp.model.bean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2018/3/13.
 */

public class OrderListBean {
    private int code;
    private int count;
    private ArrayList<OrderBean>list;
    private boolean success;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<OrderBean> getList() {
        return list;
    }

    public void setList(ArrayList<OrderBean> list) {
        this.list = list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class OrderBean{
        private String car_loantype;
        private String car_num;
        private String car_vin;
        private String createtime;
        private String id;
        private String install_address;
        private String install_cont_per;
        private String install_cont_pho;
        private String pledger_idcard;
        private String pledger_name;
        private String pledger_phone;
        private String status;
        private int service_life;

        public String getCar_loantype() {
            return car_loantype;
        }

        public void setCar_loantype(String car_loantype) {
            this.car_loantype = car_loantype;
        }

        public String getCar_num() {
            return car_num;
        }

        public void setCar_num(String car_num) {
            this.car_num = car_num;
        }

        public String getCar_vin() {
            return car_vin;
        }

        public void setCar_vin(String car_vin) {
            this.car_vin = car_vin;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInstall_address() {
            return install_address;
        }

        public void setInstall_address(String install_address) {
            this.install_address = install_address;
        }

        public String getInstall_cont_per() {
            return install_cont_per;
        }

        public void setInstall_cont_per(String install_cont_per) {
            this.install_cont_per = install_cont_per;
        }

        public String getInstall_cont_pho() {
            return install_cont_pho;
        }

        public void setInstall_cont_pho(String install_cont_pho) {
            this.install_cont_pho = install_cont_pho;
        }

        public String getPledger_idcard() {
            return pledger_idcard;
        }

        public void setPledger_idcard(String pledger_idcard) {
            this.pledger_idcard = pledger_idcard;
        }

        public String getPledger_name() {
            return pledger_name;
        }

        public void setPledger_name(String pledger_name) {
            this.pledger_name = pledger_name;
        }

        public String getPledger_phone() {
            return pledger_phone;
        }

        public void setPledger_phone(String pledger_phone) {
            this.pledger_phone = pledger_phone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getService_life() {
            return service_life;
        }

        public void setService_life(int service_life) {
            this.service_life = service_life;
        }
    }
}
