package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/13.
 */
public class CarSeries {

    /**
     * Result : 0
     * ErrorMsg : null
     */

    private ResultAnzerBean Result_Anzer;
    /**
     * Result_Anzer : {"Result":0,"ErrorMsg":null}
     * error_code : 0
     * error_msg : null
     * status : 1
     * series_list : [{"series_id":5,"series_name":"奥迪A3","series_group_name":"一汽奥迪"},{"series_id":6,"series_name":"奥迪A4","series_group_name":"一汽奥迪"},{"series_id":1,"series_name":"奥迪A4L","series_group_name":"一汽奥迪"},{"series_id":2285,"series_name":"奥迪A6","series_group_name":"一汽奥迪"},{"series_id":3,"series_name":"奥迪A6L","series_group_name":"一汽奥迪"},{"series_id":4,"series_name":"奥迪Q3","series_group_name":"一汽奥迪"},{"series_id":2,"series_name":"奥迪Q5","series_group_name":"一汽奥迪"},{"series_id":13,"series_name":"奥迪A1","series_group_name":"进口奥迪"},{"series_id":16,"series_name":"奥迪A3(进口)","series_group_name":"进口奥迪"},{"series_id":18,"series_name":"奥迪A4(进口)","series_group_name":"进口奥迪"},{"series_id":12,"series_name":"奥迪A5","series_group_name":"进口奥迪"},{"series_id":17,"series_name":"奥迪A6(进口)","series_group_name":"进口奥迪"},{"series_id":14,"series_name":"奥迪A7","series_group_name":"进口奥迪"},{"series_id":10,"series_name":"奥迪A8L","series_group_name":"进口奥迪"},{"series_id":26,"series_name":"奥迪Allroad quattro","series_group_name":"进口奥迪"},{"series_id":22,"series_name":"奥迪Q3(进口)","series_group_name":"进口奥迪"},{"series_id":19,"series_name":"奥迪Q5(进口)","series_group_name":"进口奥迪"},{"series_id":9,"series_name":"奥迪Q7","series_group_name":"进口奥迪"},{"series_id":11,"series_name":"奥迪R8","series_group_name":"进口奥迪"},{"series_id":2310,"series_name":"奥迪S3","series_group_name":"进口奥迪"},{"series_id":25,"series_name":"奥迪S4","series_group_name":"进口奥迪"},{"series_id":21,"series_name":"奥迪S5","series_group_name":"进口奥迪"},{"series_id":24,"series_name":"奥迪S6","series_group_name":"进口奥迪"},{"series_id":23,"series_name":"奥迪S7","series_group_name":"进口奥迪"},{"series_id":20,"series_name":"奥迪S8","series_group_name":"进口奥迪"},{"series_id":2220,"series_name":"奥迪SQ5","series_group_name":"进口奥迪"},{"series_id":15,"series_name":"奥迪TT","series_group_name":"进口奥迪"},{"series_id":28,"series_name":"奥迪RS5","series_group_name":"进口奥迪RS"},{"series_id":2564,"series_name":"奥迪RS6","series_group_name":"进口奥迪RS"},{"series_id":29,"series_name":"奥迪RS7","series_group_name":"进口奥迪RS"}]
     */

    private int error_code;
    private Object error_msg;
    private int status;
    /**
     * series_id : 5
     * series_name : 奥迪A3
     * series_group_name : 一汽奥迪
     */

    private List<SeriesListBean> series_list;

    public ResultAnzerBean getResult_Anzer() {
        return Result_Anzer;
    }

    public void setResult_Anzer(ResultAnzerBean Result_Anzer) {
        this.Result_Anzer = Result_Anzer;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Object getError_msg() {
        return error_msg;
    }

    public void setError_msg(Object error_msg) {
        this.error_msg = error_msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<SeriesListBean> getSeries_list() {
        return series_list;
    }

    public void setSeries_list(List<SeriesListBean> series_list) {
        this.series_list = series_list;
    }

    public static class ResultAnzerBean {
        private int Result;
        private Object ErrorMsg;

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

    public static class SeriesListBean {
        private int series_id;
        private String series_name;
        private String series_group_name;

        public int getSeries_id() {
            return series_id;
        }

        public void setSeries_id(int series_id) {
            this.series_id = series_id;
        }

        public String getSeries_name() {
            return series_name;
        }

        public void setSeries_name(String series_name) {
            this.series_name = series_name;
        }

        public String getSeries_group_name() {
            return series_group_name;
        }

        public void setSeries_group_name(String series_group_name) {
            this.series_group_name = series_group_name;
        }
    }
}

