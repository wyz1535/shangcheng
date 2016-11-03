package cn.wupeng.sc.bean.orderDetail;

import java.util.List;

/**
 * Created by admin on 2016/7/2.
 */
public class ProductBean {
    private int id;
    private String name;
    private String pic;
    private int price;
    /**
     * id : 0
     * k : 颜色
     * v : 红色
     */

    private List<ProductPropertyBean> productProperty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<ProductPropertyBean> getProductProperty() {
        return productProperty;
    }

    public void setProductProperty(List<ProductPropertyBean> productProperty) {
        this.productProperty = productProperty;
    }

}
