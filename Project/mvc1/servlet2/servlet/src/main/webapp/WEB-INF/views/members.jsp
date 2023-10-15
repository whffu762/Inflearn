<%--
  Created by IntelliJ IDEA.
  User: whffu
  Date: 2023-02-01
  Time: 오후 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
  <thead>
  <th>id</th>
  <th>username</th>
  <th>age</th>
  </thead>
  <tbody>
  <c:forEach var="item" items="${members}"> <!-- for문을 대체해주는 템플릿의 for문
    이런 식으로 자바 코드를 view에서 없앨 수 있음-->
      <tr>
        <td>${item.id}</td>
        <td>${item.username}</td>
        <td>${item.age}</td>
      </tr>

  </c:forEach>
  </tbody>
</table>
</body>
</html>
