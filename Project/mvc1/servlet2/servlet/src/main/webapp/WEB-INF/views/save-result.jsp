<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
success
<ul>
  <li>id=<%=((Member)request.getAttribute("member")).getId()%></li> <!-- request.getAttribute()의 반환형이 Object라서 뒤에 .getId() 쓰려면 형변환 해줘야 함 -->
  <li>username=${member.username}</li>  <!-- 이런 식으로도 접근할 수 있음 더 간편한 방법 -->
  <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>

