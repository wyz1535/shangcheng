package cn.wupeng.sc.bean.accountBean;

/**
 * Created by Administrator on 2016/7/2.
 */
public class ProductListAcBean {
    private int prodNum;
    /**
     * id : 1
     * name : 韩版时尚蕾丝裙
     * pic : /images/product/detail/c3.jpg
     * price : 350
     * productProperty : [{"id":1,"k":"颜色","v":"红色"},{"id":3,"k":"尺码","v":"M"}]
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
