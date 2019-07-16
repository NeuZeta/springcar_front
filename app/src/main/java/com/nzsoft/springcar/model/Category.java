package com.nzsoft.springcar.model;

public class Category {

    private String name;
    private Double baseInsurancePrice;
    private Double topInsurancePrice;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBaseInsurancePrice() {
        return baseInsurancePrice;
    }

    public void setBaseInsurancePrice(Double baseInsurancePrice) {
        this.baseInsurancePrice = baseInsurancePrice;
    }

    public Double getTopInsurancePrice() {
        return topInsurancePrice;
    }

    public void setTopInsurancePrice(Double topInsurancePrice) {
        this.topInsurancePrice = topInsurancePrice;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", baseInsurancePrice=" + baseInsurancePrice +
                ", topInsurancePrice=" + topInsurancePrice +
                '}';
    }
}
