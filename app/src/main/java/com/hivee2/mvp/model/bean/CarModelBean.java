package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/16.
 */
public class CarModelBean {

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
     * model_list : [{"model_id":30452,"model_name":"2016款 奥迪A3 Sportback 35 TFSI 进取型","model_price":"18.49","model_year":"2016","discharge_standard":"国5"},{"model_id":30449,"model_name":"2016款 奥迪A3 Limousine 35 TFSI 进取型","model_price":"19.09","model_year":"2016","discharge_standard":"国5"},{"model_id":30451,"model_name":"2016款 奥迪A3 Sportback 35 TFSI 领英型","model_price":"20.92","model_year":"2016","discharge_standard":"国5"},{"model_id":30448,"model_name":"2016款 奥迪A3 Limousine 35 TFSI 领英型","model_price":"21.52","model_year":"2016","discharge_standard":"国5"},{"model_id":30450,"model_name":"2016款 奥迪A3 Sportback 35 TFSI 风尚型","model_price":"22.59","model_year":"2016","discharge_standard":"国5"},{"model_id":30447,"model_name":"2016款 奥迪A3 Limousine 35 TFSI 风尚型","model_price":"23.19","model_year":"2016","discharge_standard":"国5"},{"model_id":31624,"model_name":"2016款 奥迪A3 Sportback 35 TFSI 特别版","model_price":"23.88","model_year":"2016","discharge_standard":"国5"},{"model_id":31623,"model_name":"2016款 奥迪A3 Limousine 35 TFSI 特别版","model_price":"24.28","model_year":"2016","discharge_standard":"国5"},{"model_id":30446,"model_name":"2016款 奥迪A3 Sportback 40 TFSI 风尚型","model_price":"24.99","model_year":"2016","discharge_standard":"国5"},{"model_id":30444,"model_name":"2016款 奥迪A3 Limousine 40 TFSI 风尚型","model_price":"25.39","model_year":"2016","discharge_standard":"国5"},{"model_id":30445,"model_name":"2016款 奥迪A3 Sportback 40 TFSI 豪华型","model_price":"27.7","model_year":"2016","discharge_standard":"国5"},{"model_id":30443,"model_name":"2016款 奥迪A3 Limousine 40 TFSI 豪华型","model_price":"28.1","model_year":"2016","discharge_standard":"国5"},{"model_id":24712,"model_name":"2015款 奥迪A3 Sportback 35 TFSI 手动进取型","model_price":"18.49","model_year":"2015","discharge_standard":"国5"},{"model_id":24713,"model_name":"2015款 奥迪A3 Limousine 35 TFSI 手动进取型","model_price":"19.09","model_year":"2015","discharge_standard":"国5"},{"model_id":25126,"model_name":"2015款 奥迪A3 Sportback 35 TFSI 百万纪念智领型","model_price":"22.79","model_year":"2015","discharge_standard":"国5"},{"model_id":25127,"model_name":"2015款 奥迪A3 Limousine 35 TFSI 百万纪念智领型","model_price":"23.39","model_year":"2015","discharge_standard":"国5"},{"model_id":25128,"model_name":"2015款 奥迪A3 Sportback 35 TFSI 百万纪念舒享型","model_price":"23.98","model_year":"2015","discharge_standard":"国5"},{"model_id":25129,"model_name":"2015款 奥迪A3 Limousine 35 TFSI 百万纪念舒享型","model_price":"24.58","model_year":"2015","discharge_standard":"国5"},{"model_id":25130,"model_name":"2015款 奥迪A3 Sportback 35 TFSI 百万纪念乐享型","model_price":"27.57","model_year":"2015","discharge_standard":"国5"},{"model_id":25131,"model_name":"2015款 奥迪A3 Limousine 35 TFSI 百万纪念乐享型","model_price":"28.17","model_year":"2015","discharge_standard":"国5"},{"model_id":24714,"model_name":"2015款 奥迪A3 Sportback 40 TFSI 自动舒适型","model_price":"26.56","model_year":"2015","discharge_standard":"国5"},{"model_id":24716,"model_name":"2015款 奥迪A3 Limousine 40 TFSI 自动舒适型","model_price":"26.96","model_year":"2015","discharge_standard":"国5"},{"model_id":24715,"model_name":"2015款 奥迪A3 Sportback 40 TFSI 自动豪华型","model_price":"29.27","model_year":"2015","discharge_standard":"国5"},{"model_id":27482,"model_name":"2015款 奥迪A3 Limousine 40 TFSI 自动豪华型","model_price":"29.67","model_year":"2015","discharge_standard":"国5"},{"model_id":22064,"model_name":"2014款 奥迪A3 Sportback 35 TFSI 进取型","model_price":"19.99","model_year":"2014","discharge_standard":"国5"},{"model_id":23980,"model_name":"2014款 奥迪A3 Limousine 35 TFSI 进取型","model_price":"20.59","model_year":"2014","discharge_standard":"国5"},{"model_id":22065,"model_name":"2014款 奥迪A3 Sportback 35 TFSI 时尚型","model_price":"21.89","model_year":"2014","discharge_standard":"国5"},{"model_id":23981,"model_name":"2014款 奥迪A3 Limousine 35 TFSI 时尚型","model_price":"22.49","model_year":"2014","discharge_standard":"国5"},{"model_id":22066,"model_name":"2014款 奥迪A3 Sportback 35 TFSI 舒适型","model_price":"24.88","model_year":"2014","discharge_standard":"国5"},{"model_id":23982,"model_name":"2014款 奥迪A3 Limousine 35 TFSI 舒适型","model_price":"25.48","model_year":"2014","discharge_standard":"国5"},{"model_id":22067,"model_name":"2014款 奥迪A3 Sportback 35 TFSI 豪华型","model_price":"28.67","model_year":"2014","discharge_standard":"国5"},{"model_id":23983,"model_name":"2014款 奥迪A3 Limousine 35 TFSI 豪华型","model_price":"29.27","model_year":"2014","discharge_standard":"国5"}]
     */

    private int error_code;
    private Object error_msg;
    private int status;
    /**
     * model_id : 30452
     * model_name : 2016款 奥迪A3 Sportback 35 TFSI 进取型
     * model_price : 18.49
     * model_year : 2016
     * discharge_standard : 国5
     */

    private List<ModelListBean> model_list;

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

    public List<ModelListBean> getModel_list() {
        return model_list;
    }

    public void setModel_list(List<ModelListBean> model_list) {
        this.model_list = model_list;
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

    public static class ModelListBean {
        private int model_id;
        private String model_name;
        private String model_price;
        private String model_year;
        private String discharge_standard;

        public int getModel_id() {
            return model_id;
        }

        public void setModel_id(int model_id) {
            this.model_id = model_id;
        }

        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
        }

        public String getModel_price() {
            return model_price;
        }

        public void setModel_price(String model_price) {
            this.model_price = model_price;
        }

        public String getModel_year() {
            return model_year;
        }

        public void setModel_year(String model_year) {
            this.model_year = model_year;
        }

        public String getDischarge_standard() {
            return discharge_standard;
        }

        public void setDischarge_standard(String discharge_standard) {
            this.discharge_standard = discharge_standard;
        }
    }
}
