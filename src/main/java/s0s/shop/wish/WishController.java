package s0s.shop.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/addWish")
    public ResponseEntity<String> addWish(@RequestParam Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        String result = wishService.toggleWish(productId, userDetails.getUsername());
        return ResponseEntity.ok(result);
    }
}