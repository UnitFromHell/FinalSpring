package com.example.itog.repositories;

import com.example.itog.models.SizeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<SizeProduct,Long> {
    boolean existsByName(double name);
}
