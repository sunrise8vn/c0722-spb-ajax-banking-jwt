package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;

    private String mediaId;
    private String fileName;
    private String fileFolder;
    private String fileUrl;


}
