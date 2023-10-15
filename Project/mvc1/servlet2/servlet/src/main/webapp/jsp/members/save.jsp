<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %><%--
  Created by IntelliJ IDEA.
  User: whffu
  Date: 2023-02-01
  Time: 오후 8:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    //request, response 는 그냥 사용 가능(req, resp는 안됨)
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));    //넘어오는 age는 문자열이기 때문에 int형으로 변환

    Member member = new Member(username, age);
    memberRepository.save(member);
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
success
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
