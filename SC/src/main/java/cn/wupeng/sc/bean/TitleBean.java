package cn.wupeng.sc.bean;

import java.util.List;

/**
 * Created by Administrator on 2016-7-1.
 */
public class TitleBean {


    /**
     * response : title
     * title : [{"title":"单笔订单满49元全国包邮哦","type":"吃货头条"},{"title":"挥泪大甩卖，跳楼价，走过路过都来瞧一瞧","type":"促销"},{"title":"年终大促即将上演，各路豪杰摩拳擦掌，请大家拭目以待","type":"通告"}]
     */

    private String response;
    /**
     * title : 单笔订单满49元全国包邮哦
     * type : 吃货头条
     */

    private List<TitlesBean> title;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<TitlesBean> getTitle() {
        return title;
    }

    public void setTitle(List<TitlesBean> title) {
        this.title = title;
    }

    public static class TitlesBean {
        private String title;
        private String type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
