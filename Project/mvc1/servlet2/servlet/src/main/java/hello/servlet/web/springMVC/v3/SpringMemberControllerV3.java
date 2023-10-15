package hello.servlet.web.springMVC.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    //@RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String newFrom(){
        return "new-form";
    }

    //@RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            /**
             * String username = req.getPramter("username); - HttpServletRequest 객체로 받기
             * String username = paramMap.get("username);  - 넘겨진 HashMap 받아서 데이터 받기
                이 부분을 대체함
             */

            Model model){
        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);

        return "save-result";
    }

    @RequestMapping
    public String members(Model model){

        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);

        return "members";
    }


}
