package com.ndourcodeur.productbackendservice.controller;

import com.ndourcodeur.productbackendservice.dto.ProductDto;
import com.ndourcodeur.productbackendservice.message.Message;
import com.ndourcodeur.productbackendservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "")
    public ResponseEntity<?> findAllProducts(){
        List<ProductDto> list = this.productService.findProducts();
        if(list.isEmpty()){
            return new ResponseEntity<>(new Message("List of products is empty!"), HttpStatus.BAD_REQUEST);
        }
        list = list.stream()
                .sorted(Comparator.comparing(ProductDto::getId).reversed())
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/byCategoryName/{categoryName}")
    public ResponseEntity<?> findAllProductsByCategoryName(@PathVariable("categoryName") String categoryName){
        List<ProductDto> productDtos = productService.findProductByCategoryName(categoryName);
        if (productDtos.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No content almost!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<ProductDto> findProductByName(@PathVariable("name") String name){
        if(!this.productService.existsProductByName(name)){
            return new ResponseEntity(new Message("Product not found with name="+name+ "!"), HttpStatus.BAD_REQUEST);
        }
        ProductDto findProductByName = this.productService.findProductByName(name);
        return new ResponseEntity<>(findProductByName, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable("id") int id){
        if(!this.productService.existsProductById(id)){
            return new ResponseEntity(new Message("Product not found with id="+id+ "!"), HttpStatus.BAD_REQUEST);
        }
        ProductDto productDto = this.productService.findProduct(id);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        if(this.productService.existsProductByName(productDto.getName())){
            return new ResponseEntity(new Message("PRODUCT NAME IS ALREADY IN USE!"), HttpStatus.BAD_REQUEST);
        }
        ProductDto dto = this.productService.addProduct(productDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable("id") Integer id, @RequestBody ProductDto productDto){
        if(this.productService.existsProductByName(productDto.getName()) && this.productService.findProductByName(productDto.getName()).getId() != id){
            return new ResponseEntity(new Message("PRODUCT NAME IS ALREADY IN USE!"), HttpStatus.BAD_REQUEST);
        }
        if(!this.productService.existsProductById(productDto.getId())){
            return new ResponseEntity(new Message("PRODUCT NOT FOUND WITH ID:"+id), HttpStatus.BAD_REQUEST);
        }
        ProductDto editProductDto = this.productService.editProduct(id, productDto);
        return new ResponseEntity<>(editProductDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Message> deleteProductById(@PathVariable("id") Integer id){
        if(!this.productService.existsProductById(id)){
            return new ResponseEntity(new Message("Product not found with id="+id+ "!"), HttpStatus.BAD_REQUEST);
        }
        this.productService.deleteProduct(id);
        return new ResponseEntity<>(new Message("Product deleted successfully with id:"+id), HttpStatus.OK);
    }
}
