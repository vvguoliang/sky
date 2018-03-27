package com.sky.beautiful.Model;

/**
 * @Time : 2017/12/29 no 下午6:53
 * @USER : vvguoliang
 * @File : MyCollect.java
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

public class MyCollect extends DataEntity {

    private String imgID = "";
    private String imageUrl = "";
    private String imageAtlasCode = "";
    private int collectType = 1;
    private int px = 0;
    private String atlasName = "";
    private String firstImage = "";

    public MyCollect() {
        super();
    }

    public MyCollect(String imgID, String imageUrl, String imageAtlasCode, int px, int collectType,String atlasName,String firstImage) {
        super();
        this.imageUrl = imageUrl;
        this.imgID = imgID;
        this.imageAtlasCode = imageAtlasCode;
        this.px = px;
        this.collectType = collectType;
        this.atlasName = atlasName;
        this.firstImage = firstImage;
    }

    public String getImgID() {
        return imgID;
    }

    public void setImgID(String imgID) {
        this.imgID = imgID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageAtlasCode() {
        return imageAtlasCode;
    }

    public void setImageAtlasCode(String imageAtlasCode) {
        this.imageAtlasCode = imageAtlasCode;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getCollectType() {
        return collectType;
    }

    public void setCollectType(int collectType) {
        this.collectType = collectType;
    }

    public String getAtlasName() {
        return atlasName;
    }

    public void setAtlasName(String atlasName) {
        this.atlasName = atlasName;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }
}
