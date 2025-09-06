package br.com.gerenciamento.api_products.dto;

public record ProductResponseDTO(Long id,
                                 String productName,
                                 java.math.BigDecimal productPrice,
                                 String productCategory,
                                 String productDescription
) {
}
