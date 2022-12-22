package com.cg.service.productMedia;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface IProductMediaService extends IGeneralService<ProductMedia> {

    Optional<ProductMedia> findByProduct(Product product);
}
