package hello.exception.exhandler.advice;

import hello.exception.api.ApiExceptionV2Controller;
import hello.exception.api.ApiExceptionV3Controller;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//api 하위 패키지에 있는 모든 컨트롤러 적용됨
//@RestControllerAdvice("hello.exception.api")

//V3에서만 적용됨
@RestControllerAdvice(assignableTypes = ApiExceptionV3Controller.class )
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("exceptionHandler ex ", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("exceptionHandler ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    //Exception은 최상위 예외임 그래서 위에서 놓친 예외를 전부 받아서 처리해줌
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("exceptionHandler ex", e);
        return new ErrorResult("ex", "internal error");
    }


}
