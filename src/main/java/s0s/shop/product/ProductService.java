package s0s.shop.product;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface ProductService {

    @Transactional
    public void uploadProduct(ProductDTO productDTO, String nickname, MultipartFile[] imageFiles);

    @Transactional
    void updateProduct(ProductDTO productDTO, MultipartFile[] imageFiles);

    @Transactional
    void deleteProduct(Long productId);

    public List<Product> findAllProduct();

    public ProductDTO findProductById(Long productId);

    public Page<Product> findAllProduct(int page, int size);
}
