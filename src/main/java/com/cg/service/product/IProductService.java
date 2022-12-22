package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.model.dto.ProductResponseDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {

    List<ProductResponseDTO> findAllProductResponseDTOByDeleteIsFalse();

    Product create(ProductCreateDTO productCreateDTO);
}
