package cn.wupeng.sc.bean;

import java.util.List;

/**
 * Created by heshun on 2016/7/1.
 */
public class SalesPremotion {

    /**
     * response : topic
     * topic : [{"id":1,"name":"孕妇服装大特惠","pic":"/images/topic/1.jpg"},{"id":2,"name":"儿童玩具聚划算","pic":"/images/topic/2.jpg"},{"id":3,"name":"尿不湿大酬宾","pic":"/images/topic/3.png"},{"id":4,"name":"美女","pic":"/images/topic/4.png"},{"id":5,"name":"蝴蝶","pic":"/images/topic/5.png"},{"id":6,"name":"黄昏下的城市","pic":"/images/topic/6.png"},{"id":7,"name":"激情冲浪","pic":"/images/topic/7.png"},{"id":8,"name":"润微","pic":"/images/topic/8.png"}]
     */

    private String response;
    /**
     * id : 1
     * name : 孕妇服装大特惠
     * pic : /images/topic/1.jpg
     */

    private List<SalesPremotionBean> topic;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<SalesPremotionBean> getTopic() {
        return topic;
    }

    public void setTopic(List<SalesPremotionBean> topic) {
        this.topic = topic;
    }

}
