package com.example.itog.repositories;

import com.example.itog.models.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<TypeProduct,Long> {
    boolean existsByName(String category);
}
