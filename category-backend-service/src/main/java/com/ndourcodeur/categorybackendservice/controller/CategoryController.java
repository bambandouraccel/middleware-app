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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/categories")
@CrossOrigin(origins = "*")
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
        list = list.stream()
                .sorted(Comparator.comparing(CategoryDto::getId).reversed())
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> findCategoryById(@PathVariable("id") int id){
        CategoryDto categoryDto = this.categoryService.findCategory(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping(path = "/findCategoryName/{categoryName}")
    public ResponseEntity<?> findCategoryById(@PathVariable("categoryName") String categoryName){
        if (!this.categoryService.existsCategoryByName(categoryName)){
            return new ResponseEntity<>(new Message(""), HttpStatus.OK);
        }
        CategoryDto dto = this.categoryService.findCategoryByName(categoryName);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto){
        if(this.categoryService.existsCategoryByName(categoryDto.getName())){
            return new ResponseEntity(new Message("CATEGORY NAME IS ALREADY IN USE!"), HttpStatus.BAD_REQUEST);
        }
        CategoryDto dto = this.categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Integer id, @Valid @RequestBody CategoryDto categoryDto){
        if(this.categoryService.existsCategoryByName(categoryDto.getName()) && this.categoryService.findCategoryByName(categoryDto.getName()).getId() != id){
            return new ResponseEntity(new Message("CATEGORY NAME IS ALREADY IN USE!"), HttpStatus.BAD_REQUEST);
        }
        if(!this.categoryService.existsCategoryByName(categoryDto.getName())){
            return new ResponseEntity(new Message("CATEGORY NOT FOUND WITH ID:"+id), HttpStatus.BAD_REQUEST);
        }
        CategoryDto editCategory = this.categoryService.editCategory(id, categoryDto);
        return new ResponseEntity<>(editCategory, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Message> deleteCategory(@PathVariable("id") Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(new Message("Category deleted successfully with id:"+id), HttpStatus.OK);
    }

    @PostMapping(path = "/products")
    public ResponseEntity<?> addProductViaProductBackendServiceRestApi(@Valid @RequestBody Product product){
        Product addProduct = this.categoryService.saveProduct(product);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @PutMapping(path = "/products/{idProduct}")
    public ResponseEntity<?> updateProductByIdViaProductBackendServiceRestApi(@Valid @PathVariable Integer idProduct,
                                                                              @Valid @RequestBody Product product){
        Product updateProduct = this.categoryService.editProductById(idProduct, product);
        return new ResponseEntity<>(updateProduct, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/products/{idProduct}")
    public ResponseEntity<?> removeProductByIdViaProductBackendServiceRestApi(@PathVariable Integer idProduct){
        categoryService.deleteProductById(idProduct);
        return new ResponseEntity<>(new Message("Product with ID:"+idProduct+" deleted successfully!"), HttpStatus.OK);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<?> findAllProductsViaProductBackendServiceRestApi(){
        List<Product> productList = this.categoryService.findAllProductsViaProductService();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping(path = "/products/{idProduct}")
    public ResponseEntity<?> findProductByIdViaProductBackendServiceRestApi(@PathVariable("idProduct") Integer idProduct){
        Product findProduct = this.categoryService.findProductById(idProduct);
        return new ResponseEntity<>(findProduct, HttpStatus.OK);
    }

    @GetMapping(path = "/products/findProductByName/{productName}")
    public ResponseEntity<?> findProductByNameViaProductBackendServiceRestApi(@PathVariable String productName){
        Product product = this.categoryService.findProductByName(productName);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(path = "/products/allProducts/{categoryName}")
    public ResponseEntity<?> findCategoriesWithProductsViaProductBackendServiceRestApi(@PathVariable String categoryName){
        Map<String, Object> result = categoryService.findAllCategoriesAndProducts(categoryName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
