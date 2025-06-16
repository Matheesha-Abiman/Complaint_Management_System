<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Employee Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
<div class="dashboard">
  <h1>Welcome, <%= session.getAttribute("full_name") %> 👋</h1>
  <p>You are logged in as <strong>Employee</strong>.</p>

  <% if ("success".equals(request.getParameter("login"))) { %>
  <div class="alert alert-success">Login successful!</div>
  <% } %>

  <a href="${pageContext.request.contextPath}/logout.jsp" class="logout-btn">Logout</a>
</div>
</body>
</html>
