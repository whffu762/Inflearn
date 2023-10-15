package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;

@Slf4j
@Controller
//@ResponseBody class 단위로 붙이면 해당 클래스의 메소드(컨트롤러)에 전부 붙는다
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse resp) throws IOException{
        resp.getWriter().write("ok");
    }


    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3(){
        return "ok";
    }


    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("kim");

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("kim");

        return helloData;
    }



}
