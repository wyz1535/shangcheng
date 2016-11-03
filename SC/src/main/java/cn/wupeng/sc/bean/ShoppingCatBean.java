package cn.wupeng.sc.bean;

/**
 * Created by Android on 2016/7/1.
 *
 * 详情页面数据传到购物车的JavaBean
 *
 */
public class ShoppingCatBean {

    private  int price;
    private  String ivUrl;
    private  String name;
    private  String count;
    private  int id;

    public ShoppingCatBean(int id, int price, String ivUrl, String name, String count) {
        this.id=id;
        this.price=price;
        this.ivUrl=ivUrl;
        this.name=name;
        this.count=count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCount() {

        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIvUrl() {
        return ivUrl;
    }

    public void setIvUrl(String ivUrl) {
        this.ivUrl = ivUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
