package s0s.shop.wish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s0s.shop.member.Member;
import s0s.shop.product.Product;

import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndProduct(Member member, Product product);
    void deleteByMemberAndProduct(Member member, Product product);
}
