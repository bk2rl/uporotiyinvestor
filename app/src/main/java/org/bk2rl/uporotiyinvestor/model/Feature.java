package org.bk2rl.uporotiyinvestor.model;

public class Feature implements Item{

    private String feature;
    private String imgSrc;

    @Override
    public String getItem() {
        return feature;
    }

    @Override
    public Feature setItem(String product) {
        this.feature = product;
        return this;
    }

    @Override
    public String getImgSrc() {
        return imgSrc;
    }

    @Override
    public Feature setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature1 = (Feature) o;

        if (feature != null ? !feature.equals(feature1.feature) : feature1.feature != null)
            return false;
        return imgSrc != null ? imgSrc.equals(feature1.imgSrc) : feature1.imgSrc == null;

    }

    @Override
    public int hashCode() {
        int result = feature != null ? feature.hashCode() : 0;
        result = 31 * result + (imgSrc != null ? imgSrc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "feature='" + feature + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }
}
