package s0s.shop.product;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface ProductService {

    public void uploadProduct(ProductDTO productDTO, String nickname, MultipartFile[] imageFiles);
    public List<Product> findAllProduct();

    public ProductDTO findProductById(Long productId);

    public Page<Product> findAllProduct(int page, int size);

    void deleteProduct(Long productId);
}
