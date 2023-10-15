package com.example.typeconv;

import com.example.typeconv.converter.IntegerToStringConverter;
import com.example.typeconv.converter.IpPortToStringConverter;
import com.example.typeconv.converter.StringToIntegerConverter;
import com.example.typeconv.converter.StringToIpPortConverter;
import com.example.typeconv.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        //우선 순위 때문에 주석
        //주석 처리한 이유 : StringToInteger와 IntegerToString 도 MyNumberFormatter도 문자 객체 변환이기 때문에
        //기능이 중복됨 호출할 때 converter가 우선순위가 높아서 formatter 적용 안됨
        //registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new StringToIpPortConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new IpPortToStringConverter());
        
        
        //formatter 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
