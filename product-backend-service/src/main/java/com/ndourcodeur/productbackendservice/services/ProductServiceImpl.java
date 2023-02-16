package com.ndourcodeur.productbackendservice.services;

import com.ndourcodeur.productbackendservice.dto.ProductDto;
import com.ndourcodeur.productbackendservice.exception.ResourceNotFoundException;
import com.ndourcodeur.productbackendservice.entity.Product;
import com.ndourcodeur.productbackendservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> findProducts() {
        List<Product> products = this.productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        products.stream().forEach(product -> {
            ProductDto productDto = mapEntityToDto(product);
            productDtos.add(productDto);
        });
        return productDtos;
    }

    @Override
    public List<ProductDto> findProductByCategoryName(String categoryName) {
        List<Product> products = this.productRepository.findProductByCategoryName(categoryName);
        List<ProductDto> productDtos = new ArrayList<>();
        products.stream().forEach(product -> {
            ProductDto productDto = mapEntityToDto(product);
            productDtos.add(productDto);
        });
        return productDtos;
    }

    @Override
    public ProductDto findProduct(Integer idProduct) {
        Product findProduct = this.productRepository.findById(idProduct)
                .orElseThrow( () -> new ResourceNotFoundException("Product not found with id="+idProduct));
        ProductDto productDto = mapEntityToDto(findProduct);
        return productDto;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = new Product();
        mapDtoToEntity(productDto, product);
        Product newProduct = this.productRepository.save(product);
        return mapEntityToDto(newProduct);
    }

    @Override
    public ProductDto editProduct(Integer idProduct, ProductDto productDto) {
        Product product = this.productRepository.findById(idProduct)
                .orElseThrow( () -> new ResourceNotFoundException("Product not found with id="+idProduct));
        mapDtoToEntity(productDto, product);
        Product editProduct = this.productRepository.save(product);
        return mapEntityToDto(editProduct);
    }

    @Override
    public void deleteProduct(Integer idProduct) {
        Product deleteProduct = this.productRepository.findById(idProduct)
                .orElseThrow( () -> new ResourceNotFoundException("Product not found with id="+idProduct));
        this.productRepository.delete(deleteProduct);
    }

    @Override
    public ProductDto findProductByName(String productName) {
        Product findProductByName = this.productRepository.findProductByName(productName);
        ProductDto productDto = mapEntityToDto(findProductByName);
        log.info("");
        return productDto;
    }

    @Override
    public boolean existsProductByName(String productName) {
        log.info("");
        return this.productRepository.existsProductByName(productName);
    }

    @Override
    public boolean existsProductById(Integer id) {
        log.info("");
        return this.productRepository.existsById(id);
    }

    private ProductDto mapEntityToDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setInStock(product.isInStock());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        dto.setCategoryName(product.getCategoryName());
        return dto;
    }

    private void mapDtoToEntity(ProductDto dto, Product product){
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setInStock(dto.isInStock());
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());
        product.setCategoryName(dto.getCategoryName());
    }
}
