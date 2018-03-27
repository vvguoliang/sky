package com.sky.beautiful.Model;

/**
 * @Time : 2017/12/29 no 下午4:56
 * @USER : vvguoliang
 * @File : MyUserMsg.java
 * @Software: Android Studio
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ***┏┓   ┏ ┓
 * **┏┛┻━━━┛ ┻┓
 * **┃   ☃   ┃
 * **┃ ┳┛  ┗┳ ┃
 * **┃    ┻   ┃
 * **┗━┓    ┏━┛
 * ****┃    ┗━━━┓
 * ****┃ 神兽保佑 ┣┓
 * ****┃ 永无BUG！┏┛
 * ****┗┓┓┏━┳┓┏┛┏┛
 * ******┃┫┫  ┃┫┫
 * ******┗┻┛  ┗┻┛
 */

public class MyUserMsg extends DataEntity {

    private String userNo = "";
    private String gradeNo = "";
    private String gradeUrl = "";
    private String userName = "";
    private String sex = "";
    private String age = "";
    private String headUrl = "";
    private String score = "";
    private String coupon = "";
    private String activityUrl = "";
    private String memberUrl = "";
    private String melonMoney = "";

    public MyUserMsg() {
        super();
    }

    public MyUserMsg(String userNo, String gradeNo, String gradeUrl, String userName, String sex, String age, String headUrl, String score, String coupon, String activityUrl, String memberUrl, String melonMoney) {
        super();
        this.userNo = userNo;
        this.gradeNo = gradeNo;
        this.gradeUrl = gradeUrl;
        this.userName = userName;
        this.sex = sex;
        this.age = age;
        this.headUrl = headUrl;
        this.score = score;
        this.coupon = coupon;
        this.activityUrl = activityUrl;
        this.memberUrl = memberUrl;
        this.melonMoney = melonMoney;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getGradeNo() {
        return gradeNo;
    }

    public void setGradeNo(String gradeNo) {
        this.gradeNo = gradeNo;
    }

    public String getGradeUrl() {
        return gradeUrl;
    }

    public void setGradeUrl(String gradeUrl) {
        this.gradeUrl = gradeUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getMemberUrl() {
        return memberUrl;
    }

    public void setMemberUrl(String memberUrl) {
        this.memberUrl = memberUrl;
    }

    public String getMelonMoney() {
        return melonMoney;
    }

    public void setMelonMoney(String melonMoney) {
        this.melonMoney = melonMoney;
    }
}
