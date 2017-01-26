package org.bk2rl.uporotiyinvestor.model;

public class Product implements Item{

    private String product;
    private String imgSrc;

    @Override
    public String getItem() {
        return product;
    }

    @Override
    public Product setItem(String product) {
        this.product = product;
        return this;
    }

    @Override
    public String getImgSrc() {
        return imgSrc;
    }

    @Override
    public Product setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product1 = (Product) o;

        if (product != null ? !product.equals(product1.product) : product1.product != null)
            return false;
        return imgSrc != null ? imgSrc.equals(product1.imgSrc) : product1.imgSrc == null;

    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (imgSrc != null ? imgSrc.hashCode() : 0);
        return result;
    }
}
