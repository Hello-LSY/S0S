//package s0s.shop.file;
//
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import s0s.shop.product.Product;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class UploadFile {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String imageUrl;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="product_id")
//    private Product product;
//
//    @Builder
//    public UploadFile(String imageUrl, Product product) {
//        this.imageUrl = imageUrl;
//        this.product = product;
//    }
//}
