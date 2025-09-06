package br.com.gerenciamento.api_products.controller;

import br.com.gerenciamento.api_products.dto.ProductCreateDTO;
import br.com.gerenciamento.api_products.dto.ProductResponseDTO;
import br.com.gerenciamento.api_products.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController{

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        List<ProductResponseDTO> productsDTO = productService.getAllProducts();
        return ResponseEntity.ok(productsDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category") // URL será /products/category?name=Perfumes
    public ResponseEntity<List<ProductResponseDTO>> getByProductsCategory(@RequestParam("name") String category) {
        List<ProductResponseDTO> productsDTO = productService.getByProductsCategory(category);
        return ResponseEntity.ok(productsDTO);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(productCreateDTO);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.id())
                .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProductCreateDTO productCreateDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productCreateDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}