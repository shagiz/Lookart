package com.shagi.yandex.lookart.pojo;

import java.io.Serializable;

/**
 * POJO
 * @author Shagi
 */
public class Cover implements Serializable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cover cover = (Cover) o;

        return big != null ? big.equals(cover.big) : cover.big == null && (small != null ? small.equals(cover.small) : cover.small == null);

    }

    @Override
    public int hashCode() {
        int result = big != null ? big.hashCode() : 0;
        result = 31 * result + (small != null ? small.hashCode() : 0);
        return result;
    }
}