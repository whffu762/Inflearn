package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns =  "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //상태코드 지정 ()에 200이라고 직접 넣을 수도 있는데 HttpServletResponse를 이용하는게 더 좋음(사실 큰 상관 없음)
        resp.setStatus(HttpServletResponse.SC_OK);
        //resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400


        //header의 각 속성을 지정(key value 형식으로 지정)
        //content-type 지정
        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello");

        //cookie 지정
        resp.setHeader("Set-Cookie", "myCookie=good;Max-Age=600");

        //redirect 지정
        resp.setStatus(HttpServletResponse.SC_FOUND);   //상태 코드가 300일때만 redirect됨
        resp.setHeader("Location", "/basic/hello-form.html");   //redirect 될 페이지 지정

        //header 편의 메소드
        /**
         content(resp);
         cookie(resp);
        redirect(resp);
**/
        //message body
        PrintWriter writer = resp.getWriter();
        writer.println("ok");
    }


    //resp.setHeader()를 이용한 content-type 지정을 대체하는 메소드

    private void content(HttpServletResponse resp){
        resp.setContentType("text/plain");  //resp.setHeader("Content-Type", "text/plain")을 대체
        resp.setCharacterEncoding("utf-8");
        //resp.setContentLength(2); body content의 길이를 지정(생략하면 입력되는 값 알아서 계산해서 지정됨)
    }


    //resp.setHeader()를 이용한 cookie 지정을 대체하는 메소드

    private void cookie(HttpServletResponse resp){
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);  //600초 동안 유지됨
        resp.addCookie(cookie);
    }


    //resp.setHeader()를 이용한 redirect를 대체하는 메소드
    private void redirect(HttpServletResponse resp) throws IOException{
        resp.sendRedirect("/basic/hello-form.html");
    }


}
