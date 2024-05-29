package s0s.shop.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import s0s.shop.wish.Wish;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String productName;

    @Column(nullable = false, length = 50)
    private String category;

    // 나중에 관계 설정해주고 판매자의 닉네임으로 매칭시키기
    @Column(nullable = false, length = 100)
    private String writerName;

    @Column(nullable = false)
    private int price;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private String description;

    // 여러 이미지 URL을 저장하는 리스트
    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();

    // Product 엔티티
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes = new ArrayList<>();

    @Builder
    public Product(String productName, String category, String writerName, int price, String description) {
        this.productName = productName;
        this.category = category;
        this.writerName = writerName;
        this.price = price;
        this.description = description;
    }

    public void updateProduct(String productName, String category, int price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    // 게시글에 이미지 URL 추가
    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }
    public void clearImageUrls() {
        this.imageUrls.clear();
    }

}

