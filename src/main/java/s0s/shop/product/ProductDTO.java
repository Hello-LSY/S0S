package s0s.shop.product;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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

    @Builder
    public ProductDTO(Long id, String productName, String category, String writerName, int price, LocalDateTime createdDate, String description, List<String> imageUrls) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.writerName = writerName;
        this.price = price;
        this.createdDate = createdDate;
        this.description = description;
        this.imageUrls = imageUrls;
    }
}
