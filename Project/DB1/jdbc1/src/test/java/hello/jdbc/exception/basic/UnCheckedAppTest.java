package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void checked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(()->controller.request())
                .isInstanceOf(RuntimeSqlException.class);
    }

    @Test
    void printEx(){
        Controller controller = new Controller();
        try{
            controller.request();
        } catch (Exception e){
            log.info("ex", e);
        }
    }
    
    /**
        컨트롤러와 서비스에서 던지는 예외를 따로 선언하지 않음
     */
    static class Controller{
        Service service = new Service();
        public void request(){
            service.logic();
        }
    }
    static class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic(){
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient{
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository{
        public void call() {
            try{
                runSql();
            }catch (SQLException e){
                throw new RuntimeSqlException(e);
            }
        }

        public void runSql() throws SQLException{
            throw new SQLException("SQL EX");
        }
    }

    static class RuntimeConnectException extends RuntimeException{
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSqlException extends RuntimeException{
        public RuntimeSqlException(Throwable cause) {
            super(cause);
        }
    }
}
