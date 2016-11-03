package cn.wupeng.sc.bean.toSubmitbean;

/**
 * Created by Administrator on 2016/7/3.
 */
public class Submitbean {

    /**
     * orderId : 915178
     * paymentType : 1
     * price : 450
     */

    private OrderInfoBean orderInfo;
    /**
     * orderInfo : {"orderId":"915178","paymentType":1,"price":450}
     * response : orderSubmit
     */

    private String response;

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
