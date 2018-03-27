package com.sky.beautiful.OKHttp;

import org.json.JSONArray;

import java.util.Map;

/**
 * @Time : 2017/12/22 no 下午7:05
 * @USER : vvguoliang
 * @File : AggregateMapIntersion.java
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

public interface AggregateMapIntersion {

    /**
     * 注册
     *
     * @param phone
     * @param passWord
     * @param identifyCode
     * @return
     */
    Map<String, Object> setRegister(String phone, String passWord, String identifyCode);

    /**
     * 用户修改密码接口
     *
     * @param phone
     * @param newPassWord
     * @param passWord
     * @param identifyCode
     * @param type
     * @return
     */
    Map<String, Object> setModifyPassword(String phone, String newPassWord, String passWord, String identifyCode, int type);

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    Map<String, Object> setLogin(String phone, String password);

    /**
     * 退出登录
     *
     * @param userNo
     * @return
     */
    Map<String, Object> setExitLogin(String userNo);

    /**
     * 用户个人信息录入
     *
     * @param name
     * @param sex
     * @param age
     * @param userNo
     * @return
     */
    Map<String, Object> setUserMsgInput(String name, String sex, Long age, String userNo);

    /**
     * 用户头像上传
     *
     * @param userNo
     * @param headUrl
     * @return
     */
    Map<String, Object> setUploadUserHead(String userNo, String headUrl);

    /**
     * 收藏、取消收藏
     *
     * @param userNo
     * @param collectType
     * @param imgID
     * @param isClose
     * @return
     */
    Map<String, Object> setIsCollect(String userNo, int collectType, String imgID, int isClose, int type, JSONArray imgIDAry);

    /**
     * 我的收藏
     *
     * @param userNo
     * @param page
     * @return
     */
    Map<String, Object> setMyCollect(String userNo, int page, int collectType);

    /**
     * 点赞、取消点赞
     *
     * @param userNo
     * @param imgID
     * @param islike
     * @return
     */
    Map<String, Object> setIsLike(String userNo, String imgID, int islike);

    /**
     * 首页照片瀑布流、获取私照照片瀑布流、搜索
     *
     * @param userNo
     * @param tagName
     * @param state
     * @param page
     * @return
     */
    Map<String, Object> setGetPageShow(String userNo, String tagName, int state, int page);

    /**
     * 查看图集
     *
     * @param atlasCode
     * @param page
     * @return
     */
    Map<String, Object> setGetViewAtlas(String atlasCode, int page, String userNo);

    /**
     * 查看标签
     *
     * @param userNo
     * @param tagName
     * @return
     */
    Map<String, Object> setGetLabel(String userNo, String tagName);

    /**
     * 我的页面展示信息
     *
     * @param userNo
     * @return
     */
    Map<String, Object> setMyUserMsg(String userNo);

    /**
     * 获取短信
     *
     * @param phone
     * @return
     */
    Map<String, Object> setDynamicCode(String phone);

    /**
     *  购买瓜币
     * @param userNo
     * @param atlasCode
     * @param atlasName
     * @return
     */
    Map<String, Object> setBug(String userNo , String atlasCode , String atlasName);

}
