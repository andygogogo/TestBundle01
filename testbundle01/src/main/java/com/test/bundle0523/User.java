package com.test.bundle0523;

/**
 * Created by andy on 2018/5/11.
 */

public class User {
    private String lgname;
    private String lgpassword;

    public String getLgname() {
        return lgname == null ? "" : lgname;
    }

    public void setLgname(String lgname) {
        this.lgname = lgname;
    }

    public String getLgpassword() {
        return lgpassword == null ? "" : lgpassword;
    }

    public void setLgpassword(String lgpassword) {
        this.lgpassword = lgpassword;
    }
}
