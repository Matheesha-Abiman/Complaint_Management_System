<%--
  Created by IntelliJ IDEA.
  User: matheesha
  Date: 6/15/25
  Time: 4:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="lk.ijse.gdse72.model.Complaint" %>

<html>
<head>
  <title>Employee Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>

<div class="top-bar">
  <span>Welcome,<%=request.getSession().getAttribute("full_name")%></span>
  <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

<h1>Employee Complaint Portal</h1>
<!-- Complaint Submission Form -->
<h2>Submit a New Complaint</h2>
<form method="post" action="submitComplaint">
  <input type="text" name="title" placeholder="Complaint Title" required><br><br>
  <textarea name="description" placeholder="Complaint Description" rows="5" required></textarea><br><br>
  <button type="submit" id="esubmit-btn">Submit Complaint</button>
</form>

<hr>
<!-- Employee's Complaint List -->
<h2>Your Complaints</h2>
<table border="1" cellpadding="10">
  <thead>
  <tr>
    <th>ID</th>
    <th>Title</th>
    <th>Description</th>
    <th>Status</th>
    <th>Remarks</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="c" items="${requestScope.complaints}">
    <tr>
      <td>${c.id}</td>
      <td>${c.title}</td>
      <td>${c.description}</td>
      <td>${c.status}</td>
      <td>${c.remarks}</td>
      <td>
        <c:if test="${c.status != 'RESOLVED'}">
          <!-- Edit Form -->
          <form method="post" action="editComplaint">
            <input type="hidden" name="id" value="${c.id}">
            <input type="text" name="title" value="${c.title}" required><br>
            <textarea name="description" rows="3" required>${c.description}</textarea><br>
            <button type="submit" id="eupdate-btn">Update</button>
          </form>
          <!-- Delete Form -->
          <form method="post" action="edeleteComplaint" onsubmit="return confirm('Are you sure?');">
            <input type="hidden" name="id" value="${c.id}">
            <button type="submit" id="edelete-btn">Delete</button>
          </form>
        </c:if>
        <c:if test="${c.status == 'RESOLVED'}">
          <em>Locked (Resolved)</em>
        </c:if>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
