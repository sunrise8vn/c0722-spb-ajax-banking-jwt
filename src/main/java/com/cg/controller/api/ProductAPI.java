package com.cg.controller.api;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.model.dto.ProductResponseDTO;
import com.cg.service.product.IProductService;
import com.cg.service.productMedia.IProductMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductMediaService productMediaService;


    @GetMapping
    public ResponseEntity<?> getAllProducts() {

        List<ProductResponseDTO> productResponseDTOS = productService.findAllProductResponseDTOByDeleteIsFalse();

        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(ProductCreateDTO productCreateDTO) {

        productCreateDTO.setId(null);
        productCreateDTO.setQuantity("0");
        Product newProduct = productService.create(productCreateDTO);

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        Optional<ProductMedia> productMediaOptional = productMediaService.findByProduct(newProduct);

        if (!productMediaOptional.isPresent()) {
            ProductMedia productMedia = new ProductMedia();
            productMedia.setId(null);
            productMedia.setFileName(null);
            productMedia.setFileFolder(null);
            productMedia.setFileUrl(null);

            productResponseDTO = newProduct.toProductResponseDTO(productMedia);
        }
        else {
            ProductMedia productMedia = productMediaOptional.get();
            productResponseDTO = newProduct.toProductResponseDTO(productMedia);
        }

        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }
}
