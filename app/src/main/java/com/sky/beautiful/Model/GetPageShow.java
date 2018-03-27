package com.sky.beautiful.Model;

/**
 * @Time : 2017/12/28 no 下午12:58
 * @USER : vvguoliang
 * @File : GetPageShow.java
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

public class GetPageShow extends DataEntity {


    private String atlasName = "";
    private String atlasCode = "";
    private String firstImage = "";
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int num = 0;
    private int viewTimes = 0;

    private int isClose = 0;

    private String ID = "";

    public GetPageShow() {
        super();
    }

    public GetPageShow(String atlasName, String atlasCode, String firstImage, int imageWidth, int imageHeight, int num, int viewTimes, int isClose, String ID) {
        super();
        this.atlasCode = atlasCode;
        this.atlasName = atlasName;
        this.firstImage = firstImage;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.num = num;
        this.viewTimes = viewTimes;
        this.isClose = isClose;
        this.ID = ID;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getAtlasName() {
        return atlasName;
    }

    public void setAtlasName(String atlasName) {
        this.atlasName = atlasName;
    }

    public String getAtlasCode() {
        return atlasCode;
    }

    public void setAtlasCode(String atlasCode) {
        this.atlasCode = atlasCode;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(int viewTimes) {
        this.viewTimes = viewTimes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }
}
