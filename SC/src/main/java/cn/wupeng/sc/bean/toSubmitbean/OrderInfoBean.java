package cn.wupeng.sc.bean.toSubmitbean;

/**
 * Created by Administrator on 2016/7/3.
 */
public class OrderInfoBean {
    private String orderId;
    private int paymentType;
    private int price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
