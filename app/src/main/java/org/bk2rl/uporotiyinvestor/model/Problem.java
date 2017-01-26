package org.bk2rl.uporotiyinvestor.model;

public class Problem implements Item {

    private String product;
    private String imgSrc;

    @Override
    public String getItem() {
        return product;
    }

    @Override
    public Problem setItem(String product) {
        this.product = product;
        return this;
    }

    @Override
    public String getImgSrc() {
        return imgSrc;
    }

    @Override
    public Problem setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem1 = (Problem) o;

        if (product != null ? !product.equals(problem1.product) : problem1.product != null)
            return false;
        return imgSrc != null ? imgSrc.equals(problem1.imgSrc) : problem1.imgSrc == null;

    }

    @Override
    public int hashCode() {
        int result = product != null ? product.hashCode() : 0;
        result = 31 * result + (imgSrc != null ? imgSrc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "product='" + product + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
