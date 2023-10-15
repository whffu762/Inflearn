package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest req, HttpServletResponse resp, Object Handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) Handler;

        Map<String, String> paramMap = createMap(req);
        HashMap<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);  //여기까지가 V4의 역할

        //V4는 String을 반환하기 때문에 modelView로 만들어서 보내주는게 adapter의 역할
        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);

        return modelView;
    }

    private static Map<String, String> createMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
}
