package org.another.vtbsring.service;

import org.another.vtbsring.entry.Cart;
import org.another.vtbsring.entry.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderService {
    public void createOrder(Cart cart) {
        List<Product> cartProduct = cart.getProducts();
        int detailsPrice = 0;

        for (Product product : cartProduct) {
            detailsPrice += product.getCost();
        }
        System.out.println(detailsPrice);
    }
}

class Main {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        Cart cart = new Cart();
        ProductService productService = new ProductService();

        Product product = productService.findByTitle("Яблоко");
        cart.add(product);

        Product product1 = productService.findByTitle("Сыр");
        cart.add(product1);

        orderService.createOrder(cart);
    }
}
