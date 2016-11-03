package cn.wupeng.sc.bean;

import java.util.List;

/**
 * Created by admin on 2016/7/5.
 */
public class BrandListInfo {

    /**
     * brand : [{"id":1,"key":"百草味","value":[{"id":1218,"name":"爱尚吃","pic":"/images/brand/aishangchi.jpg"},{"id":1219,"name":"麦七七","pic":"/images/brand/maiqiqi.jpg"},{"id":1220,"name":"渝太太","pic":"/images/brand/yutaitai.jpg"}]},{"id":2,"key":"达利园","value":[{"id":1202,"name":"咪咪","pic":"/images/brand/b.png"}]},{"id":3,"key":"德芙","value":[{"id":1209,"name":"德芙","pic":"/images/brand/defu.jpg"},{"id":1210,"name":"好阿爸","pic":"/images/brand/haoaba.jpg"},{"id":1211,"name":"老婆大人","pic":"/images/brand/laopodaren.jpg"},{"id":1212,"name":"良品铺子","pic":"/images/brand/lppuzi.jpg"}]},{"id":4,"key":"好阿爸","value":[]},{"id":5,"key":"老婆大人","value":[{"id":1206,"name":"Nutrilon","pic":"/images/brand/a.png"},{"id":1207,"name":"百草味","pic":"/images/brand/baicaiwei.jpg"},{"id":1208,"name":"达利园","pic":"/images/brand/daliyuan.jpg"},{"id":1213,"name":"盼盼","pic":"/images/brand/panpan.jpg"},{"id":1214,"name":"恰恰","pic":"/images/brand/qaqa.jpg"},{"id":1215,"name":"宅客","pic":"/images/brand/zaike.jpg"},{"id":1216,"name":"零食多","pic":"/images/brand/lingshiduo.jpg"},{"id":1217,"name":"哇哈哈","pic":"/images/brand/wahaha.jpg"}]},{"id":6,"key":"良品铺子","value":[]},{"id":7,"key":"盼盼","value":[{"id":1201,"name":"爱家","pic":"/images/brand/1378_1333165554_8.png"}]},{"id":8,"key":"恰恰","value":[]},{"id":9,"key":"嘴馋坊","value":[{"id":1203,"name":"防辐射","pic":"/images/brand/c.png"},{"id":1205,"name":"枕工坊","pic":"/images/brand/d.png"}]}]
     * response : brand
     */

    private String response;
    /**
     * id : 1
     * key : 百草味
     * value : [{"id":1218,"name":"爱尚吃","pic":"/images/brand/aishangchi.jpg"},{"id":1219,"name":"麦七七","pic":"/images/brand/maiqiqi.jpg"},{"id":1220,"name":"渝太太","pic":"/images/brand/yutaitai.jpg"}]
     */

    private List<BrandBean> brand;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<BrandBean> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandBean> brand) {
        this.brand = brand;
    }

}
