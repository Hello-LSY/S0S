package s0s.shop.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping("/addWish")
    public String addWish(@RequestParam Long productId, @AuthenticationPrincipal UserDetails userDetails){
        wishService.addWish(productId, userDetails.getUsername());

        return "redirect:/product/detail/" + productId;
    }

}
