package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.RequestDispatcher.*;


@Slf4j
@Controller
public class ErrorPageController {



    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-p/404";
    }


    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response){
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-p/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(
            HttpServletRequest request, HttpServletResponse response){

        log.info("API errorPage 500");
        Map<String, Object> result = new HashMap<>();

        Integer statusCode = (Integer) request.getAttribute(ERROR_STATUS_CODE);
        result.put("status", statusCode);

        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("message", ex.getMessage());


        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }



    private void printErrorInfo(HttpServletRequest request){
        log.info("Error Exception = {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("Error Exception type = {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("Error message = {}", request.getAttribute(ERROR_MESSAGE));
        log.info("Error request uri = {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("Error status name = {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("Error status code = {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatcher type = {}", request.getDispatcherType());



    }
}
