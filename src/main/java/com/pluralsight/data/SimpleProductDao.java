package com.pluralsight.data;

import com.pluralsight.models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
public class SimpleProductDao implements ProductDao {

    private final List<Product> products = new ArrayList<>();

    public SimpleProductDao() {
        products.add(new Product(1, "Chai EL", "Beverages", 18.0));
        products.add(new Product(2, "Jalal-Abad 24", "Beverages", 19.0));
        products.add(new Product(3, "Syrup", "Condiments", 10.0));
    }

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products);
    }

    public boolean deleteById(int productId) {
        return products.removeIf(p -> p.getProductId() == productId);
    }

    public Product findById(int productId) {
        return products.stream()
                .filter(p -> p.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }

    public boolean update(Product updated) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == updated.getProductId()) {
                products.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public List<Product> searchByName(String namePart) {
        String search = namePart.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(search))
                .toList();
    }
}
