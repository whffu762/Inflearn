package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

/*
    @GetMapping("/")
    public String home() {
        return "home";
    }
*/
/*
    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model){
        //로그인을 못한 사용자
        if(memberId == null){
            return "home";
        }

        //쿠키로 받은 ID를 이용해서 사용자 조회
        Member loginMember = memberRepository.findById(memberId);

        //로그인 성공했는데 쿠키가 오래전에 발급된 경우
        //로그인이 상태가 오래된 경우
        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }*/

    private final SessionManager sessionManager;

 /*   @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model){
        //세션 관리자에 저장된 정보 조회
        Member member = (Member)sessionManager.getSession(request);

        if(member == null){
            return "home";
        }

        model.addAttribute("member", member);
        return "loginhome";
    }
*/
/*

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model){
        //세션 관리자에 저장된 정보 조회
        HttpSession session = request.getSession(false);

        if(session == null){
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(loginMember == null){
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginhome";
    }
*/



 /*   @GetMapping("/")
    public String homeLogin(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member, Model model){

        if(member == null){
            return "home";
        }

        model.addAttribute("member", member);
        return "loginhome";
    }
*/

    @GetMapping("/")
    public String homeLoginArgumentResolver(@Login Member member, Model model){

        if(member == null){
            return "home";
        }

        model.addAttribute("member", member);
        return "loginhome";
    }

}