package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
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
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJonV1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);     //String으로 바꾼 Json 데이터의 key와 각 class의 필드를 매핑함
        log.info("username={} age={}", helloData.getUsername(), helloData.getAge());    //class로 접근 가능함

        resp.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v1-2")
    public String requestBodyJonV1(HttpEntity<HelloData> HttpData) throws IOException {

        log.info("messageBody={}", HttpData);

        HelloData hellodata = HttpData.getBody();   //JSON에선 HTTP로 받을 때 클래스 형식으로 바로 매핑하면서 받을 수 있음

        log.info("username={} age={}", hellodata.getUsername(), hellodata.getAge());    //class로 접근 가능함

        return "ok";
    }




    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJonV2(@RequestBody String messageBody) throws IOException {    //마찬가지로 HttpEntity를 String으로 바꿔줌

        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={} age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJonV3(@RequestBody HelloData helloData) throws IOException {   //String으로 바꾼걸 매핑을 알아서 해줌

        log.info("username={} age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public HelloData requestBodyJonV4(@RequestBody HelloData helloData) throws IOException {   //반환형을 HelloData로도 할 수 있음 알아서 JSON으로 바뀌어서 응답 메시지 바디에 들어감

        log.info("username={} age={}", helloData.getUsername(), helloData.getAge());

        return helloData;
    }


    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData [] requestBodyJonV5(@RequestBody HelloData [] helloData) throws IOException {   //여러 개의 Json(json array)를 받을 땐 DTO의 배열로 하면 됨

        for(HelloData d : helloData){
            log.info("username={} age={}", d.getUsername(), d.getAge());
        }

        return helloData;
    }

}
