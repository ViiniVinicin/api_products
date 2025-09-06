package br.com.gerenciamento.api_products.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotBlank @Size(min = 3, max = 100) String productName,
        @NotBlank @Size(min = 3, max = 50)String productCategory,
        @NotBlank @Size(min = 3, max = 255) String productDescription,
        @NotNull @Positive  BigDecimal productPrice,
        int productStockQuantity
){}
