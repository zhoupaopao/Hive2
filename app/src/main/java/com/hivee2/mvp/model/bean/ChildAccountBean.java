package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/28.
 */
public class ChildAccountBean extends BaseApiResponse {

    /**
     * Result : 0
     * ErrorMsg : 成功
     * UserID : c0a0cfb7b2504c649fcc116a77b54c09
     * CustomerName : 万高朋
     * TempQty : 0
     * AleaQty : 0
     * CarQty : 0
     * children : [{"UserID":"132c042a4fba4f9b98f8ef979d679592","CustomerName":"cccccc","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"05bbe5d412704d6c96d1251ef1525d5a","CustomerName":"test","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"460af515fa6c49ada0247a596952c490","CustomerName":"myg","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"9ddeec3b96d04b338146297ba8c5f11e","CustomerName":"yonglun","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"4fe5ecfa456b4b2389d6a55a24855a87","CustomerName":"bbbb","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"ab661b49ff67494a8e0780148380089f","CustomerName":"55","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"8dca5b9a15d044ea9c94e924f8258a38","CustomerName":"培训1","TempQty":0,"AleaQty":0,"CarQty":0,"children":[{"UserID":"3abbb15b68574b0292e7464ef16f9f48","CustomerName":"培训2","TempQty":0,"AleaQty":0,"CarQty":0,"children":[{"UserID":"e4bceb673e9e4e129c445bda6d3b0334","CustomerName":"培训2-1","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"db89b52211c9474f80e176e9e3cf1d4e","CustomerName":"培训3","TempQty":0,"AleaQty":0,"CarQty":0,"children":[{"UserID":"881a9273182c438da64857589d7910d8","CustomerName":"培训3-1","TempQty":0,"AleaQty":0,"CarQty":0}]},{"UserID":"653d65ebadbf4858b9f407e30ea060bc","CustomerName":"李彪","TempQty":0,"AleaQty":0,"CarQty":0}]}]},{"UserID":"09a3b4209c5744f1822259d48eac0ea0","CustomerName":"wgp1","TempQty":0,"AleaQty":0,"CarQty":0},{"UserID":"e30fb19562b8468cb30fdab970f0458e","CustomerName":"asdd","TempQty":0,"AleaQty":0,"CarQty":0}]
     */

    private String UserID;
    private String CustomerName;
    private int TempQty;
    private int AleaQty;
    private int CarQty;
    /**
     * UserID : 132c042a4fba4f9b98f8ef979d679592
     * CustomerName : cccccc
     * TempQty : 0
     * AleaQty : 0
     * CarQty : 0
     */

    private List<ChildAccountBean> children;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
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

    public int getCarQty() {
        return CarQty;
    }

    public void setCarQty(int CarQty) {
        this.CarQty = CarQty;
    }

    public List<ChildAccountBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildAccountBean> children) {
        this.children = children;
    }


}
