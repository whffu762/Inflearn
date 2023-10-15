package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw(){
        Service service = new Service();
        Assertions.assertThatThrownBy(()-> service.callThrows())
                .isInstanceOf(MyCheckException.class);
    }

    static class MyCheckException extends Exception{
        public MyCheckException(String message){
            super(message);
        }
    }

    static class Service{
        Repository repository = new Repository();

        /**
            예외를 잡아서 처리
         */
        public void callCatch(){
            try {
                repository.call();
            } catch (MyCheckException e) {
                    log.info("예외 처리, Message = {}",e.getMessage(),e);
                }
            }
            public void callThrows() throws MyCheckException{
                repository.call();
            }
        }

    static class Repository{
        public void call() throws MyCheckException {
            throw new MyCheckException("ex");
        }
    }
}
