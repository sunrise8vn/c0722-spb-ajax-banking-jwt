package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.cg.model.dto.ProductResponseDTO (" +
                "p.id, " +
                "p.title, " +
                "p.price, " +
                "p.quantity, " +
                "p.description, " +
                "pm.id, " +
                "pm.fileName, " +
                "pm.fileFolder, " +
                "pm.fileUrl " +
            ") " +
            "FROM Product AS p " +
            "JOIN ProductMedia AS pm " +
            "ON pm.product = p " +
            "AND p.deleted = FALSE "
    )
    List<ProductResponseDTO> findAllProductResponseDTOByDeleteIsFalse();
}
