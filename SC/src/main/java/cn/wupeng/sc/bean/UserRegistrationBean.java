package cn.wupeng.sc.bean;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class UserRegistrationBean {

    /**
     * response : register
     * userInfo : {"userid":"1195"}
     */

    private String response;
    /**
     * userid : 1195
     */

    private UserInfoBean userInfo;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
