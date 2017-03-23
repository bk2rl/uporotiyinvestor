package org.bk2rl.uporotiyinvestor.model;

public class Product extends Item{
    @Override
    public String getFullImgSrc() {
        return "file:///android_asset/products/" + getImgSrc();
    }
}
