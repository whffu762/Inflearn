package hello.springmvc.basic.requestMapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping //RequestMapping에서 모두 묶였으면 안적어도 됨
    public String user(){
        return "get users";
    }

    @PostMapping
    public String addUser(){
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId){

        return "get userId" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable("userId") String user){
        return "update " + user;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete" + userId;
    }
}
