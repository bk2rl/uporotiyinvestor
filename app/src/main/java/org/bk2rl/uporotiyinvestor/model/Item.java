package org.bk2rl.uporotiyinvestor.model;

import android.os.Parcelable;

import java.io.Serializable;

public abstract class Item implements Serializable{

    private String item;
    private String imgSrc;

    public String getItem() {
        return item;
    }

    public Item setItem(String product) {
        this.item = product;
        return this;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public Item setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        return this;
    }

    public abstract String getFullImgSrc();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item product1 = (Item) o;

        if (item != null ? !item.equals(product1.item) : product1.item != null)
            return false;
        return imgSrc != null ? imgSrc.equals(product1.imgSrc) : product1.imgSrc == null;

    }

    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + (imgSrc != null ? imgSrc.hashCode() : 0);
        return result;
    }
}
