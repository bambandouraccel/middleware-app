package com.ndourcodeur.categorybackendservice.controller;

import com.ndourcodeur.categorybackendservice.dto.CategoryDto;
import com.ndourcodeur.categorybackendservice.message.Message;
import com.ndourcodeur.categorybackendservice.payload.Product;
import com.ndourcodeur.categorybackendservice.repository.CategoryRepository;
import com.ndourcodeur.categorybackendservice.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(path = "")
    public ResponseEntity<?> findAllCategories(){
        List<CategoryDto> list = this.categoryService.findCategories();
        if(list.isEmpty()){
            return new ResponseEntity<>(new Message("Empty list!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable("id") int id){
        CategoryDto categoryDto = this.categoryService.findCategory(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<CategoryDto> addNewCategory(@RequestBody CategoryDto categoryDto){
        /*if(this.productRepository.existsProductByName(productDto.getName())){
            return new ResponseEntity(new Message("PRODUCT NAME IS ALREADY IN USE!"), HttpStatus.BAD_REQUEST);
        }*/
        CategoryDto dto = this.categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Integer id, @RequestBody CategoryDto categoryDto){
        /*if(this.productRepository.existsProductByName(productDto.getName()) && this.productService.findProduct(productDto.getId()).getId() != id){
            return new ResponseEntity(new Message("PRODUCT NAME IS ALREADY IN USE!"), HttpStatus.BAD_REQUEST);
        }
        if(!this.productRepository.existsById(productDto.getId())){
            return new ResponseEntity(new Message("PRODUCT NOT FOUND WITH ID:"+id), HttpStatus.BAD_REQUEST);
        }*/
        CategoryDto editCategory = this.categoryService.editCategory(id, categoryDto);
        return new ResponseEntity<>(editCategory, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Message> deleteCategory(@PathVariable("id") Integer id){
        /*if(!this.productRepository.existsById(id)){
            return new ResponseEntity(new Message("Product not found with id="+id+ "!"), HttpStatus.BAD_REQUEST);
        }*/
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new Message("Category deleted successfully with id:"+id), HttpStatus.OK);
    }

    @PostMapping(path = "/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product){
        Product addProduct = this.categoryService.saveProduct(product);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @PutMapping(path = "/products/{idProduct}")
    public ResponseEntity<?> updateProductById(@PathVariable Integer idProduct, @Valid @RequestBody Product product){
        Product updateProduct = this.categoryService.editProductById(idProduct, product);
        return new ResponseEntity<>(updateProduct, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/products/{idProduct}")
    public ResponseEntity<?> removeProductById(@PathVariable Integer idProduct){
        categoryService.deleteProductById(idProduct);
        return new ResponseEntity<>(new Message("Product with ID:"+idProduct+" deleted successfully!"), HttpStatus.OK);
    }

    @GetMapping(path = "/products/{categoryName}")
    public ResponseEntity<?> findCategoriesWithProducts(@PathVariable String categoryName){
        Map<String, Object> result = categoryService.findAllCategoriesAndProducts(categoryName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
