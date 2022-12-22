package com.cg.model;

import com.cg.model.dto.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(precision = 9, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long quantity;

    @Lob
    private String description;


    public ProductResponseDTO toProductResponseDTO(ProductMedia productMedia) {
        return new ProductResponseDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setMediaId(productMedia.getId())
                .setFileName(productMedia.getFileName())
                .setFileFolder(productMedia.getFileFolder())
                .setFileUrl(productMedia.getFileUrl())
                ;
    }

}
