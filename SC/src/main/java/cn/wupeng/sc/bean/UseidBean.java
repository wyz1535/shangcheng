package cn.wupeng.sc.bean;

/**
 * Created by Administrator on 2016/7/1 0001.
 */
public class UseidBean  {

    /**
     * response : login
     * userInfo : {"userid":"141734"}
     */

    private String response;
    /**
     * userid : 141734
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
