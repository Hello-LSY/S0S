package s0s.shop.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import s0s.shop.member.CustomUser;

import java.util.List;
import java.util.Map;

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
        model.addAttribute("size", size);

        return "product/ListForm";
    }


    @GetMapping("/detail/{productId}")
    public String productDetail(@PathVariable Long productId, Model model){
        ProductDTO productDTO = productService.findProductById(productId);
        model.addAttribute("productDetail", productDTO);

        return "product/detailForm";
    }

    @PostMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            // ProductService를 통해 해당 상품을 삭제
            productService.deleteProduct(productId);
            // 삭제가 성공하면 클라이언트에게 성공 메시지를 보냄
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            // 삭제 중에 오류가 발생하면 클라이언트에게 오류 메시지를 보냄
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }


}

