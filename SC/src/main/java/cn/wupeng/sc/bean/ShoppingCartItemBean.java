package cn.wupeng.sc.bean;

/**
 * Created by hasee on 2016/7/1.
 */
public class ShoppingCartItemBean {

    private int id;
    private String name;
    private double price;
    private int count;
    private String pic;
    private boolean isChecked;
    private String proid;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public ShoppingCartItemBean(int id, String name, double price, int count, String pic) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.pic = pic;
        this.proid = proid;
    }

    public ShoppingCartItemBean() {

    }

    @Override
    public String toString() {
        return "ShoppingCartItemBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", pic='" + pic + '\'' +
                '}';
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
