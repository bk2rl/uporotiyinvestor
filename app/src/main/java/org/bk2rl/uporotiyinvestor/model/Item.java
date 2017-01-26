package org.bk2rl.uporotiyinvestor.model;

public interface Item {

    enum ItemType{
        PRODUCT,PROBLEM,FEATURE
    }

    String getItem();

    Item setItem(String product);

    String getImgSrc();

    Item setImgSrc(String imgSrc);
}
