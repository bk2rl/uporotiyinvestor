package org.bk2rl.uporotiyinvestor.model;

public class Problem extends Item {

    @Override
    public String getFullImgSrc() {
        return "file:///android_asset/problems/" + getImgSrc();
    }

}
