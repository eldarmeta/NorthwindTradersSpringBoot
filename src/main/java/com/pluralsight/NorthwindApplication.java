package com.pluralsight;

import com.pluralsight.data.ProductDao;
import com.pluralsight.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindApplication implements CommandLineRunner {

    @Autowired
    private ProductDao productDao;

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n**** NORTHWIND ADMIN ****");
            System.out.println("1. List products");
            System.out.println("2. Add product");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> listProducts();
                case "2" -> addProduct(scanner);
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listProducts() {
        List<Product> products = productDao.getAll();
        products.forEach(System.out::println);
    }

    private void addProduct(Scanner scanner) {

        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Product product = new Product(id, name, category, price);
        productDao.add(product);

        System.out.println("Product added.");
    }
}
