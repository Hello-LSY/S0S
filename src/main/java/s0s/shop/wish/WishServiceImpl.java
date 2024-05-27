package s0s.shop.wish;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import s0s.shop.member.Member;
import s0s.shop.member.MemberRepository;
import s0s.shop.product.Product;
import s0s.shop.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService{

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public void addWish(Long productId, String username){

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("찜 오류 : 해당 ID의 회원이 존재하지 않습니다."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("찜 오류 : 해당 ID의 상품이 존재하지 않습니다."));

        Wish wish = Wish.builder()
                .member(member)
                .product(product)
                .build();

        wishRepository.save(wish);
    }
}
