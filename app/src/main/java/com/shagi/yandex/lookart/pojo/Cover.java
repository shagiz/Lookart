package com.shagi.yandex.lookart.pojo;

/**
 * Created by Shagi on 06.04.2016.
 */
public class Cover {
    private String big;

    private String small;

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Override
    public String toString() {
        return "ClassPojo [big = " + big + ", small = " + small + "]";
    }
}