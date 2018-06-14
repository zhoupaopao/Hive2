package com.hivee2.content;

/**
 * Created by gewubin on 2016/7/14
 * email: gewubin95@qq.com
 */
public class Api {
//    public static final String LOGIN="http://m1api.chetxt.com:8083/Customer.asmx/Jsonp_GetLogin";
//public static final String LOGIN="http://118.178.227.126:8083/Customer.asmx/Jsonp_GetLogin";//测试
    public static final String LOGIN="http://118.178.227.126:8084/api/userinfo/Login";
    public static final String BASE_API_URL=Constant.BaseUrl;
//    public static final String BASE_API_URL="http://118.178.227.126:8083/";//测试
    public static final String GET_ALARM_TYPE=BASE_API_URL+"Alarm.asmx/Jsonp_GetAlarmType";
    public static final String GET_DEVICE_BY_USERID=BASE_API_URL+"customer.asmx/Jsonp_GetDevicePositionByUserID";
    public static final String GET_ALARMS_BY_USER=BASE_API_URL+"Alarm.asmx/Json_GetAlarmsByUser_V2";
    public static final String GET_CHILD_USER_INFO=BASE_API_URL+"Customer.asmx/Jsonp_GetDirectChildUserInfo";
    public static final String GET_CHILD_TREE=BASE_API_URL+"Customer.asmx/Jsonp_GetChildTree";
    public static final String GET_USERIDBYCN=BASE_API_URL+"Customer.asmx/Jsonp_GetUserIDByCustomerName";


    public static final String GET_USER_INFO=BASE_API_URL+"Customer.asmx/Jsonp_GetUserInfoByUserID";
    public static final String MODIFY_USER_INFO=BASE_API_URL+"Customer.asmx/Jsonp_ModifyUserInfo";
    public static final String ADD_CHILD_USER=BASE_API_URL+"Customer.asmx/Jsonp_AddChildUser";
    public static final String SET_PWD=BASE_API_URL+"Customer.asmx/Jsonp_SetPwd";
    public static final String GET_RECURSIVE=BASE_API_URL+"Customer.asmx/GetRecursiveUserByUserID";
    public static final String  GET_TRACK=BASE_API_URL+"GPS.asmx/Jsonp_GetTrackByDeviceID";
    public static final String DEVICE_CLOSE=BASE_API_URL+"Goods.asmx/Jsonp_DeviceCloseTrack";
    public static final String DEVICE_OPEN=BASE_API_URL+"Goods.asmx/Jsonp_DeviceOpenTrack";
    public static final String DEVICE_OPEN2=BASE_API_URL+"Goods.asmx/Jsonp_OpenTrack";
    public static final String GET_REPLAY_BY_DEVICEID=BASE_API_URL+"GPS.asmx/Jsonp_GetReplayByDeviceID";
    public static final String GET_DEVICE_INFOBYUSER=BASE_API_URL+"Customer.asmx/Jsonp_GetDeviceInfoByUser";
    public static final String GET_GEO_FENCE_BY_USERID =BASE_API_URL+"Alarm.asmx/Jsonp_GetGeoFenceByUserID";
    public static final String GET_BIND_DEVICE_BYGEO =BASE_API_URL+"Alarm.asmx/Jsonp_GetBindDeviceByGeoFence";
    public static final String ADD_AREA_SCOPE =BASE_API_URL+"Alarm.asmx/Jsonp_AddAreaScope";
    public static final String MODIFY_AREA_SCOPE =BASE_API_URL+"Alarm.asmx/Jsonp_ModifyAreaScope";
    public static final String GET_PROVINCE_CITY =BASE_API_URL+"DataLib.asmx/Json_GetProvinceCity";
    public static final String QUERY_LOANINFO =BASE_API_URL+"Business.asmx/Json_GetLoanInfoList";

    public static final String Car_BindDevice=BASE_API_URL+"Business.asmx/Json_CarBindDevice";//提交设备列表
    public static final String UpLoad_file=BASE_API_URL+"Business.asmx/UploadFile";//提交设备图片

    public static final String ADD_PLEDGEMENT=BASE_API_URL+"Loan.asmx/Jsonp_AddPledgement";
    public static final String ADD_CAR_INFO=BASE_API_URL+"Loan.asmx/Jsonp_AddCarInfo";
    public static final String Check_LoanValid=BASE_API_URL+"Business.asmx/Json_CheckLoanValid";
    public static final String GET_PLEDGER_INFO=BASE_API_URL+"Loan.asmx/Jsonp_GetPledgerInfo";
//工单控制
    public static final String GETROLEBYUSERID=BASE_API_URL+"Customer.asmx/Json_GetRoleRightsByUserID";

    public static final String SETCAR_UNBIND_DEVICE=BASE_API_URL+"Loan.asmx/Jsonp_SetCarUnBindDevice";
    public static final String DELCAR=BASE_API_URL+"Loan.asmx/Jsonp_DelCar";
    public static final String MODIFY_PLEDGER=BASE_API_URL+"Loan.asmx/Jsonp_ModifyPledger";
    public static final String GET_CAR_INFO=BASE_API_URL+"Loan.asmx/Jsonp_GetCarInfo";
    public static final String MODIFY_CARINFO=BASE_API_URL+"Loan.asmx/Jsonp_ModifyCarInfo";
    public static final String CAR_BIND_DEVICE=BASE_API_URL+"Loan.asmx/Jsonp_CarBindDevice";
    public static final String GET_DEVICE_BYGEOFENCE=BASE_API_URL+"Alarm.asmx/Jsonp_GetDeviceByGeoFence";
    public static final String BIND_DEVICE_TOGEOFENCE=BASE_API_URL+"Alarm.asmx/Jsonp_BindDeviceToGeoFence";
    public static final String UNBIND_DEVICE_TOGEOFENCE=BASE_API_URL+"Alarm.asmx/Jsonp_UnBindDeviceToGeoFence";
    public static final String GET_SON_USER_TOKEN=BASE_API_URL+"Customer.asmx/Json_GetSonUserToken";
    public static final String DEL_PLEDGET=BASE_API_URL+"Loan.asmx/Jsonp_DelPledger";
public static final String Device_List=BASE_API_URL+"Customer.asmx/Jsonp_GetDeviceByValidDays";
    public static final String GRT_DEVICE_CLOCK=BASE_API_URL+"Goods.asmx/Jsonp_GetDeviceClock";

    public static final String SET_DEVICE_CLOCK=BASE_API_URL+"Goods.asmx/Json_SetDeviceTiming";
public static final String GetDeviceTiming=BASE_API_URL+"Goods.asmx/Jsonp_GetDeviceTiming";


    public static final String BASE_CAR_URL="http://118.178.227.126:8082/";
//    public static final String BASE_CAR_URL="http://erp.chetxt.com:8082/";
    public static final String GET_CAR_BRANDLIST=BASE_CAR_URL+"RiskService.asmx/Jsonp_GetCarBrandList";
    public static final String GET_CAR_SERIES=BASE_CAR_URL+"RiskService.asmx/Jsonp_GetCarSeriesList";
    public static final String GET_CAR_MODEL=BASE_CAR_URL+"RiskService.asmx/Jsonp_GetCarModelList";

    //工单管理接口

//    public static final String BASE_order_URL="http://order.chetxt.com:8090/";//测试接口

//    public static final String BASE_order_URL="http://m1api.chetxt.com:8090/";//正式接口
    public static final String BASE_order_URL="http://118.178.227.126:8090/";//测试接口
    public static final String GET_ORDER_LIST=BASE_order_URL+"v1/vehicleorder/selectlist";
    public static final String GET_SESSION=BASE_order_URL+"v1/vehicleorder/selectSessionByUserId";
    public static final String GET_ORDER_DETAIL=BASE_order_URL+"v1/vehicleorder/selectlistall";
    public static final String CREATE_ORDER=BASE_order_URL+"v1/vehicleorder/create";

    public static final String APP_SELECT_LIST=BASE_order_URL+"v1/vehicleorder/appselectListByUserId";
    public static final String APP_SELECT_DETAIL=BASE_order_URL+"v1/vehicleorder/appselectByUserId";
//    //获取省市区
public static final String APP_GetProvince=BASE_CAR_URL+"order.asmx/Json_GetProvince_City";//城市列表




}
