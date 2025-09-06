package br.com.gerenciamento.api_products.service;

import br.com.gerenciamento.api_products.dto.ProductCreateDTO;
import br.com.gerenciamento.api_products.dto.ProductResponseDTO;
import br.com.gerenciamento.api_products.dto.ProductUpdateDTO;
import br.com.gerenciamento.api_products.exception.ResourceNotFoundException;
import br.com.gerenciamento.api_products.model.Product;
import br.com.gerenciamento.api_products.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getAllProducts() {

        List<Product> allProducts = productRepository.findAll();

        if (allProducts.isEmpty()) {
            throw new ResourceNotFoundException("Erro: Nenhum produto encontrado.");
        } else {
            return productRepository.findAll()
                    .stream()
                    .map(this::toResponseDTO)
                    .toList();
        }
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Erro: Produto não encontrado pelo ID: " + id));
        return toResponseDTO(product);
    }

    public ProductResponseDTO createProduct(ProductCreateDTO createDTO) {
        Product newProduct = new Product();
        mapDtoToEntity(newProduct, createDTO);

        Product savedProduct = productRepository.save(newProduct);
        return toResponseDTO(savedProduct);
    }

    public ProductResponseDTO updateProduct(Long id, @Valid ProductCreateDTO updateDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Erro: Produto não encontrado pelo ID: " + id));

        mapDtoToEntity(existingProduct, updateDTO);

        Product updatedProduct = productRepository.save(existingProduct);
        return toResponseDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Erro: Produto não encontrado pelo ID: " + id));

        productRepository.deleteById(id);
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductCategory(),
                product.getProductDescription()
        );
    }

    private void mapDtoToEntity(Product product, ProductCreateDTO dto) {
        product.setProductName(dto.productName());
        product.setProductDescription(dto.productDescription());
        product.setProductCategory(dto.productCategory());
        product.setProductPrice(dto.productPrice());
        product.setProductStockQuantity(dto.productStockQuantity());
    }

    private void mapDtoToEntity(Product product, ProductUpdateDTO dto) {
        product.setProductName(dto.productName());
        product.setProductDescription(dto.productDescription());
        product.setProductCategory(dto.productCategory());
        product.setProductPrice(dto.productPrice());
        product.setProductStockQuantity(dto.productStockQuantity());
    }
}
