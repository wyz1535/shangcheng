package cn.wupeng.sc.bean.accountBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
public class AccountProductBean {


    /**
     * addressArea : 洪山区
     * addressDetail : 街道口
     * city : 武汉
     * id : 184
     * isDefault : 1
     * name : 肖文
     * phoneNumber : 15801477821
     * province : 湖北
     * zipCode : 430070
     */

    private AddressInfoBean addressInfo;
    /**
     * freight : 10
     * totalCount : 8
     * totalPoint : 30
     * totalPrice : 450
     */

    private CheckoutAddupBean checkoutAddup;
    /**
     * addressInfo : {"addressArea":"洪山区","addressDetail":"街道口","city":"武汉","id":184,"isDefault":1,"name":"肖文","phoneNumber":"15801477821","province":"湖北","zipCode":"430070"}
     * checkoutAddup : {"freight":10,"totalCount":8,"totalPoint":30,"totalPrice":450}
     * checkoutProm : ["促销信息一","促销信息二"]
     * deliveryList : [{"des":"周一至周五送货","type":1},{"des":"双休日及公众假期送货","type":2},{"des":"时间不限，工作日双休日及公众假期均可送货","type":3}]
     * paymentList : [{"des":"到付-现金","type":1},{"des":"到付-POS机","type":2},{"des":"支付宝","type":1}]
     * productList : [{"prodNum":3,"product":{"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":3,"k":"尺码","v":"M"}]}},{"prodNum":5,"product":{"id":2,"name":"粉色毛衣","pic":"/images/product/detail/q1.jpg","price":100,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":4,"k":"尺码","v":"XXL"}]}}]
     * response : checkOut
     */

    private String response;
    private List<String> checkoutProm;
    /**
     * des : 周一至周五送货
     * type : 1
     */

    private List<DeliveryListBean> deliveryList;
    /**
     * des : 到付-现金
     * type : 1
     */

    private List<PaymentListBean> paymentList;
    /**
     * prodNum : 3
     * product : {"id":1,"name":"韩版时尚蕾丝裙","pic":"/images/product/detail/c3.jpg","price":350,"productProperty":[{"id":1,"k":"颜色","v":"红色"},{"id":3,"k":"尺码","v":"M"}]}
     */

    private List<ProductListAcBean> productList;

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getCheckoutProm() {
        return checkoutProm;
    }

    public void setCheckoutProm(List<String> checkoutProm) {
        this.checkoutProm = checkoutProm;
    }

    public List<DeliveryListBean> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<DeliveryListBean> deliveryList) {
        this.deliveryList = deliveryList;
    }

    public List<PaymentListBean> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<PaymentListBean> paymentList) {
        this.paymentList = paymentList;
    }

    public List<ProductListAcBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListAcBean> productList) {
        this.productList = productList;
    }

}
