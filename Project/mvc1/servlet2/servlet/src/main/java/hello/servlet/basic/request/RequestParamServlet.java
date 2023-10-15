package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?usernaem=hello&age=20
 * GET과 POST 요청의 데이터를 받는 방식이 동일함
 */

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("전체 파라미터 조회 - start");

        req.getParameterNames().asIterator()    //req.getParamNames(); 전달된 쿼리 파라미터의 모든 key를 뽑음  asIterator(); 그 key들을 순회 가능한 iterator로 변환해서 리턴
                .forEachRemaining(paramName -> System.out.println(paramName +"=" + req.getParameter(paramName)));
        //forEachRemaining(); 모든 요소가 처리되거나 예외가 발생할 때까지 ()안의 작업을 수행함
        //getParameter(x); req의 메소드로 x에 해당하는 value를 리턴 x는 key

        System.out.println("전체 파라미터 조회 - end \n");


        System.out.println("단일 파라미터 조회 - start");

        String name = req.getParameter("username");
        String age = req.getParameter("age");
        //각각 변수 선언하고 그 변수에 파라미터 값을 넣는 방식으로 파라미터 추출

        System.out.println("username = " + name);
        System.out.println("age = " + age);
        System.out.println("단일 파라미터 조회 - end \n");


        System.out.println("이름이 같은 복수 파라미터 조회");    //이런 경우는 잘 없음
        String[] usernames = req.getParameterValues("username");
        //getParameterValues(x); key x에 해당하는 모든 value를 배열에 반환
        //하나의 key에 여러 value가 있을 때 이걸 써야함
        //getParmater()는 쓰면 안됨 쓰면 맨 처음 value만 출력됨
        for (String username : usernames) {
            System.out.println("username = " + username);
        }
        //위 for문과 아래 for문이 같은 의미임
        for(int i=0;i<usernames.length;i++){
            System.out.println("username = " + usernames[i]);

            //위 방식은 GET의 쿼리 파라미터를 받는 방식이지만 POST html-form 방식으로 전달하는 데이터도 받을 수 있음
        }
    }

}