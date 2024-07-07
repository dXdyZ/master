package org.another.vtbsring.service;

import org.another.vtbsring.entry.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {
    private List<Product> productsList;

    public ProductService() {
        this.productsList = new ArrayList<>() {
            {
                add(new Product(1, "Яблоко", 30));
                add(new Product(2, "Молоко", 70));
                add(new Product(3, "Сыр", 130));
                add(new Product(4, "Коффе", 230));
                add(new Product(5, "Хлеб", 40));
            }
        };
    }

    public void printAll() {
        productsList.forEach(System.out::println);
    }

    public Product findByTitle(String title) {
        for (Product product : productsList) {
            if (product.getTitle().equals(title)) {
                return product;
            }
        }
        return null;
    }
}
