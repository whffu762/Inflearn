package com.example.typeconv.type;


import lombok.EqualsAndHashCode;
import lombok.Getter;

//컨버터를 적용할 데이터 타입 예시
@Getter
@EqualsAndHashCode
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
}
