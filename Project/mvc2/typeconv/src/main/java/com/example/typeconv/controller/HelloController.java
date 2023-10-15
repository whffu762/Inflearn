package com.example.typeconv.controller;


import com.example.typeconv.type.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    //수동 방식
    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request){
        String data = request.getParameter("data");
        Integer intValue = Integer.valueOf(data);
        System.out.println("intValue = " + intValue);
        return "ok";
    }

    //어노테이션은 converter service 가 자동으로 호출됨
    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data){

        System.out.println("intValue = " + data);
        return "ok2";
    }


    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort){
        System.out.println("ip port = " + ipPort.getIp());
        System.out.println("port" + ipPort.getPort());

        return "ok3";
    }
}
