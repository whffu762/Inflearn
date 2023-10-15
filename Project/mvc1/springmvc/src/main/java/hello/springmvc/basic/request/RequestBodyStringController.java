package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();  //Stream에서 byte코드 받기
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); //byte를 string으로 바꿔주는 방식

        log.info("message body={}", messageBody);

        resp.getWriter().write("ok");   //응답 보내기
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream req, Writer resp) throws IOException {  //InputStream을 파라미터로 받을 수 있음

        String messageBody = StreamUtils.copyToString(req, StandardCharsets.UTF_8); //그래서 메소드 안에선 String으로 바꿔주기만 하면됨
        log.info("message body={}", messageBody);
        resp.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {   //String으로 바꿔서 HttpEntity에 저장해줌

        //그래서 HttpEntity에서 꺼내쓰기만 하면 됨
        String messageBody = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();

        log.info("message body={}", messageBody);
        return new HttpEntity<>("ok");
    }

    @PostMapping("/request-body-string-v4")
    public HttpEntity<String> requestBodyStringV4(RequestEntity<String> reqEntity) throws IOException { //HttpEntity의 역할을 Req와 Resp로 분할해줌

        String messageBody = reqEntity.getBody();
        HttpHeaders headers = reqEntity.getHeaders();

        log.info("message body={}", messageBody);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/request-body-string-v5")
    public String requestBodyStringV5(@RequestBody String messageBody) throws IOException { //HttpEntity를 String으로 바꿔줌

        log.info("message body={}", messageBody);

        return "ok";
    }
}
