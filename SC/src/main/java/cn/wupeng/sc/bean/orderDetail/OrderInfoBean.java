package cn.wupeng.sc.bean.orderDetail;

/**
 * Created by admin on 2016/7/1.
 */
public class OrderInfoBean {
    private int flag;
    private String orderId;
    private String status;
    private String time;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
