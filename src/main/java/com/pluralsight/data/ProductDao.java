package com.pluralsight.data;

import com.pluralsight.models.Product;

import java.util.List;

public interface ProductDao {
    void add(Product product);
    List<Product> getAll();
}
