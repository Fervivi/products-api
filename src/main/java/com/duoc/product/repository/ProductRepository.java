package com.duoc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.product.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{
    

}
