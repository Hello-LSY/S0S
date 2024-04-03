package s0s.shop.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    final private ProductRepository productRepository;

    @Value("${image.upload.directory}")
    String imageUploadDirectory;

    @Override
    @Transactional
    public void uploadProduct(ProductDTO productDTO, MultipartFile[] imageFiles) {
        if (productDTO == null) {
            throw new IllegalArgumentException("상품정보가 유효하지 않습니다.");
        }

        // ProductDTO로부터 상품 정보를 추출하여 Product 엔티티를 생성
        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .category(productDTO.getCategory())
                .writerName(productDTO.getWriterName())
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

        // 이미지 파일을 저장할 경로 설정
        Path uploadPath = Paths.get(imageUploadDirectory, savedFilename);

        // 이미지 파일 저장
        try {
            Files.copy(imageFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 파일을 저장하는 중에 오류가 발생했습니다.", e);
        }

        // 저장된 이미지 파일의 경로를 반환
        return savedFilename;
    }


}
