package s0s.shop.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final ProductService productService;

    @GetMapping("/upload")
    public String uploadForm() {
        return "product/uploadForm";
    }

    @PostMapping("/upload")
    public String uploadProduct(@Valid @ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("imageFiles") MultipartFile[] imageFiles,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal CustomUser customUser) {
        if (bindingResult.hasErrors()) {
            return "product/uploadForm";
        }

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
    public String productDetail(@PathVariable Long productId, Model model) {
        ProductDTO productDTO = productService.findProductById(productId);
        model.addAttribute("productDetail", productDTO);

        return "product/detailForm";
    }

    @PostMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

    @GetMapping("/edit/{productId}")
    public String editProductForm(@PathVariable Long productId, Model model) {
        ProductDTO productDTO = productService.findProductById(productId);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productId", productId);
        return "product/editForm";
    }

    @PostMapping("/edit/{productId}")
    public String updateProduct(@PathVariable Long productId,
                                @Valid @ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("imageFiles") MultipartFile[] imageFiles,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", productId);
            return "product/editForm";
        }

        productDTO.setId(productId);
        productService.updateProduct(productDTO, imageFiles);

        return "redirect:/product/detail/" + productId;
    }
}
