package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/13.
 */
public class CarBrandBean {

    /**
     * Result : 0
     * ErrorMsg : 成功
     */

    private ResultAnzerBean Result_Anzer;
    /**
     * Result_Anzer : {"Result":0,"ErrorMsg":"成功"}
     * error_code : 1
     * error_msg : 远程获取不到数据
     * status : 0
     * brand_list : [{"brand_id":1,"brand_name":"奥迪","initial":"A"},{"brand_id":3,"brand_name":"阿尔法·罗密欧","initial":"A"},{"brand_id":2,"brand_name":"阿斯顿·马丁","initial":"A"},{"brand_id":20,"brand_name":"保斐利","initial":"B"},{"brand_id":11,"brand_name":"保时捷","initial":"B"},{"brand_id":6,"brand_name":"别克","initial":"B"},{"brand_id":13,"brand_name":"北京汽车","initial":"B"},{"brand_id":14,"brand_name":"北汽制造","initial":"B"},{"brand_id":17,"brand_name":"北汽威旺","initial":"B"},{"brand_id":156,"brand_name":"北汽幻速","initial":"B"},{"brand_id":115,"brand_name":"北汽绅宝","initial":"B"},{"brand_id":12,"brand_name":"奔腾","initial":"B"},{"brand_id":9,"brand_name":"奔驰","initial":"B"},{"brand_id":7,"brand_name":"宝马","initial":"B"},{"brand_id":15,"brand_name":"宝骏","initial":"B"},{"brand_id":144,"brand_name":"宝龙","initial":"B"},{"brand_id":16,"brand_name":"宾利","initial":"B"},{"brand_id":18,"brand_name":"布加迪","initial":"B"},{"brand_id":5,"brand_name":"本田","initial":"B"},{"brand_id":10,"brand_name":"标致","initial":"B"},{"brand_id":8,"brand_name":"比亚迪","initial":"B"},{"brand_id":24,"brand_name":"昌河","initial":"C"},{"brand_id":22,"brand_name":"长城","initial":"C"},{"brand_id":23,"brand_name":"长安商用","initial":"C"},{"brand_id":21,"brand_name":"长安轿车","initial":"C"},{"brand_id":31,"brand_name":"DS","initial":"D"},{"brand_id":27,"brand_name":"东南","initial":"D"},{"brand_id":33,"brand_name":"东风","initial":"D"},{"brand_id":28,"brand_name":"东风小康","initial":"D"},{"brand_id":32,"brand_name":"东风风度","initial":"D"},{"brand_id":30,"brand_name":"东风风神","initial":"D"},{"brand_id":26,"brand_name":"东风风行","initial":"D"},{"brand_id":25,"brand_name":"大众","initial":"D"},{"brand_id":142,"brand_name":"大迪汽车","initial":"D"},{"brand_id":34,"brand_name":"大通","initial":"D"},{"brand_id":29,"brand_name":"道奇","initial":"D"},{"brand_id":36,"brand_name":"丰田","initial":"F"},{"brand_id":38,"brand_name":"法拉利","initial":"F"},{"brand_id":35,"brand_name":"福特","initial":"F"},{"brand_id":39,"brand_name":"福田","initial":"F"},{"brand_id":40,"brand_name":"福迪","initial":"F"},{"brand_id":37,"brand_name":"菲亚特","initial":"F"},{"brand_id":41,"brand_name":"飞驰商务车","initial":"F"},{"brand_id":162,"brand_name":"福汽启腾","initial":"F"},{"brand_id":47,"brand_name":"GMC","initial":"G"},{"brand_id":48,"brand_name":"光冈","initial":"G"},{"brand_id":44,"brand_name":"广汽传祺","initial":"G"},{"brand_id":45,"brand_name":"广汽吉奥","initial":"G"},{"brand_id":46,"brand_name":"观致","initial":"G"},{"brand_id":146,"brand_name":"华普","initial":"H"},{"brand_id":52,"brand_name":"华泰","initial":"H"},{"brand_id":147,"brand_name":"华翔富奇","initial":"H"},{"brand_id":160,"brand_name":"华颂","initial":"H"},{"brand_id":50,"brand_name":"哈弗","initial":"H"},{"brand_id":56,"brand_name":"哈飞","initial":"H"},{"brand_id":58,"brand_name":"恒天汽车","initial":"H"},{"brand_id":145,"brand_name":"悍马","initial":"H"},{"brand_id":59,"brand_name":"汇众","initial":"H"},{"brand_id":57,"brand_name":"海格","initial":"H"},{"brand_id":51,"brand_name":"海马","initial":"H"},{"brand_id":54,"brand_name":"海马商用车","initial":"H"},{"brand_id":53,"brand_name":"红旗","initial":"H"},{"brand_id":55,"brand_name":"黄海","initial":"H"},{"brand_id":61,"brand_name":"Jeep","initial":"J"},{"brand_id":70,"brand_name":"九龙","initial":"J"},{"brand_id":143,"brand_name":"吉利","initial":"J"},{"brand_id":62,"brand_name":"吉利全球鹰","initial":"J"},{"brand_id":63,"brand_name":"吉利帝豪","initial":"J"},{"brand_id":65,"brand_name":"吉利英伦","initial":"J"},{"brand_id":64,"brand_name":"捷豹","initial":"J"},{"brand_id":68,"brand_name":"江南","initial":"J"},{"brand_id":60,"brand_name":"江淮","initial":"J"},{"brand_id":66,"brand_name":"江铃","initial":"J"},{"brand_id":71,"brand_name":"金旅客车","initial":"J"},{"brand_id":67,"brand_name":"金杯","initial":"J"},{"brand_id":69,"brand_name":"金龙联合","initial":"J"},{"brand_id":74,"brand_name":"克莱斯勒","initial":"K"},{"brand_id":157,"brand_name":"凯翼","initial":"K"},{"brand_id":73,"brand_name":"凯迪拉克","initial":"K"},{"brand_id":158,"brand_name":"卡威","initial":"K"},{"brand_id":77,"brand_name":"卡尔森","initial":"K"},{"brand_id":75,"brand_name":"开瑞","initial":"K"},{"brand_id":76,"brand_name":"科尼塞克","initial":"K"},{"brand_id":82,"brand_name":"兰博基尼","initial":"L"},{"brand_id":81,"brand_name":"力帆","initial":"L"},{"brand_id":86,"brand_name":"劳斯莱斯","initial":"L"},{"brand_id":87,"brand_name":"林肯","initial":"L"},{"brand_id":85,"brand_name":"猎豹汽车","initial":"L"},{"brand_id":89,"brand_name":"理念","initial":"L"},{"brand_id":88,"brand_name":"莲花","initial":"L"},{"brand_id":90,"brand_name":"路特斯","initial":"L"},{"brand_id":79,"brand_name":"路虎","initial":"L"},{"brand_id":78,"brand_name":"铃木","initial":"L"},{"brand_id":83,"brand_name":"陆风","initial":"L"},{"brand_id":80,"brand_name":"雷克萨斯","initial":"L"},{"brand_id":84,"brand_name":"雷诺","initial":"L"},{"brand_id":93,"brand_name":"MG","initial":"M"},{"brand_id":94,"brand_name":"MINI","initial":"M"},{"brand_id":98,"brand_name":"摩根","initial":"M"},{"brand_id":96,"brand_name":"玛莎拉蒂","initial":"M"},{"brand_id":99,"brand_name":"美亚","initial":"M"},{"brand_id":97,"brand_name":"迈凯伦","initial":"M"},{"brand_id":95,"brand_name":"迈巴赫","initial":"M"},{"brand_id":92,"brand_name":"马自达","initial":"M"},{"brand_id":100,"brand_name":"纳智捷","initial":"N"},{"brand_id":102,"brand_name":"欧宝","initial":"O"},{"brand_id":103,"brand_name":"欧朗","initial":"O"},{"brand_id":101,"brand_name":"讴歌","initial":"O"},{"brand_id":106,"brand_name":"启辰","initial":"Q"},{"brand_id":105,"brand_name":"奇瑞","initial":"Q"},{"brand_id":107,"brand_name":"庆铃","initial":"Q"},{"brand_id":104,"brand_name":"起亚","initial":"Q"},{"brand_id":108,"brand_name":"日产","initial":"R"},{"brand_id":110,"brand_name":"瑞麒","initial":"R"},{"brand_id":109,"brand_name":"荣威","initial":"R"},{"brand_id":116,"brand_name":"Smart","initial":"S"},{"brand_id":111,"brand_name":"三菱","initial":"S"},{"brand_id":118,"brand_name":"世爵","initial":"S"},{"brand_id":117,"brand_name":"双环","initial":"S"},{"brand_id":114,"brand_name":"双龙","initial":"S"},{"brand_id":113,"brand_name":"斯巴鲁","initial":"S"},{"brand_id":112,"brand_name":"斯柯达","initial":"S"},{"brand_id":149,"brand_name":"萨博","initial":"S"},{"brand_id":150,"brand_name":"天马","initial":"T"},{"brand_id":121,"brand_name":"五菱","initial":"W"},{"brand_id":124,"brand_name":"威兹曼","initial":"W"},{"brand_id":123,"brand_name":"威麟","initial":"W"},{"brand_id":122,"brand_name":"沃尔沃","initial":"W"},{"brand_id":163,"brand_name":"五十铃","initial":"W"},{"brand_id":155,"brand_name":"夏利","initial":"X"},{"brand_id":130,"brand_name":"新凯","initial":"X"},{"brand_id":151,"brand_name":"新大地","initial":"X"},{"brand_id":148,"brand_name":"新雅途","initial":"X"},{"brand_id":125,"brand_name":"现代","initial":"X"},{"brand_id":128,"brand_name":"西雅特","initial":"X"},{"brand_id":126,"brand_name":"雪佛兰","initial":"X"},{"brand_id":127,"brand_name":"雪铁龙","initial":"X"},{"brand_id":131,"brand_name":"一汽","initial":"Y"},{"brand_id":135,"brand_name":"依维柯","initial":"Y"},{"brand_id":136,"brand_name":"扬州亚星客车","initial":"Y"},{"brand_id":134,"brand_name":"永源","initial":"Y"},{"brand_id":159,"brand_name":"英致","initial":"Y"},{"brand_id":132,"brand_name":"英菲尼迪","initial":"Y"},{"brand_id":133,"brand_name":"野马汽车","initial":"Y"},{"brand_id":139,"brand_name":"中兴","initial":"Z"},{"brand_id":137,"brand_name":"中华","initial":"Z"},{"brand_id":152,"brand_name":"中客华北","initial":"Z"},{"brand_id":140,"brand_name":"中欧","initial":"Z"},{"brand_id":154,"brand_name":"中顺","initial":"Z"},{"brand_id":138,"brand_name":"众泰","initial":"Z"},{"brand_id":120,"brand_name":"TESLA","initial":"T"},{"brand_id":169,"brand_name":"思铭","initial":"S"},{"brand_id":170,"brand_name":"东风风光","initial":"D"}]
     */

    private int error_code;
    private String error_msg;
    private int status;
    /**
     * brand_id : 1
     * brand_name : 奥迪
     * initial : A
     */

    private List<BrandListBean> brand_list;

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

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BrandListBean> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<BrandListBean> brand_list) {
        this.brand_list = brand_list;
    }

    public static class ResultAnzerBean {
        private int Result;
        private String ErrorMsg;

        public int getResult() {
            return Result;
        }

        public void setResult(int Result) {
            this.Result = Result;
        }

        public String getErrorMsg() {
            return ErrorMsg;
        }

        public void setErrorMsg(String ErrorMsg) {
            this.ErrorMsg = ErrorMsg;
        }
    }

    public static class BrandListBean {
        private int brand_id;
        private String brand_name;
        private String initial;

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }
    }
}
