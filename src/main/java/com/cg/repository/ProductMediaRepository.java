package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductMediaRepository extends JpaRepository<ProductMedia, String> {

    Optional<ProductMedia> findByProduct(Product product);
}
