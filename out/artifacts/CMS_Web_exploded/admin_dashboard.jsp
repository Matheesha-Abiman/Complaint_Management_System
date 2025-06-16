<%--
  Created by IntelliJ IDEA.
  User: matheesha
  Date: 6/15/25
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="lk.ijse.gdse72.model.Complaint" %>

<html>
<head>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>

<div class="top-bar">
  <span>Welcome,<%=request.getSession().getAttribute("full_name")%></span>
  <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

<h1>Admin Dashboard</h1>

<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>User ID</th>
    <th>Title</th>
    <th>Status</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="c" items="${requestScope.complaints}">
    <tr>
      <td>${c.id}</td>
      <td>${c.userId}</td>
      <td>${c.title}</td>
      <td>${c.status}</td>
      <td>
        <form method="post" action="updateComplaint">
          <input type="hidden" name="id" value="${c.id}">
          <select name="status">
            <option value="PENDING" ${c.status == 'PENDING' ? 'selected' : ''}>Pending</option>
            <option value="IN_PROGRESS" ${c.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
            <option value="RESOLVED" ${c.status == 'RESOLVED' ? 'selected' : ''}>Resolved</option>
          </select>
          <input type="text" name="remarks" placeholder="Remarks">
          <button type="submit">Update</button>
        </form>
        <form method="post" action="deleteComplaint" onsubmit="return confirm('Are you sure?');">
          <input type="hidden" name="id" value="${c.id}">
          <button type="submit" class="delete-btn">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
