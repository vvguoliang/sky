package com.sky.beautiful.Model;

import android.support.annotation.NonNull;

/**
 * @Time : 2017/12/27 no 上午11:03
 * @USER : vvguoliang
 * @File : GetHomeBanner.java
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

public class GetHomeBanner extends DataEntity implements Comparable<GetHomeBanner> {

    private String atlasNo;//图集编号
    private String url;//图片地址
    private int sort;//排序

    public GetHomeBanner() {
        super();
    }

    public GetHomeBanner(String atlasNo, String url, int sort) {
        super();
        this.atlasNo = atlasNo;
        this.url = url;
        this.sort = sort;
    }

    public String getAtlasNo() {
        return atlasNo;
    }

    public void setAtlasNo(String atlasNo) {
        this.atlasNo = atlasNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int compareTo(@NonNull GetHomeBanner o) {
        int i = this.getSort() - o.getSort();//先按照年龄排序
        if (i == 0) {
            return this.sort - o.getSort();//如果年龄相等了再用分数进行排序
        }
        return i;
    }
}
