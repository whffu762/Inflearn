package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {


    @Test
    void uncheckedException(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw(){
        Service service = new Service();
        Assertions.assertThatThrownBy(()->service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    /**
     * RuntimeExcetpion 을 상속받은 예외 - 언체크 예외
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Service{
        Repository repository = new Repository();

        public void callCatch(){
            try{
                repository.call();
            }catch (MyUncheckedException e){
                log.info("예외 처리 Message = {}", e.getMessage(), e);
            }
        }

        // 예외를 따로 던지지 않아도 됨
        public void callThrow(){
            repository.call();
        }
    }

    static class Repository{
        public void call(){
            throw new MyUncheckedException("unchecked Ex");
        }
    }
}
