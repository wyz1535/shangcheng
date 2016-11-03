package cn.wupeng.sc.bean;

import java.util.List;

/**
 * Created by admin on 2016/7/5.
 */
public class BrandBean {
    private int id;
    private String key;
    /**
     * id : 1218
     * name : 爱尚吃
     * pic : /images/brand/aishangchi.jpg
     */

    private List<ValueBean> value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

}
