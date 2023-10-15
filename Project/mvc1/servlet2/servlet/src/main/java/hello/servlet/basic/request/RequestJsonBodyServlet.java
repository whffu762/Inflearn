package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
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
 * API 방식(JSON)
 * 단순히 데이터를 읽는건 텍스트의 방식과 동일함
 */
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestJsonBodyServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper(); //json 형식의 데이터를 helloData class로 변환해주는 객체
    //class의 필드명과 json의 key 값이 동일해야 함

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletInputStream inputStream = req.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); //byte 코드를 String으로 변환해서 리턴

        System.out.println("messageBody = " + messageBody);



        HelloData hellodata = objectMapper.readValue(messageBody, HelloData.class); //messageBody(json)을 HelloData.class(class)형식으로 변환

        System.out.println("helloData.username = " + hellodata.getUsername());
        System.out.println("helloData.age = " + hellodata.getAge());
        //json의 데이터를 class에 넣어놨기 때문에 get()으로 데이터에 접근 가능
        
        resp.getWriter().write("ok");
    }
}
