package hello.servlet.basic.request;

import org.apache.coyote.http11.Http11InputBuffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
//urlPatterns가 이 서블릿을 호출하는 url 명시함
//name은 이 웹 서블릿의 이름을 명시
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //service하면 자동 완성 되는거 있음 그중에서 protected로 선택하면 됨 직접 다 치는 것 보다 그게 낫다

        System.out.println("service on");
        //이걸로 서블릿이 제대로 작동하는지 확인할 수 있음 url로 서블릿 호출했을 때 이게 찍히면 정상 작동


        //여기는 header에 있는 request의 정보를 뽑을 수 있는 각종 함수가 있는데 ppt에 있음 
    }
}
