package com.pluralsight;

import com.pluralsight.data.ProductDao;
import com.pluralsight.data.SimpleProductDao;
import com.pluralsight.models.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);

        ProductDao productDao = context.getBean(ProductDao.class);

        runConsoleMenu(productDao);
    }

    private static void runConsoleMenu(ProductDao productDao) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n**** NORTHWIND ADMIN MENU ****");
            System.out.println("1. List products");
            System.out.println("2. Add product");
            System.out.println("3. Find product by ID");
            System.out.println("4. Search product by name");
            System.out.println("5. Update product");
            System.out.println("6. Delete product by ID");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> listProducts(productDao);
                case "2" -> addProduct(scanner, productDao);
                case "3" -> findById(scanner, productDao);
                case "4" -> searchByName(scanner, productDao);
                case "5" -> updateProduct(scanner, productDao);
                case "6" -> deleteById(scanner, productDao);
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option ^ try again.");
            }
        }
    }

    private static void listProducts(ProductDao productDao) {
        List<Product> products = productDao.getAll();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\nID | NAME | CATEGORY | PRICE");
        System.out.println("------------------------------------");
        products.forEach(System.out::println);
    }

    private static void addProduct(Scanner scanner, ProductDao productDao) {

        System.out.print("Enter product ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Product product = new Product(id, name, category, price);
        productDao.add(product);

        System.out.println("Product added successfully.");
    }

    private static void findById(Scanner scanner, ProductDao productDao) {

        System.out.print("Enter product ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        Product product =
                ((SimpleProductDao) productDao).findById(id);

        if (product == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println(product);
        }
    }

    private static void searchByName(Scanner scanner, ProductDao productDao) {

        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        List<Product> results =
                ((SimpleProductDao) productDao).searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No matching products found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void updateProduct(Scanner scanner, ProductDao productDao) {

        System.out.print("Enter product ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        SimpleProductDao dao = (SimpleProductDao) productDao;
        Product existing = dao.findById(id);

        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("New name: ");
        String name = scanner.nextLine();

        System.out.print("New category: ");
        String category = scanner.nextLine();

        System.out.print("New price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Product updated = new Product(id, name, category, price);
        dao.update(updated);

        System.out.println("Product updated successfully.");
    }

    private static void deleteById(Scanner scanner, ProductDao productDao) {

        System.out.print("Enter product ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean deleted =
                ((SimpleProductDao) productDao).deleteById(id);

        if (deleted) {
            System.out.println("Product deleted.");
        } else {
            System.out.println("Product not found.");
        }
    }
}
