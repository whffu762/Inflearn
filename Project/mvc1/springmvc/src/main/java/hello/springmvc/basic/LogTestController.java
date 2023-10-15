package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(getClass()); //logger에 내 클래스를 지정해줘야 함

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "spring";

        System.out.println("name = " + name);   //console에 단순 문자열로 나옴
        
        //이런식으로 log의 레벨을 지정할 수 있음
        log.trace("trace log = {}", name);
        //log.trace("trace log = " + name); 이런식으로도 사용 가능한데.. 쓰면 안됨
        
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);
        
        return "ok";
    }
    
    
}
