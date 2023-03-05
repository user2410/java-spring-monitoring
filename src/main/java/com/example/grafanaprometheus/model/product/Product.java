package com.example.grafanaprometheus.model.product;

public class Product {
    private String name;
    private String brand;
    private int price;

    final static String template = "{ name: %s, brand: %s, price: %s }";

    public Product(String name, String brand, int price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return String.format(template, this.name, this.brand, this.price);
    }
}
