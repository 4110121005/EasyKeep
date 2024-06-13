package edu.xcu.easykeep.bean;

/**
 * 用户信息实体类
 * 用于封装用户信息
 */
public class UserBean {
    /**
     * 用户ID
     */
    private String uid;
    /**
     * 用户密码
     */
    private String upassword;

    /**
     * 构造函数
     *
     * @param uid       用户ID
     * @param upassword 用户密码
     */
    public UserBean(String uid, String upassword) {
        this.uid = uid;
        this.upassword = upassword;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }
}
