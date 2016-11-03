package cn.wupeng.sc.bean;

import java.util.List;

/**
 * Created by Android on 2016/7/1.
 */
public class GoodsCommentBean {


    /**
     * comment : [{"content":"裙子不错裙子不错裙子不错裙子不错裙子不错裙子不错裙子","time":"2014-12-01 23:00:00","title":"裙子","username":"杨柳"},{"content":"裙子不错裙子不错裙子不错裙子不错裙子不错裙子不错裙子","time":"2014-11-21 22:00:00","title":"裙子","username":"赵刚"}]
     * response : productComment
     */

    private String response;
    /**
     * content : 裙子不错裙子不错裙子不错裙子不错裙子不错裙子不错裙子
     * time : 2014-12-01 23:00:00
     * title : 裙子
     * username : 杨柳
     */

    private List<CommentBean> comment;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        private String content;
        private String time;
        private String title;
        private String username;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
