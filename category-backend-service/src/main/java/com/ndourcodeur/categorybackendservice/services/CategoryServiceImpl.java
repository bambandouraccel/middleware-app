package com.ndourcodeur.categorybackendservice.services;

import com.ndourcodeur.categorybackendservice.dto.CategoryDto;
import com.ndourcodeur.categorybackendservice.entity.Category;
import com.ndourcodeur.categorybackendservice.exception.ResourceNotFoundException;
import com.ndourcodeur.categorybackendservice.feignclient.ProductFeignClient;
import com.ndourcodeur.categorybackendservice.payload.Product;
import com.ndourcodeur.categorybackendservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("categoryService")
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final ProductFeignClient productFeignClient;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.stream().forEach(category -> {
            CategoryDto categoryDto = mapEntityToDto(category);
            categoryDtos.add(categoryDto);
        });
        return categoryDtos;
    }

    @Override
    public CategoryDto findCategory(Integer idCategory) {
        Category category = this.categoryRepository.findById(idCategory)
                .orElseThrow( () -> new ResourceNotFoundException("Category not found with id="+idCategory));
        CategoryDto findCategory = mapEntityToDto(category);
        return findCategory;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        mapDtoToEntity(categoryDto, category);
        Category newCategory = this.categoryRepository.save(category);
        return mapEntityToDto(newCategory);
    }

    @Override
    public CategoryDto editCategory(Integer idCategory, CategoryDto categoryDto) {
        Category category = this.categoryRepository.findById(idCategory)
                .orElseThrow( () -> new ResourceNotFoundException("Category not found with id="+idCategory));
        mapDtoToEntity(categoryDto, category);
        Category editCategory = this.categoryRepository.save(category);
        return mapEntityToDto(editCategory);
    }

    @Override
    public void deleteCategory(Integer idCategory) {
        Category deleteCategory = this.categoryRepository.findById(idCategory)
                .orElseThrow( () -> new ResourceNotFoundException("Category not found with id="+idCategory));
        this.categoryRepository.delete(deleteCategory);
    }
    @Override
    public Product saveProduct(Product product) {
        //log.info("Inside saveCar of UserService");
        //product.setCategoryName(categoryName);
        return this.productFeignClient.addNewProduct(product);
    }

    @Override
    public Product editProductById(Integer idProduct, Product product) {
        //log.info("Inside editCarById of UserService");
        Product updateProduct = this.productFeignClient.updateProductById(idProduct, product);
        return updateProduct;
    }

    @Override
    public void deleteProductById(Integer id) {
        //log.info("Inside editCarById of UserService");
        productFeignClient.deleteProductById(id);
    }

    @Override
    public List<Product> findAllProductsViaProductService() {
        //log.info("Inside editCarById of UserService");
        return productFeignClient.findAllProducts();
    }

    @Override
    public Product findProductById(Integer idProduct) {
        //log.info("Inside editCarById of UserService");
        return productFeignClient.findProductById(idProduct);
    }

    @Override
    public Product findProductByName(String name) {
        //log.info("Inside editCarById of UserService");
        return productFeignClient.findProductByName(name);
    }

    @Override
    public Map<String, Object> findAllCategoriesAndProducts(String categoryName) {
        Map<String, Object> response = new HashMap<>();
        Category category = categoryRepository.findCategoryByName(categoryName);
        if (category==null) {
            response.put("message", "Category does not exits with name:" + categoryName);
            return response;
        }
        response.put("Category", category);
        List<Product> products = productFeignClient.findAllProductsByCategoryName(categoryName);
        if (products.isEmpty())
            response.put("Product", "Sorry, No Almost Content!");
        else
            response.put("Product", products);
        return response;
    }

    private CategoryDto mapEntityToDto(Category category){
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    private void mapDtoToEntity(CategoryDto dto, Category category){
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setCreatedAt(dto.getCreatedAt());
        category.setUpdatedAt(dto.getUpdatedAt());
    }
}
