package com.hivee2.mvp.model.bean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2018/3/15.
 */

public class ChooseAccountBean {
    private String id;
    private String text;
    private String state;
    private ArrayList<ChooseAccountBean> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<ChooseAccountBean> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChooseAccountBean> children) {
        this.children = children;
    }
}
