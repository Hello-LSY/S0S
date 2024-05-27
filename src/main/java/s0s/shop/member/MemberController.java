package s0s.shop.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;


    @GetMapping("/signup")
    public String signUpForm(){
        return "member/signForm";
    }

    @PostMapping("/signup")
    public String signUpMember(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "member/signForm";
        }

        memberService.join(memberDTO);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "member/loginForm";
    }

}
