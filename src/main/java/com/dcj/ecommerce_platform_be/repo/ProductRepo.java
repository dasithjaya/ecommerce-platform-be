package com.dcj.ecommerce_platform_be.repo;

import com.dcj.ecommerce_platform_be.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
