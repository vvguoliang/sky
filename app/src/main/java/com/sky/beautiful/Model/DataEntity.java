package com.sky.beautiful.Model;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by vvguoliang on 2017/9/19.
 * <p>
 * 基类 实体类
 */

public class DataEntity implements Serializable {

    private String code = "";
    private String msg = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
