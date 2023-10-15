package hello.springmvc.basic.requestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping("/hello-basic")
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }



    //url의 변수 값을 추출한는 @Pathvariable
    @GetMapping("/mapping/{userid}")
    public String mappingPath(@PathVariable("userid") String userId){

        log.info("mappingPath userid = {}", userId);
        return "ok";
    }

    //@Pathvariable의 key 생략
    @GetMapping("/mapping/test/{user}")
    public String mappingPath2(@PathVariable String user){
        log.info("mappingPath2 user = {} ", user);
        return "ok";
    }

    //열러 개의 값 사용 가능
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String MappingPath3(@PathVariable String userId, @PathVariable String orderId){
        log.info("userId = {} orderId = {}",userId, orderId);
        return "ok";
    }

    @GetMapping(value = "/hello/users/params", params="exParam")
    public String MappingParam(){
        log.info("exParma");
        return "ok";
    }

    @GetMapping(value = "/hello/users/params2", params="x=exParam")
    public String MappingParam2(){
        log.info("exParma");
        return "ok";
    }

    @PostMapping(value="/hello/users/headersX", headers="exHeaders" )
    public String MappingHeader2() {
        log.info("exHeaders");
        return "ok";
    }

    @PostMapping(value="/hello/users/headers", headers="x=exHeaders" )
    public String MappingHeader(){
        log.info("exHeaders");
        return "ok";
    }

    @PostMapping(value="hello/users/consumes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String MappingConsumes(){
        log.info("consumes");
        return "ok";
    }

    //이거 무조건 ok 나옴 왜?
    @PostMapping(value ="hello/users/produces", produces = MediaType.APPLICATION_JSON_VALUE)
    public String MappingProduces(){
        log.info("produces");
        return "ok";
    }


}
