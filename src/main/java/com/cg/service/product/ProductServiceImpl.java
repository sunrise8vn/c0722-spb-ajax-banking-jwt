package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.model.dto.ProductResponseDTO;
import com.cg.repository.ProductMediaRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.upload.IUploadService;
import com.cg.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMediaRepository productMediaRepository;

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public List<ProductResponseDTO> findAllProductResponseDTOByDeleteIsFalse() {
        return productRepository.findAllProductResponseDTOByDeleteIsFalse();
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product create(ProductCreateDTO productCreateDTO) {

        Product product = productCreateDTO.toProduct();
        product.setId(null);
        product.setQuantity(0L);
        productRepository.save(product);

        String fileType = productCreateDTO.getFile().getContentType();

        assert fileType != null;

        fileType = fileType.substring(0, 5);

        ProductMedia productMedia = new ProductMedia();
        productMedia.setId(null);
        productMedia.setFileType(fileType);
        productMediaRepository.save(productMedia);

        uploadAndSaveProductImage(productCreateDTO, product, productMedia);

        return product;
    }

    private void uploadAndSaveProductImage(ProductCreateDTO productCreateDTO, Product product, ProductMedia productMedia) {
        try {
            Map uploadResult = uploadService.uploadImage(productCreateDTO.getFile(), uploadUtil.buildImageUploadParams(productMedia));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productMedia.setFileName(productMedia.getId() + "." + fileFormat);
            productMedia.setFileUrl(fileUrl);
            productMedia.setFileFolder(uploadUtil.IMAGE_UPLOAD_FOLDER);
            productMedia.setCloudId(productMedia.getFileFolder() + "/" + productMedia.getId());
            productMedia.setProduct(product);
            productMediaRepository.save(productMedia);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
