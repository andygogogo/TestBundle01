package com.test.bundle0523;

/**
 * Created by andy on 2018/5/11.
 */

public class LoginResponse {
    //    "position":"职位ps：中级开发工程师",
//            "phone":"手机 ps：13211551452",
//            "error":"错误信息 0成功，1失败ps：0",
//            "FixedPhone":"固定电话 ps：021-6543812",
//            "email":"邮箱 ps：123456@qq.com",
//            "errorCode":"错误码 没有即为null",
//            "trumpet":"短号ps：3352",
//            "branch":"部门 ps：研发部门",
//            "role":"角色 ps:工程师",
//            "errorMsg":"错误信息没有即为null",
//            "lgname":"用户名 ps：xiaoming",
//            "chName":"中文名 ps：小明"

    public Body body;
    public Header header;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public static class Body {
        public String position;
        public String phone;
        public String error;
        public String FixedPhone;
        public String email;
        public String errorCode;
        public String trumpet;
        public String branch;
        public String role;
        public String errorMsg;
        public String lgname;
        public String chName;

        public String getPosition() {
            return position == null ? "" : position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getError() {
            return error == null ? "" : error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getFixedPhone() {
            return FixedPhone == null ? "" : FixedPhone;
        }

        public void setFixedPhone(String fixedPhone) {
            FixedPhone = fixedPhone;
        }

        public String getEmail() {
            return email == null ? "" : email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getErrorCode() {
            return errorCode == null ? "" : errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getTrumpet() {
            return trumpet == null ? "" : trumpet;
        }

        public void setTrumpet(String trumpet) {
            this.trumpet = trumpet;
        }

        public String getBranch() {
            return branch == null ? "" : branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getRole() {
            return role == null ? "" : role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getErrorMsg() {
            return errorMsg == null ? "" : errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getLgname() {
            return lgname == null ? "" : lgname;
        }

        public void setLgname(String lgname) {
            this.lgname = lgname;
        }

        public String getChName() {
            return chName == null ? "" : chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }
    }

    public static class Header {
    }
}
