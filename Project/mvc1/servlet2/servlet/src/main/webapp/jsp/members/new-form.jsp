<%--
  Created by IntelliJ IDEA.
  User: whffu
  Date: 2023-02-01
  Time: 오후 8:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">  <!-- action이 requset url이랑 동일해야 함 -->
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>
