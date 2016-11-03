package cn.wupeng.sc.bean;

/**
 * Created by heshun on 2016/6/30.
 */
public class ProductListBean {
    private int id;
    private int marketPrice;
    private String name;
    private String pic;
    private int price;
    private int leftTime;
    private int limitPrice;
    private int commontCount;

    public int getCommontCount() {
        return commontCount;
    }

    public void setCommontCount(int commontCount) {
        this.commontCount = commontCount;
    }

    public int getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(int limitPrice) {
        this.limitPrice = limitPrice;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
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
}
