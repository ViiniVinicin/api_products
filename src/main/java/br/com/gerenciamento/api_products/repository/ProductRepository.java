package br.com.gerenciamento.api_products.repository;

import br.com.gerenciamento.api_products.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {


    private final List<Product> productList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Product> findAll() {
        return new ArrayList<>(productList);
    }

    public Optional<Product> findById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idCounter.incrementAndGet());
            productList.add(product);
            return product;
        } else {
            findById(product.getId()).ifPresent(existingProduct -> {
                productList.remove(existingProduct);
                productList.add(product);
            });
            return product;
        }
    }

    public void deleteById(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }
}