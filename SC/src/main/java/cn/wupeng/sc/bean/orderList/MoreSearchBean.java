package cn.wupeng.sc.bean.orderList;

import java.util.List;

/**
 * Created by Administrator on 2016/7/3 0003.
 */
public class MoreSearchBean {


    private String response;
    private List<String> searchKeywords;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<String> getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(List<String> searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    @Override
    public String toString() {
        return "MoreSearchBean{" +
                "searchKeywords=" + searchKeywords +
                '}';
    }
}
