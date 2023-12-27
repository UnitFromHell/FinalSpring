package com.example.itog.repositories;

import com.example.itog.models.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryProduct,Long> {
    boolean existsByName(String category);
}
