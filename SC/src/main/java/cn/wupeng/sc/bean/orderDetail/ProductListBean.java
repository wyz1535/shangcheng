package cn.wupeng.sc.bean.orderDetail;

/**
 * Created by admin on 2016/7/5.
 */
public class ProductListBean {
    private int prodNum;
    /**
     * id : 3
     * name : 女裙
     * pic : /images/product/detail/c1.jpg
     * price : 300
     * productProperty : [{"id":0,"k":"颜色","v":"红色"},{"id":1,"k":"尺码","v":"M"}]
     */

    private ProductBean product;

    public int getProdNum() {
        return prodNum;
    }

    public void setProdNum(int prodNum) {
        this.prodNum = prodNum;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

}
