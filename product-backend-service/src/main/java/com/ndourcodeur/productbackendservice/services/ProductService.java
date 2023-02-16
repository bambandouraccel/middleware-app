package com.ndourcodeur.productbackendservice.services;

import com.ndourcodeur.productbackendservice.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findProducts();
    public List<ProductDto> findProductByCategoryName(String categoryName);
    ProductDto findProduct(Integer idProduct);
    ProductDto addProduct(ProductDto productDto);
    ProductDto editProduct(Integer idProduct, ProductDto productDto);
    void deleteProduct(Integer idProduct);
    ProductDto findProductByName(String productName);
    boolean existsProductByName(String productName);
    boolean existsProductById(Integer id);

}
