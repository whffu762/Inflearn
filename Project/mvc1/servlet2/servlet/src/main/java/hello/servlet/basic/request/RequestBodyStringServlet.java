package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * API 방식 - 데이터를 body에 직접 넣어서 전송
 * 단순 텍스트 전달
 */
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();  //messageBody 내용을 byte 코드로 리턴
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); //byte 코드를 String으로 변환해서 리턴

        System.out.println("messageBody = " + messageBody);

        resp.getWriter().write("ok");   //console에 찍히는 로그

    }
}
