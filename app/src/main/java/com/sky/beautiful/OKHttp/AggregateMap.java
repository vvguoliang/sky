package com.sky.beautiful.OKHttp;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2017/12/22 no 下午7:05
 * @USER : vvguoliang
 * @File : AggregateMap.java
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

public class AggregateMap implements AggregateMapIntersion {

    @Override
    public Map<String, Object> setRegister(String phone, String passWord, String identifyCode) {
        return getRegister(phone, passWord, identifyCode);
    }

    private Map<String, Object> getRegister(String phone, String passWord, String identifyCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("passWord", passWord);
        map.put("identifyCode", identifyCode);
        return map;
    }

    @Override
    public Map<String, Object> setModifyPassword(String phone, String newPassWord, String passWord, String identifyCode, int type) {
        return getModifyPassword(phone, newPassWord, passWord, identifyCode, type);
    }

    private Map<String, Object> getModifyPassword(String phone, String newPassWord, String passWord, String identifyCode, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("newPassWord", newPassWord);
        map.put("passWord", passWord);
        map.put("identifyCode", identifyCode);
        map.put("type", type);
        return map;
    }

    @Override
    public Map<String, Object> setLogin(String phone, String password) {
        return getLogin(phone, password);
    }

    private Map<String, Object> getLogin(String phone, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("passWord", password);
        return map;
    }

    @Override
    public Map<String, Object> setExitLogin(String userNo) {
        return getExitLogin(userNo);
    }

    private Map<String, Object> getExitLogin(String userNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        return map;
    }

    @Override
    public Map<String, Object> setUserMsgInput(String name, String sex, Long age, String userNo) {
        return getUserMsgInput(name, sex, age, userNo);
    }

    private Map<String, Object> getUserMsgInput(String name, String sex, Long age, String userNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("sex", sex);
        map.put("age", age);
        map.put("userNo", userNo);
        return map;
    }

    @Override
    public Map<String, Object> setUploadUserHead(String userNo, String headUrl) {
        return getUploadUserHead(userNo, headUrl);
    }

    private Map<String, Object> getUploadUserHead(String userNo, String headUrl) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("file", headUrl);
        return map;
    }

    @Override
    public Map<String, Object> setIsCollect(String userNo, int collectType, String imgID, int isClose, int type, JSONArray imgIDAry) {
        return getIsCollect(userNo, collectType, imgID, isClose, type, imgIDAry);
    }

    private Map<String, Object> getIsCollect(String userNo, int collectType, String imgID, int isClose, int type, JSONArray imgIDAry) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("collectType", collectType);
        map.put("imgID", imgID);
        map.put("isClose", isClose);
        map.put("type", type);
        map.put("imgIDAry", imgIDAry);
        return map;
    }

    @Override
    public Map<String, Object> setMyCollect(String userNo, int page, int collectType) {
        return getMyCollect(userNo, page, collectType);
    }

    private Map<String, Object> getMyCollect(String userNo, int page, int collectType) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("page", page);
        map.put("collectType", collectType);
        return map;
    }

    @Override
    public Map<String, Object> setIsLike(String userNo, String imgID, int islike) {
        return getIsLike(userNo, imgID, islike);
    }

    private Map<String, Object> getIsLike(String userNo, String imgID, int islike) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("imgID", imgID);
        map.put("islike", islike);
        return map;
    }

    @Override
    public Map<String, Object> setGetPageShow(String userNo, String tagName, int state, int page) {
        return getGetPageShow(userNo, tagName, state, page);
    }

    private Map<String, Object> getGetPageShow(String userNo, String tagName, int state, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("tagName", tagName);
        map.put("state", state);
        map.put("page", page);
        return map;
    }

    @Override
    public Map<String, Object> setGetViewAtlas(String atlasCode, int page, String userNo) {
        return getGetViewAtlas(atlasCode, page, userNo);
    }

    private Map<String, Object> getGetViewAtlas(String atlasCode, int page, String userNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("atlasCode", atlasCode);
        map.put("page", page);
        map.put("userNo", userNo);
        return map;
    }

    @Override
    public Map<String, Object> setGetLabel(String userNo, String tagName) {
        return getGetLabel(userNo, tagName);
    }

    private Map<String, Object> getGetLabel(String userNo, String tagName) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("tagName", tagName);
        return map;
    }

    @Override
    public Map<String, Object> setMyUserMsg(String userNo) {
        return getExitLogin(userNo);
    }

    @Override
    public Map<String, Object> setDynamicCode(String phone) {
        return getDynamicCode(phone);
    }

    @Override
    public Map<String, Object> setBug(String userNo, String atlasCode, String atlasName) {
        return getBug(userNo, atlasCode, atlasName);
    }

    private Map<String, Object> getBug(String userNo, String atlasCode, String atlasName) {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("atlasCode", atlasCode);
        map.put("atlasName", atlasName);
        return map;
    }

    private Map<String, Object> getDynamicCode(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        return map;
    }


    /**
     * 单例对象实例
     */
    private static class AggregateMapHolder {
        static final AggregateMap INSTANCE = new AggregateMap();
    }

    public static AggregateMap getInstance() {
        return AggregateMap.AggregateMapHolder.INSTANCE;
    }

    /**
     * private的构造函数用于避免外界直接使用new来实例化对象
     */
    private AggregateMap() {
    }
}
