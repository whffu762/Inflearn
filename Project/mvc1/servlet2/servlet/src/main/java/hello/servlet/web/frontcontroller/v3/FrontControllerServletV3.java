package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name ="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    public FrontControllerServletV3(){  //매핑 정보 저장, 요청 url과 컨트롤러 객체를 매핑
        controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reqURI = req.getRequestURI();

        ControllerV3 controller = controllerV3Map.get(reqURI);
        if(controller == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Map<String, String> paramMap = createMap(req);
        ModelView modelView = controller.process(paramMap);

        MyView view = viewResolver(modelView);

        view.render(modelView.getModel(), req, resp);


    }

    private static MyView viewResolver(ModelView modelView) {
        return new MyView("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
    }

    private static Map<String, String> createMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()    //여기서 parameter name을 뽑아서 iterator로 바꿈
                .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));   //paramName은 iterator의 값을 의미하는 변수
        return paramMap;
        
    }
}
