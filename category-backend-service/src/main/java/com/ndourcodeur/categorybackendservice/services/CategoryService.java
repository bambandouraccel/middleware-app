package com.ndourcodeur.categorybackendservice.services;

import com.ndourcodeur.categorybackendservice.dto.CategoryDto;
import com.ndourcodeur.categorybackendservice.payload.Product;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<CategoryDto> findCategories();
    CategoryDto findCategory(Integer idCategory);
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto editCategory(Integer idCategory, CategoryDto categoryDto);
    void deleteCategory(Integer idCategory);

    // Feign Client ===> Middleware
    public Product saveProduct(Product product);

    public Product editProductById(Integer idProduct, Product product);
    public void deleteProductById(Integer id);

    public List<Product> findAllProductsViaProductService();
    public Product findProductById(Integer idProduct);
    public Product findProductByName(String name);
    public Map<String, Object> findAllCategoriesAndProducts(String categoryName);

}
