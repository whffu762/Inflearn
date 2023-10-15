package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV1 {
    //각 컨트롤러의 서로 다른 처리를 위한 process()메소드
    //각 process() 메소드의 구현 내용은 컨트롤러마다 다름
    void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
