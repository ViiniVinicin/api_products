package br.com.gerenciamento.api_products.service;

import br.com.gerenciamento.api_products.dto.ProductCreateDTO;
import br.com.gerenciamento.api_products.dto.ProductResponseDTO;
import br.com.gerenciamento.api_products.exception.ProductAlreadyExistsException;
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

    public List<ProductResponseDTO> getByProductsCategory(String category) {
        // A CHAMADA AO REPOSITÓRIO ESTÁ CORRETA?
        List<Product> products = productRepository.findByProductCategory(category);

        if(products.isEmpty()) {
            throw new ResourceNotFoundException("Erro: Nenhum produto encontrado na categoria: " + category);
        } else {
            return products.stream()
                    .map(this::toResponseDTO)
                    .toList();
        }
    }

    public ProductResponseDTO createProduct(ProductCreateDTO createDTO) {

        productRepository.findByProductNameIgnoreCase(createDTO.productName())
                .ifPresent(product -> {
                    throw new ProductAlreadyExistsException("Um produto com o nome '" + createDTO.productName() + "' já existe.");
                });

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
}
