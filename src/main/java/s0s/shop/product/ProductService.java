package s0s.shop.product;

import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface ProductService {

    public void uploadProduct(ProductDTO productDTO, MultipartFile[] imageFiles);
}
