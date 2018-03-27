package com.sky.beautiful.Model;

/**
 * @Time : 2017/12/29 no 上午11:34
 * @USER : vvguoliang
 * @File : ViewAtlas.java
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

public class ViewAtlas extends DataEntity {

    private String imgID = "";
    private String url = "";
    private String imageAuth = "";
    private String isSee = "";
    private String isCollect = "";
    private String isLike = "";
    private String atlasPrice = "";

    public ViewAtlas() {
        super();
    }

    public ViewAtlas(String imgID, String url, String imageAuth, String isSee, String isCollect, String isLike, String atlasPrice) {
        super();
        this.imgID = imgID;
        this.imageAuth = imageAuth;
        this.isCollect = isCollect;
        this.isLike = isLike;
        this.isSee = isSee;
        this.url = url;
        this.atlasPrice = atlasPrice;
    }

    public String getImgID() {
        return imgID;
    }

    public void setImgID(String imgID) {
        this.imgID = imgID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageAuth() {
        return imageAuth;
    }

    public void setImageAuth(String imageAuth) {
        this.imageAuth = imageAuth;
    }

    public String getIsSee() {
        return isSee;
    }

    public void setIsSee(String isSee) {
        this.isSee = isSee;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getAtlasPrice() {
        return atlasPrice;
    }

    public void setAtlasPrice(String atlasPrice) {
        this.atlasPrice = atlasPrice;
    }
}
