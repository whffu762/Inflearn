package com.example.typeconv.formatter;

import com.example.typeconv.converter.IpPortToStringConverter;
import com.example.typeconv.converter.StringToIntegerConverter;
import com.example.typeconv.converter.StringToIpPortConverter;
import com.example.typeconv.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService(){
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addFormatter(new MyNumberFormatter());

        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1",8080));

        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");

        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);


    }
}
