package org.another.vtbsring.entry;

import org.another.vtbsring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {
    List<Product> products;
    public Cart() {
        this.products = new ArrayList<>();
    }

    public void add(Product newProd) {
        products.add(newProd);
    }

    public List<Product> getProducts() {
        return products;
    }
}
