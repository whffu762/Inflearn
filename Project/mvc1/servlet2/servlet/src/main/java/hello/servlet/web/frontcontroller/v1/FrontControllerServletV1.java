package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name ="frontControllerServletV1", urlPatterns = "/front-controller/v1/*")   //관리할 컨트롤러가 공통적으로 가지는 url을 모두 요청으로 받음
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerV1Map = new HashMap<>();    //매핑 정보 저장을 위한 해쉬 맵

    public FrontControllerServletV1(){  //매핑 정보 저장, 요청 url과 컨트롤러 객체를 매핑
        controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members", new MemberListControllerV1());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //front-controller에서만 service()이용 나머지는 process()

        System.out.println("FrontControllerService");

        String reqURI = req.getRequestURI();    //req에서 url 뽑기
        ControllerV1 controller = controllerV1Map.get(reqURI);  //ControllerV1 레퍼런스 변수에 url에 해당하는 컨트롤러 객체 저장

        if(controller == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(req,resp);   //그 객체의 process()메소드 호출
    }
}
