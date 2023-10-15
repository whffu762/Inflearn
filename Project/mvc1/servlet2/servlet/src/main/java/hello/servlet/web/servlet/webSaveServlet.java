package hello.servlet.web.servlet;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class webSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance(); //싱글톤

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MemberSaveServlet.service");

        String username = req.getParameter("username");
        int age = Integer.parseInt(req.getParameter("age"));    //넘어오는 age는 문자열이기 때문에 int형으로 변환

        Member member = new Member(username, age);
        memberRepository.save(member);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        PrintWriter w = resp.getWriter();
        w.write("<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                " <li>id="+member.getId()+"</li>\n" +
                " <li>username="+member.getUsername()+"</li>\n" +
                " <li>age="+member.getAge()+"</li>\n" +
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");

    }
}
