package com.cg.service.productMedia;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.repository.ProductMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductMediaServiceImpl implements IProductMediaService {

    @Autowired
    private ProductMediaRepository productMediaRepository;

    @Override
    public List<ProductMedia> findAll() {
        return null;
    }

    @Override
    public Optional<ProductMedia> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductMedia getById(Long id) {
        return null;
    }

    @Override
    public Optional<ProductMedia> findByProduct(Product product) {
        return productMediaRepository.findByProduct(product);
    }

    @Override
    public ProductMedia save(ProductMedia productMedia) {
        return null;
    }

    @Override
    public void delete(ProductMedia productMedia) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
