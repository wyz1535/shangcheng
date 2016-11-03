package cn.wupeng.sc.bean.orderList;

import java.util.List;

/**
 * Created by admin on 2016/7/1.
 */
public class OrderList {

    /**
     * orderList : [{"flag":3,"orderId":"534520","price":450,"status":"未处理","time":"1467363534533"},{"flag":1,"orderId":"536216","price":450,"status":"未处理","time":"1467363536230"},{"flag":3,"orderId":"536887","price":450,"status":"未处理","time":"1467363536903"}]
     * response : orderList
     */

    private String response;
    /**
     * flag : 3
     * orderId : 534520
     * price : 450
     * status : 未处理
     * time : 1467363534533
     */

    private List<OrderListBean> orderList;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

}
