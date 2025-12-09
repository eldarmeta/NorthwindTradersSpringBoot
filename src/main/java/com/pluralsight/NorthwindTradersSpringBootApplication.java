package com.pluralsight;

import com.pluralsight.data.ProductDao;
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
            System.out.println("\n*** Northwind Admin ***");
            System.out.println("1. List products");
            System.out.println("2. Add product");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> listProducts(productDao);
                case "2" -> addProduct(scanner, productDao);
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. try again");
            }
        }
    }

    private static void listProducts(ProductDao productDao) {
        List<Product> products = productDao.getAll();
        System.out.println("\nID | Name | Category | Price");
        System.out.println("-------------------------------------");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static void addProduct(Scanner scanner, ProductDao productDao) {
        System.out.print("Enter product id: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Product product = new Product(id, name, category, price);
        productDao.add(product);
        System.out.println("Product added!");
    }
}
