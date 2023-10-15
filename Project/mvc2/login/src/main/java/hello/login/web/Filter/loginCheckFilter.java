package hello.login.web.Filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//실제 로그인에 대한 처리를 위한 필터
@Slf4j
public class loginCheckFilter implements Filter {

    private static final String [] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("인증 체크 필터 시작 {}", requestURI);

            if(isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);

                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청 {} ", requestURI);
                    //로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);  //LoginController 로 요청을 보냄
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e){
            throw e;
            //예외 로깅 가능하지만 톰켓까지 예외를 보내줘야 함
        } finally {
            log.info("체크 필터 종료");
        }
    }

    //whiteList 안에 있는 url 인지 확인
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }


}
