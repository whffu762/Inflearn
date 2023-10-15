package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {

    private String viewPath;

    //viewPath를 필드로 가짐
    public MyView(String viewPath){
        this.viewPath = viewPath;
    }

    //view로의 이동을 구현
    public void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, resp);
    }

    public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        modelToRequestAtt(model, req);
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, resp);
    }

    private static void modelToRequestAtt(Map<String, Object> model, HttpServletRequest req) {
        model.forEach((key, value)-> req.setAttribute(key, value));
    }
}
