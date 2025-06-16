<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign-In</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css">
</head>
<body>
<div class="container">
    <div class="login-container">
        <h2 class="login-title">Sign In</h2>

        <!-- Logo Image -->
        <img src="${pageContext.request.contextPath}/assets/images/img1.png" alt="Logo" class="logo" width="340" height="300">

        <!-- Display error if login fails -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger mt-3" role="alert">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/signin" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="text" class="form-control" id="email" name="email"
                       placeholder="Enter your email" required autocomplete="email"
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="password-input-group">
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="Enter your password" required autocomplete="current-password">
                    <i class="bi bi-eye-fill toggle-password" id="togglePassword"></i>
                </div>
            </div>

            <button type="submit" class="btn btn-primary login-btn">Login</button>

            <div class="forgot-password mt-2">
                <a href="#" class="text-decoration-none">Forgot password?</a>
            </div>
        </form>

        <div class="signup-link mt-3">
            <p class="text-muted">Don't have an account?
                <a href="${pageContext.request.contextPath}/signup.jsp" class="text-decoration-none">Sign up</a>
            </p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/signin.js"></script>
</body>
</html>
