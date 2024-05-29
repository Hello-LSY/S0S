package s0s.shop.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String productName;
    private String category;
    private String writerName;
    private int price;
    private LocalDateTime createdDate;
    private String description;
    private List<String> imageUrls;
    private MultipartFile[] imageFiles;  // 이미지 파일을 추가

    @Builder
    public ProductDTO(Long id, String productName, String category, String writerName, int price, LocalDateTime createdDate, String description, List<String> imageUrls, MultipartFile[] imageFiles) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.writerName = writerName;
        this.price = price;
        this.createdDate = createdDate;
        this.description = description;
        this.imageUrls = imageUrls;
        this.imageFiles = imageFiles;  // 이미지 파일 초기화
    }


    //이 두가지 부분을 매퍼로 빼야할지 고민

    // 엔티티로 변환하는 메소드
    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .category(this.category)
                .writerName(this.writerName)
                .price(this.price)
                .description(this.description)
                .build();
    }

    // 엔티티로부터 DTO를 생성하는 메소드
    public static ProductDTO fromEntity(Product product) {
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
}
