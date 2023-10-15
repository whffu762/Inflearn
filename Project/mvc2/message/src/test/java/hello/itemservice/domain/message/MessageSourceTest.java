package hello.itemservice.domain.message;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage(){
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode(){
        assertThatThrownBy(()->messageSource.getMessage("nope", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCode2(){
        String result = messageSource.getMessage("nope" ,null,"default",null);
        assertThat(result).isEqualTo("default");
    }

    @Test
    void argumentMessage(){
        String message = messageSource.getMessage("hello.name", new Object[] {"spring"}, null);
        assertThat(message).isEqualTo("안녕 spring");
    }

    @Test
    void defaultLang(){
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        assertThat(messageSource.getMessage("hello",null, Locale.ENGLISH)).isEqualTo("hello");
    }
}
