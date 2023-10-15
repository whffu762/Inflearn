package hello.thymeleaf.basic;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data" , "<b>hello</b>");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String utextBasic(Model model){
        model.addAttribute("udata" , "<b>hello</b>");
        return "basic/utext-basic";
    }

    @GetMapping("/variable")
    public String variable(Model model){

        User userA = new User("UserA", 10);
        User userB = new User("UserB", 20);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObject(HttpSession session){
        session.setAttribute("sessionData", "hello Session");

        return "basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());

        return "basic/date";
    }

    @GetMapping("link")
    public String link(Model model){
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");

        return "basic/link";
    }

    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data", "spring");

        return "/basic/literal";
    }


    @GetMapping("/operation")
    public String operation(Model model){
        model.addAttribute("nullData", null);
        model.addAttribute("data", "spring");

        return "basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute(){

        return "basic/attribute";
    }

    private static List<User> addUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("UserA", 10));
        users.add(new User("UserB", 20));
        users.add(new User("UserC", 30));
        users.add(new User("UserD", 40));
        return users;
    }

    @GetMapping("each")
    public String each(Model model){
        List<User> users = addUsers();

        model.addAttribute("users", users);


        return "basic/each";
    }



    @GetMapping("/condition")
    public String condition(Model model){
        List<User> users = addUsers();

        model.addAttribute("users", users);

        return "basic/condition";
    }

    @GetMapping("/comments")
    public String comments(Model model){
        model.addAttribute("data", "Spring");
        return "basic/comments";
    }

    @GetMapping("/block")
    public String block(Model model){
        List<User> users = addUsers();
        model.addAttribute("users", users);

        return "/basic/block";
    }

    @GetMapping("/javascript")
    public String inline(Model model){
        List<User> users = addUsers();
        model.addAttribute("users", users);
        model.addAttribute("user",new User("user\"A\"", 10));

        return "basic/javascript";
    }
}
