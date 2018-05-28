package com.hivee2.mvp.model.bean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2018/3/19.
 */

public class RoleRightsBeans {
    private String userid;
    private int Result;
    private String ErrorMsg;
    private ArrayList<RoleRight>DataList;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public ArrayList<RoleRight> getDataList() {
        return DataList;
    }

    public void setDataList(ArrayList<RoleRight> dataList) {
        DataList = dataList;
    }

    public   static  class RoleRight{
        private int menu_id;
        private boolean is_ban;
        private boolean can_show;
        private boolean can_read;
        private boolean can_manage;

        public int getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(int menu_id) {
            this.menu_id = menu_id;
        }

        public boolean isIs_ban() {
            return is_ban;
        }

        public void setIs_ban(boolean is_ban) {
            this.is_ban = is_ban;
        }

        public boolean isCan_show() {
            return can_show;
        }

        public void setCan_show(boolean can_show) {
            this.can_show = can_show;
        }

        public boolean isCan_read() {
            return can_read;
        }

        public void setCan_read(boolean can_read) {
            this.can_read = can_read;
        }

        public boolean isCan_manage() {
            return can_manage;
        }

        public void setCan_manage(boolean can_manage) {
            this.can_manage = can_manage;
        }
    }
}
