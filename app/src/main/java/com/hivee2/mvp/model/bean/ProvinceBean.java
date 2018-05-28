package com.hivee2.mvp.model.bean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/11.
 */
public class ProvinceBean {

    /**
     * ProvinceList : [{"ProvinceID":1,"Province":"北京","CityList":[{"CityID":1,"City":"选择全省"}]},{"ProvinceID":2,"Province":"上海","CityList":[{"CityID":2,"City":"选择全省"}]},{"ProvinceID":3,"Province":"天津","CityList":[{"CityID":3,"City":"选择全省"}]},{"ProvinceID":4,"Province":"重庆","CityList":[{"CityID":4,"City":"选择全省"}]},{"ProvinceID":5,"Province":"安徽","CityList":[{"CityID":5,"City":"选择全省"},{"CityID":6,"City":"合肥"},{"CityID":7,"City":"安庆"},{"CityID":8,"City":"蚌埠"},{"CityID":9,"City":"亳州"},{"CityID":10,"City":"巢湖"},{"CityID":11,"City":"池州"},{"CityID":12,"City":"滁州"},{"CityID":13,"City":"阜阳"},{"CityID":14,"City":"淮北"},{"CityID":15,"City":"淮南"},{"CityID":16,"City":"黄山"},{"CityID":17,"City":"六安"},{"CityID":18,"City":"马鞍山"},{"CityID":19,"City":"宿州"},{"CityID":20,"City":"铜陵"},{"CityID":21,"City":"芜湖"},{"CityID":22,"City":"宣城"}]},{"ProvinceID":23,"Province":"福建","CityList":[{"CityID":23,"City":"选择全省"},{"CityID":24,"City":"福州"},{"CityID":25,"City":"龙岩"},{"CityID":26,"City":"南平"},{"CityID":27,"City":"宁德"},{"CityID":28,"City":"莆田"},{"CityID":29,"City":"泉州"},{"CityID":30,"City":"三明"},{"CityID":31,"City":"厦门"},{"CityID":32,"City":"漳州"}]},{"ProvinceID":33,"Province":"甘肃","CityList":[{"CityID":33,"City":"选择全省"},{"CityID":34,"City":"兰州"},{"CityID":35,"City":"白银"},{"CityID":36,"City":"定西"},{"CityID":37,"City":"甘南州"},{"CityID":38,"City":"嘉峪关"},{"CityID":39,"City":"金昌"},{"CityID":40,"City":"酒泉"},{"CityID":41,"City":"临夏州"},{"CityID":42,"City":"陇南"},{"CityID":43,"City":"平凉"},{"CityID":44,"City":"庆阳"},{"CityID":45,"City":"天水"},{"CityID":46,"City":"武威"},{"CityID":47,"City":"张掖"}]},{"ProvinceID":48,"Province":"广东","CityList":[{"CityID":48,"City":"选择全省"},{"CityID":49,"City":"广州"},{"CityID":50,"City":"潮州"},{"CityID":51,"City":"东莞"},{"CityID":52,"City":"佛山"},{"CityID":53,"City":"河源"},{"CityID":54,"City":"惠州"},{"CityID":55,"City":"江门"},{"CityID":56,"City":"揭阳"},{"CityID":57,"City":"茂名"},{"CityID":58,"City":"梅州"},{"CityID":59,"City":"清远"},{"CityID":60,"City":"汕头"},{"CityID":61,"City":"汕尾"},{"CityID":62,"City":"韶关"},{"CityID":63,"City":"深圳"},{"CityID":64,"City":"阳江"},{"CityID":65,"City":"云浮"},{"CityID":66,"City":"湛江"},{"CityID":67,"City":"肇庆"},{"CityID":68,"City":"中山"},{"CityID":69,"City":"珠海"}]},{"ProvinceID":70,"Province":"广西","CityList":[{"CityID":70,"City":"选择全省"},{"CityID":71,"City":"南宁"},{"CityID":72,"City":"百色"},{"CityID":73,"City":"北海"},{"CityID":74,"City":"崇左"},{"CityID":75,"City":"防城港"},{"CityID":76,"City":"桂林"},{"CityID":77,"City":"贵港"},{"CityID":78,"City":"河池"},{"CityID":79,"City":"贺州"},{"CityID":80,"City":"来宾"},{"CityID":81,"City":"柳州"},{"CityID":82,"City":"钦州"},{"CityID":83,"City":"梧州"},{"CityID":84,"City":"玉林"}]},{"ProvinceID":85,"Province":"贵州","CityList":[{"CityID":85,"City":"选择全省"},{"CityID":86,"City":"贵阳"},{"CityID":87,"City":"安顺"},{"CityID":88,"City":"毕节地区"},{"CityID":89,"City":"六盘水"},{"CityID":90,"City":"铜仁地区"},{"CityID":91,"City":"遵义"},{"CityID":92,"City":"黔西南州"},{"CityID":93,"City":"黔东南州"},{"CityID":94,"City":"黔南州"}]},{"ProvinceID":95,"Province":"海南","CityList":[{"CityID":95,"City":"选择全省"},{"CityID":96,"City":"海口"},{"CityID":97,"City":"白沙"},{"CityID":98,"City":"保亭"},{"CityID":99,"City":"昌江"},{"CityID":100,"City":"儋州"},{"CityID":101,"City":"澄迈"},{"CityID":102,"City":"东方"},{"CityID":103,"City":"定安"},{"CityID":104,"City":"琼海"},{"CityID":105,"City":"琼中"},{"CityID":106,"City":"乐东"},{"CityID":107,"City":"临高"},{"CityID":108,"City":"陵水"},{"CityID":109,"City":"三亚"},{"CityID":110,"City":"屯昌"},{"CityID":111,"City":"万宁"},{"CityID":112,"City":"文昌"},{"CityID":113,"City":"五指山"}]},{"ProvinceID":114,"Province":"河北","CityList":[{"CityID":114,"City":"选择全省"},{"CityID":115,"City":"石家庄"},{"CityID":116,"City":"保定"},{"CityID":117,"City":"沧州"},{"CityID":118,"City":"承德"},{"CityID":119,"City":"邯郸"},{"CityID":120,"City":"衡水"},{"CityID":121,"City":"廊坊"},{"CityID":122,"City":"秦皇岛"},{"CityID":123,"City":"唐山"},{"CityID":124,"City":"邢台"},{"CityID":125,"City":"张家口"}]},{"ProvinceID":126,"Province":"河南","CityList":[{"CityID":126,"City":"选择全省"},{"CityID":127,"City":"郑州"},{"CityID":128,"City":"安阳"},{"CityID":129,"City":"鹤壁"},{"CityID":130,"City":"焦作"},{"CityID":131,"City":"开封"},{"CityID":132,"City":"洛阳"},{"CityID":133,"City":"漯河"},{"CityID":134,"City":"南阳"},{"CityID":135,"City":"平顶山"},{"CityID":136,"City":"濮阳"},{"CityID":137,"City":"三门峡"},{"CityID":138,"City":"商丘"},{"CityID":139,"City":"新乡"},{"CityID":140,"City":"信阳"},{"CityID":141,"City":"许昌"},{"CityID":142,"City":"周口"},{"CityID":143,"City":"驻马店"}]},{"ProvinceID":144,"Province":"黑龙江","CityList":[{"CityID":144,"City":"选择全省"},{"CityID":145,"City":"哈尔滨"},{"CityID":146,"City":"大庆"},{"CityID":147,"City":"大兴安岭地区"},{"CityID":148,"City":"鹤岗"},{"CityID":149,"City":"黑河"},{"CityID":150,"City":"鸡西"},{"CityID":151,"City":"佳木斯"},{"CityID":152,"City":"牡丹江"},{"CityID":153,"City":"七台河"},{"CityID":154,"City":"齐齐哈尔"},{"CityID":155,"City":"双鸭山"},{"CityID":156,"City":"绥化"},{"CityID":157,"City":"伊春"}]},{"ProvinceID":158,"Province":"湖北","CityList":[{"CityID":158,"City":"选择全省"},{"CityID":159,"City":"武汉"},{"CityID":160,"City":"鄂州"},{"CityID":161,"City":"恩施"},{"CityID":162,"City":"黄冈"},{"CityID":163,"City":"黄石"},{"CityID":164,"City":"荆门"},{"CityID":165,"City":"荆州"},{"CityID":166,"City":"潜江"},{"CityID":167,"City":"神农架林区"},{"CityID":168,"City":"十堰"},{"CityID":169,"City":"随州"},{"CityID":170,"City":"天门"},{"CityID":171,"City":"仙桃"},{"CityID":172,"City":"咸宁"},{"CityID":173,"City":"襄樊"},{"CityID":174,"City":"孝感"},{"CityID":175,"City":"宜昌"}]},{"ProvinceID":176,"Province":"湖南","CityList":[{"CityID":176,"City":"选择全省"},{"CityID":177,"City":"长沙"},{"CityID":178,"City":"常德"},{"CityID":179,"City":"郴州"},{"CityID":180,"City":"衡阳"},{"CityID":181,"City":"怀化"},{"CityID":182,"City":"娄底"},{"CityID":183,"City":"邵阳"},{"CityID":184,"City":"湘潭"},{"CityID":185,"City":"湘西州"},{"CityID":186,"City":"益阳"},{"CityID":187,"City":"永州"},{"CityID":188,"City":"岳阳"},{"CityID":189,"City":"张家界"},{"CityID":190,"City":"株洲"}]},{"ProvinceID":191,"Province":"江苏","CityList":[{"CityID":191,"City":"选择全省"},{"CityID":192,"City":"南京"},{"CityID":193,"City":"常州"},{"CityID":194,"City":"淮安"},{"CityID":195,"City":"连云港"},{"CityID":196,"City":"南通"},{"CityID":197,"City":"苏州"},{"CityID":198,"City":"宿迁"},{"CityID":199,"City":"泰州"},{"CityID":200,"City":"无锡"},{"CityID":201,"City":"徐州"},{"CityID":202,"City":"盐城"},{"CityID":203,"City":"扬州"},{"CityID":204,"City":"镇江"}]},{"ProvinceID":205,"Province":"江西","CityList":[{"CityID":205,"City":"选择全省"},{"CityID":206,"City":"南昌"},{"CityID":207,"City":"抚州"},{"CityID":208,"City":"赣州"},{"CityID":209,"City":"吉安"},{"CityID":210,"City":"景德镇"},{"CityID":211,"City":"九江"},{"CityID":212,"City":"萍乡"},{"CityID":213,"City":"上饶"},{"CityID":214,"City":"新余"},{"CityID":215,"City":"宜春"},{"CityID":216,"City":"鹰潭"}]},{"ProvinceID":217,"Province":"吉林","CityList":[{"CityID":217,"City":"选择全省"},{"CityID":218,"City":"长春"},{"CityID":219,"City":"白城"},{"CityID":220,"City":"白山"},{"CityID":221,"City":"吉林市"},{"CityID":222,"City":"辽源"},{"CityID":223,"City":"四平"},{"CityID":224,"City":"松原"},{"CityID":225,"City":"通化"},{"CityID":226,"City":"延边"}]},{"ProvinceID":227,"Province":"辽宁","CityList":[{"CityID":227,"City":"选择全省"},{"CityID":228,"City":"沈阳"},{"CityID":229,"City":"鞍山"},{"CityID":230,"City":"本溪"},{"CityID":231,"City":"朝阳"},{"CityID":232,"City":"大连"},{"CityID":233,"City":"丹东"},{"CityID":234,"City":"抚顺"},{"CityID":235,"City":"阜新"},{"CityID":236,"City":"葫芦岛"},{"CityID":237,"City":"锦州"},{"CityID":238,"City":"辽阳"},{"CityID":239,"City":"盘锦"},{"CityID":240,"City":"铁岭"},{"CityID":241,"City":"营口"}]},{"ProvinceID":242,"Province":"内蒙古","CityList":[{"CityID":242,"City":"选择全省"},{"CityID":243,"City":"呼和浩特"},{"CityID":244,"City":"阿拉善盟"},{"CityID":245,"City":"包头"},{"CityID":246,"City":"巴彦淖尔"},{"CityID":247,"City":"赤峰"},{"CityID":248,"City":"鄂尔多斯"},{"CityID":249,"City":"呼伦贝尔"},{"CityID":250,"City":"通辽"},{"CityID":251,"City":"乌海"},{"CityID":252,"City":"乌兰察布"},{"CityID":253,"City":"锡林郭勒盟"},{"CityID":254,"City":"兴安盟"}]},{"ProvinceID":255,"Province":"宁夏","CityList":[{"CityID":255,"City":"选择全省"},{"CityID":256,"City":"银川"},{"CityID":257,"City":"固原"},{"CityID":258,"City":"石嘴山"},{"CityID":259,"City":"吴忠"},{"CityID":260,"City":"中卫"}]},{"ProvinceID":261,"Province":"青海","CityList":[{"CityID":261,"City":"选择全省"},{"CityID":262,"City":"西宁"},{"CityID":263,"City":"果洛州"},{"CityID":264,"City":"海东地区"},{"CityID":265,"City":"海北州"},{"CityID":266,"City":"海南州"},{"CityID":267,"City":"海西州"},{"CityID":268,"City":"黄南州"},{"CityID":269,"City":"玉树州"}]},{"ProvinceID":270,"Province":"山东","CityList":[{"CityID":270,"City":"选择全省"},{"CityID":271,"City":"济南"},{"CityID":272,"City":"滨州"},{"CityID":273,"City":"东营"},{"CityID":274,"City":"德州"},{"CityID":275,"City":"菏泽"},{"CityID":276,"City":"济宁"},{"CityID":277,"City":"莱芜"},{"CityID":278,"City":"聊城"},{"CityID":279,"City":"临沂"},{"CityID":280,"City":"青岛"},{"CityID":281,"City":"日照"},{"CityID":282,"City":"泰安"},{"CityID":283,"City":"威海"},{"CityID":284,"City":"潍坊"},{"CityID":285,"City":"烟台"},{"CityID":286,"City":"枣庄"},{"CityID":287,"City":"淄博"}]},{"ProvinceID":288,"Province":"山西","CityList":[{"CityID":288,"City":"选择全省"},{"CityID":289,"City":"太原"},{"CityID":290,"City":"长治"},{"CityID":291,"City":"大同"},{"CityID":292,"City":"晋城"},{"CityID":293,"City":"晋中"},{"CityID":294,"City":"临汾"},{"CityID":295,"City":"吕梁"},{"CityID":296,"City":"朔州"},{"CityID":297,"City":"忻州"},{"CityID":298,"City":"阳泉"},{"CityID":299,"City":"运城"}]},{"ProvinceID":300,"Province":"陕西","CityList":[{"CityID":300,"City":"选择全省"},{"CityID":301,"City":"西安"},{"CityID":302,"City":"安康"},{"CityID":303,"City":"宝鸡"},{"CityID":304,"City":"汉中"},{"CityID":305,"City":"商洛"},{"CityID":306,"City":"铜川"},{"CityID":307,"City":"渭南"},{"CityID":308,"City":"咸阳"},{"CityID":309,"City":"延安"},{"CityID":310,"City":"榆林"}]},{"ProvinceID":311,"Province":"四川","CityList":[{"CityID":311,"City":"选择全省"},{"CityID":312,"City":"成都"},{"CityID":313,"City":"阿坝州"},{"CityID":314,"City":"巴中"},{"CityID":315,"City":"达州"},{"CityID":316,"City":"德阳"},{"CityID":317,"City":"甘孜州"},{"CityID":318,"City":"广安"},{"CityID":319,"City":"广元"},{"CityID":320,"City":"乐山"},{"CityID":321,"City":"凉山州"},{"CityID":322,"City":"泸州"},{"CityID":323,"City":"南充"},{"CityID":324,"City":"眉山"},{"CityID":325,"City":"绵阳"},{"CityID":326,"City":"内江"},{"CityID":327,"City":"攀枝花"},{"CityID":328,"City":"遂宁"},{"CityID":329,"City":"雅安"},{"CityID":330,"City":"宜宾"},{"CityID":331,"City":"资阳"},{"CityID":332,"City":"自贡"}]},{"ProvinceID":333,"Province":"西藏","CityList":[{"CityID":333,"City":"选择全省"},{"CityID":334,"City":"拉萨"},{"CityID":335,"City":"阿里地区"},{"CityID":336,"City":"昌都地区"},{"CityID":337,"City":"林芝地区"},{"CityID":338,"City":"那曲地区"},{"CityID":339,"City":"日喀则地区"},{"CityID":340,"City":"山南地区"}]},{"ProvinceID":341,"Province":"新疆","CityList":[{"CityID":341,"City":"选择全省"},{"CityID":342,"City":"乌鲁木齐"},{"CityID":343,"City":"阿拉尔"},{"CityID":344,"City":"阿克苏地区"},{"CityID":345,"City":"阿勒泰地区"},{"CityID":346,"City":"巴音郭楞"},{"CityID":347,"City":"博尔塔拉州"},{"CityID":348,"City":"昌吉州"},{"CityID":349,"City":"哈密地区"},{"CityID":350,"City":"和田地区"},{"CityID":351,"City":"喀什地区"},{"CityID":352,"City":"克拉玛依"},{"CityID":353,"City":"克孜勒苏州"},{"CityID":354,"City":"石河子"},{"CityID":355,"City":"塔城地区"},{"CityID":356,"City":"图木舒克"},{"CityID":357,"City":"吐鲁番地区"},{"CityID":358,"City":"五家渠"},{"CityID":359,"City":"伊犁州"}]},{"ProvinceID":360,"Province":"云南","CityList":[{"CityID":360,"City":"选择全省"},{"CityID":361,"City":"昆明"},{"CityID":362,"City":"保山"},{"CityID":363,"City":"楚雄州"},{"CityID":364,"City":"大理州"},{"CityID":365,"City":"德宏州"},{"CityID":366,"City":"迪庆州"},{"CityID":367,"City":"红河州"},{"CityID":368,"City":"丽江"},{"CityID":369,"City":"临沧"},{"CityID":370,"City":"怒江州"},{"CityID":371,"City":"普洱"},{"CityID":372,"City":"曲靖"},{"CityID":373,"City":"昭通"},{"CityID":374,"City":"文山"},{"CityID":375,"City":"西双版纳"},{"CityID":376,"City":"玉溪"}]},{"ProvinceID":377,"Province":"浙江","CityList":[{"CityID":377,"City":"选择全省"},{"CityID":378,"City":"杭州"},{"CityID":379,"City":"湖州"},{"CityID":380,"City":"嘉兴"},{"CityID":381,"City":"金华"},{"CityID":382,"City":"丽水"},{"CityID":383,"City":"宁波"},{"CityID":384,"City":"衢州"},{"CityID":385,"City":"绍兴"},{"CityID":386,"City":"台州"},{"CityID":387,"City":"温州"},{"CityID":388,"City":"舟山"}]},{"ProvinceID":389,"Province":"香港","CityList":[{"CityID":389,"City":"选择全省"}]},{"ProvinceID":390,"Province":"澳门","CityList":[{"CityID":390,"City":"选择全省"}]},{"ProvinceID":391,"Province":"台湾","CityList":[{"CityID":391,"City":"选择全省"}]}]
     * Result : 0
     * ErrorMsg : null
     */

    private int Result;
    private Object ErrorMsg;
    /**
     * ProvinceID : 1
     * Province : 北京
     * CityList : [{"CityID":1,"City":"选择全省"}]
     */

    private List<ProvinceListBean> ProvinceList;

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

    public List<ProvinceListBean> getProvinceList() {
        return ProvinceList;
    }

    public void setProvinceList(List<ProvinceListBean> ProvinceList) {
        this.ProvinceList = ProvinceList;
    }

    public static class ProvinceListBean {
        private int ProvinceID;
        private String Province;
        /**
         * CityID : 1
         * City : 选择全省
         */

        private List<CityListBean> CityList;

        public int getProvinceID() {
            return ProvinceID;
        }

        public void setProvinceID(int ProvinceID) {
            this.ProvinceID = ProvinceID;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public List<CityListBean> getCityList() {
            return CityList;
        }

        public void setCityList(List<CityListBean> CityList) {
            this.CityList = CityList;
        }

        public static class CityListBean {
            private int CityID;
            private String City;

            public int getCityID() {
                return CityID;
            }

            public void setCityID(int CityID) {
                this.CityID = CityID;
            }

            public String getCity() {
                return City;
            }

            public void setCity(String City) {
                this.City = City;
            }
        }
    }
}
