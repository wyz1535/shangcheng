package cn.wupeng.sc.bean.orderDetail;

import java.util.List;

/**
 * Created by admin on 2016/7/1.
 */
public class OrderDetail {

    /**
     * addressArea : 密云县
     * addressDetail : 街道口地铁c口
     * id : 139
     * name : itcast
     */

    private AddressInfoBean addressInfo;
    /**
     * freight : 10
     * totalCount : 5
     * totalPoint : 30
     * totalPrice : 1250
     */

    private CheckoutAddupBean checkoutAddup;
    /**
     * type : 1
     */

    private DeliveryInfoBean deliveryInfo;
    /**
     * invoiceContent : 传智慧播客教育科技有限公司
     * invoiceTitle : 传智慧播客教育科技有限公司
     */

    private InvoiceInfoBean invoiceInfo;
    /**
     * flag : 1
     * orderId : 334310
     * status : 未处理
     * time : 1467359334331
     */

    private OrderInfoBean orderInfo;
    /**
     * type : 1
     */

    private PaymentInfoBean paymentInfo;
    /**
     * addressInfo : {"addressArea":"密云县","addressDetail":"街道口地铁c口","id":139,"name":"itcast"}
     * checkoutAddup : {"freight":10,"totalCount":5,"totalPoint":30,"totalPrice":1250}
     * deliveryInfo : {"type":"1"}
     * invoiceInfo : {"invoiceContent":"传智慧播客教育科技有限公司","invoiceTitle":"传智慧播客教育科技有限公司"}
     * orderInfo : {"flag":1,"orderId":"334310","status":"未处理","time":"1467359334331"}
     * paymentInfo : {"type":1}
     * productList : [{"prodNum":3,"product":{"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":0,"k":"颜色","v":"红色"},{"id":1,"k":"颜色","v":"绿色"}]}},{"prodNum":2,"product":{"id":2,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100,"productProperty":[{"id":0,"k":"颜色","v":"绿色"},{"id":1,"k":"尺码","v":"M"}]}}]
     * prom : ["促销信息一","促销信息二"]
     * response : orderDetail
     */

    private String response;
    /**
     * prodNum : 3
     * product : {"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":0,"k":"颜色","v":"红色"},{"id":1,"k":"颜色","v":"绿色"}]}
     */

    private List<ProductListBean> productList;
    private List<String> prom;

    public AddressInfoBean getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfoBean addressInfo) {
        this.addressInfo = addressInfo;
    }

    public CheckoutAddupBean getCheckoutAddup() {
        return checkoutAddup;
    }

    public void setCheckoutAddup(CheckoutAddupBean checkoutAddup) {
        this.checkoutAddup = checkoutAddup;
    }

    public DeliveryInfoBean getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfoBean deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public InvoiceInfoBean getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(InvoiceInfoBean invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public PaymentInfoBean getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfoBean paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public List<String> getProm() {
        return prom;
    }

    public void setProm(List<String> prom) {
        this.prom = prom;
    }

}
