package org.bk2rl.uporotiyinvestor.model;

public class Feature extends Item{

    @Override
    public String getFullImgSrc() {
        return "file:///android_asset/features/" + getImgSrc();
    }

}
