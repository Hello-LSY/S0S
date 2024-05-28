package s0s.shop.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import s0s.shop.wish.Wish;
import s0s.shop.wish.WishRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    final private ProductRepository productRepository;
    final private WishRepository wishRepository;
    final private ResourceLoader resourceLoader;

    @Value("${image.upload.directory}")
    String imageUploadDirectory;

    @Override
    @Transactional
    public void uploadProduct(ProductDTO productDTO, String nickname, MultipartFile[] imageFiles) {
        if (productDTO == null) {
            throw new IllegalArgumentException("상품정보가 유효하지 않습니다.");
        }

        // ProductDTO로부터 상품 정보를 추출하여 Product 엔티티를 생성
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .category(productDTO.getCategory())
                .writerName(nickname)
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .build();

        // 이미지 파일 처리
        if (imageFiles != null && imageFiles.length > 0) {
            for (MultipartFile imageFile : imageFiles) {
                // 각 이미지 파일을 저장하고 저장된 파일의 URL을 추출하여 Product 엔티티에 추가
                String savedImageUrl = saveImageFile(imageFile);
                product.addImageUrl(savedImageUrl);
            }
        }

        // 상품을 저장
        productRepository.save(product);
    }

    private String saveImageFile(MultipartFile imageFile) {
        // UUID를 사용하여 파일 이름 생성
        String uuid = UUID.randomUUID().toString();
        String fileExtension = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
        String savedFilename = uuid + fileExtension;

        try {
            // 이미지 파일을 저장할 경로 설정
            Resource resource = resourceLoader.getResource(imageUploadDirectory);
            File uploadDir = resource.getFile();
            Path uploadPath = Paths.get(uploadDir.getAbsolutePath(), savedFilename);

            // 이미지 파일 저장
            Files.copy(imageFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            // 저장된 이미지 파일의 경로를 반환
            return savedFilename;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 파일을 저장하는 중에 오류가 발생했습니다.", e);
        }
    }
    @Override
    public List<Product> findAllProduct(){
        List<Product> productList = productRepository.findAll();
        Collections.reverse(productList);
        return productList;
    }

    @Override
    public ProductDTO findProductById(Long productId){
         Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 아이디에 해당하는 상품을 찾을 수 없습니다."));

        return convertToProductDTO(product);
    }

    //페이징처리된 상품리스트
    @Override
    public Page<Product> findAllProduct(int page, int size) {
        //등록날짜 DESC 페이징
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return productRepository.findAll(pageRequest);
    }

    private ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .category(product.getCategory())
                .writerName(product.getWriterName())
                .price(product.getPrice())
                .createdDate(product.getCreatedDate())
                .description(product.getDescription())
                .imageUrls(product.getImageUrls())
                .build();
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 아이디에 해당하는 상품을 찾을 수 없습니다."));

        // 해당 상품의 모든 찜을 삭제
        wishRepository.deleteAll(product.getWishes());
        // 상품 삭제
        productRepository.deleteById(productId);
    }


}
