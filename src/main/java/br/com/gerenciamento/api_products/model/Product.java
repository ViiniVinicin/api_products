package br.com.gerenciamento.api_products.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private String productCategory;
    private int productStockQuantity;

}