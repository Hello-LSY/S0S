package s0s.shop.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String productName;
    @Column(nullable = false, length = 50)
    private String category;
    @Column(nullable = false, length = 100)
    private String registrantName;
    @Column(nullable = false)
    private String imagePath;
    @Column(nullable = false)
    private int price;
    private String description;

}
