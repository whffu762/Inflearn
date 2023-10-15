package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");

        for(String mc : messageCodes){
            System.out.println("code = " + mc);
        }

        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodeResolverField(){
        String [] messageCodes = codesResolver.resolveMessageCodes("max", "item", "quantity", String.class);
        for(String x : messageCodes){
            System.out.println("code = " + x);
        }
    }


}
