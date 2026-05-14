package com.duoc.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.product.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{

    List<ProductModel> findByActivoTrue();

    List<ProductModel> findByCategoriaAndActivoTrue(String categoria);


}
