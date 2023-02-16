package com.ndourcodeur.categorybackendservice.repository;


import com.ndourcodeur.categorybackendservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByName(String name);
    boolean existsCategoryByName(String name);
}
