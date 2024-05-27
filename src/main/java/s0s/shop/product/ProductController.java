package s0s.shop.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import s0s.shop.member.CustomUser;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService; // UploadFileServiceImpl 대신 ProductService로 변경
    @GetMapping("/upload")
    public String uploadForm(){
        return "product/uploadForm";
    }

    @PostMapping("/upload")
    public String uploadProduct(@Valid @ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("imageFiles") MultipartFile[] imageFiles,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal CustomUser customUser
    ) {
        if (bindingResult.hasErrors()) {
            return "product/uploadForm";
        }

        // 이미지 파일들과 함께 상품 정보를 서비스로 전달하여 상품을 업로드
        productService.uploadProduct(productDTO, customUser.getNickname(), imageFiles);

        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productList(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.findAllProduct(page, size);
        List<Product> productList = productPage.getContent();

        model.addAttribute("productList", productList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "product/ListForm";
    }

    @GetMapping("/detail/{productId}")
    public String productDetail(@PathVariable Long productId, Model model){
        ProductDTO productDTO = productService.findProductById(productId);
        model.addAttribute("productDetail", productDTO);

        return "product/detailForm";
    }

}

