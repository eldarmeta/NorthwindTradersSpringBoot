package com.pluralsight;

import com.pluralsight.data.ProductDao;
import com.pluralsight.data.SimpleProductDao;
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
            System.out.println("3. Find product by ID");
            System.out.println("4. Search product by name");
            System.out.println("5. Update product");
            System.out.println("6. Delete product by ID");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> listProducts();
                case "2" -> addProduct(scanner);
                case "3" -> findById(scanner);
                case "4" -> searchByName(scanner);
                case "5" -> updateProduct(scanner);
                case "6" -> deleteById(scanner);
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
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Category: ");
        String category = scanner.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        productDao.add(new Product(id, name, category, price));
        System.out.println("Product added.");
    }
    private void findById(Scanner scanner) {
        int id = Integer.parseInt(scanner.nextLine());
        Product p = ((SimpleProductDao) productDao).findById(id);
        System.out.println(p != null ? p : "Not found");
    }

    private void searchByName(Scanner scanner) {
        ((SimpleProductDao) productDao)
                .searchByName(scanner.nextLine())
                .forEach(System.out::println);
    }

    private void updateProduct(Scanner scanner) {
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New name: ");
        String name = scanner.nextLine();
        System.out.print("New category: ");
        String category = scanner.nextLine();
        System.out.print("New price: ");
        double price = Double.parseDouble(scanner.nextLine());

        ((SimpleProductDao) productDao)
                .update(new Product(id, name, category, price));
    }

    private void deleteById(Scanner scanner) {
        int id = Integer.parseInt(scanner.nextLine());
        ((SimpleProductDao) productDao).deleteById(id);
    }
}
