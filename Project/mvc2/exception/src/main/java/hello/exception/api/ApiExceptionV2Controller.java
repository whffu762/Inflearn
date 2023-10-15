package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @GetMapping("api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id){
        if(id.equals("ex")){
            throw new RuntimeException("wrong user");
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("wrong input");
        }
        if(id.equals("user-ex")){
            throw new UserException("user error");
        }

        return new MemberDto(id,"hello " + id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e){
        log.error("exceptionHandle : ", e);
        return new ErrorResult("bad", e.getMessage());
    }

    @ExceptionHandler
    //대상이 될 예외를 생략하면 메소드의 파라미터의 예외가 적용된다
    public ResponseEntity<ErrorResult> userExHandle(UserException e){
        log.error("exception :  ",e);
        ErrorResult errorResult = new ErrorResult("User-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e){
        log.error("exception : ", e);
        return new ErrorResult("ex", "내부 오류");
    }


    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }


}
