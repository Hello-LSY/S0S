package s0s.shop.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/uploadForm";
        }

        // 이미지 파일들과 함께 상품 정보를 서비스로 전달하여 상품을 업로드
        productService.uploadProduct(productDTO, imageFiles);

        return "redirect:/";
    }

}

