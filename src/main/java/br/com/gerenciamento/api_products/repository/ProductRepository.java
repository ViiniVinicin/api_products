package br.com.gerenciamento.api_products.repository;

import br.com.gerenciamento.api_products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductNameIgnoreCase(String productName);
    List<Product> findByProductCategory(String category);

}
