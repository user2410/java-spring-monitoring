package com.example.grafanaprometheus.model.product;

import com.example.grafanaprometheus.model.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductDAO implements Dao<Product> {

    private List<Product> products = new ArrayList<>();

    public ProductDAO(){
        products.add(new Product("IPhone 14", "Apple", 60));
        products.add(new Product("Samsung FE", "Samsung", 24));
        products.add(new Product("Xperia", "Sony", 20));
    }

    @Override
    public Optional<Product> get(long id) {
        return Optional.ofNullable(products.get((int)id));
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void update(Product product, String[] params) {
        product.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        product.setBrand(Objects.requireNonNull(params[1], "Brand cannot be null"));
        product.setPrice(Objects.requireNonNull(Integer.parseInt(params[2]), "Price cannot be null"));
        products.add(product);
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }
}
