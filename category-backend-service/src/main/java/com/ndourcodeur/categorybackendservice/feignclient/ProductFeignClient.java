package com.ndourcodeur.categorybackendservice.feignclient;

import com.ndourcodeur.categorybackendservice.payload.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(
        name = "product-backend-service",
        url = "http://localhost:8081",
        path = "/api/v1/products"
)
public interface ProductFeignClient {
    @PostMapping(path = "")
    Product addNewProduct(@Valid @RequestBody Product product);

    @PutMapping(path = "/{idProduct}")
    public Product updateProductById(@PathVariable("idProduct") Integer idProduct , @Valid @RequestBody Product product);

    @DeleteMapping(path = "/{idProduct}")
    public void deleteProductById(@PathVariable Integer idProduct);

    @GetMapping(path = "")
    public List<Product> findAllProducts();

    @GetMapping(path = "/{idProduct}")
    public Product findProductById(Integer idProduct);

    @GetMapping(path = "/findByName/{name}")
    public Product findProductByName(String name);

    @GetMapping(path = "/byCategoryName/{categoryName}")
    public List<Product> findAllProductsByCategoryName(@PathVariable("categoryName") String categoryName);

}