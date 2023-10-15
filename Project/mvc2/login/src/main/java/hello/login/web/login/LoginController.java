package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.Binding;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult){
        log.error("이게 호출 됨");
        return "login/loginForm";
    }

    @PostMapping("/login1")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail", "error at Id or PW");
            return "login/loginForm";
        }

        //쿠키에 만료 시간을 따로 지정하지 않으면 브라우저 꺼질 때까지 유지
        Cookie cookieId = new Cookie("memberID", String.valueOf(loginMember.getId()));
        response.addCookie(cookieId);

        return "redirect:/";    //로그인 성공
    }


    private final SessionManager sessionManager;

    @PostMapping("/login2")
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail", "error at Id or PW");
            return "login/loginForm";
        }

        //세션 관리자를 통해 세션 생성
        sessionManager.createSession(loginMember, response);

        return "redirect:/";    //로그인 성공
    }


    @PostMapping("/login3")
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail", "error at Id or PW");
            return "login/loginForm";
        }

        //세션이 있으면 있는 세션 사용, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인한 회원 정보 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        
        return "redirect:/";    //로그인 성공
    }


    @PostMapping("/login")  //loginCheckFilter 에서 보낸 요청을 이 메소드가 받음
    public String loginV4(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                          @RequestParam(name="redirectURL", defaultValue = "/") String redirectUrl,
                          HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail", "error at Id or PW");
            return "login/loginForm";
        }

        //세션이 있으면 있는 세션 사용, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인한 회원 정보 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        log.info("redirectURL = {}", redirectUrl );

        return "redirect:" + redirectUrl;    //로그인 성공
    }

    @PostMapping("/logout1")
    public String logout(HttpServletResponse response){
        mkLogoutCookie(response, "memberId");
        return "redirect:/";
    }


    @PostMapping("/logout2")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }

    private static void mkLogoutCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
