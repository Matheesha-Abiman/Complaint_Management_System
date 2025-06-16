<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign-Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
</head>
<body>
<div class="container">
    <div class="signup-container">
        <h2 class="signup-title">Create Account</h2>

        <%-- Display error message if present --%>
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/signup" method="post">
            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" name="fullName" class="form-control" id="name"
                       placeholder="Enter your name" required
                       value="<%= request.getParameter("fullName") != null ? request.getParameter("fullName") : "" %>">
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" name="email" class="form-control" id="email"
                       placeholder="Enter your email" required
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
                <div class="form-text">We'll never share your email with anyone else.</div>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="password-input-group">
                    <input type="password" name="password" class="form-control" id="password"
                           placeholder="Create password" required>
                    <i class="bi bi-eye-fill toggle-password" id="togglePassword"></i>
                </div>
                <div class="form-text">Use 8 or more characters with a mix of letters, numbers & symbols.</div>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password</label>
                <div class="password-input-group">
                    <input type="password" name="confirmPassword" class="form-control" id="confirmPassword"
                           placeholder="Confirm password" required>
                    <i class="bi bi-eye-fill toggle-password" id="toggleConfirmPassword"></i>
                </div>
            </div>

            <div class="mb-3">
                <label for="role" class="form-label">Select Role</label>
                <select class="form-select" id="role" name="role" required>
                    <option value="">-- Select Role --</option>
                    <option value="EMPLOYEE" <%= "EMPLOYEE".equals(request.getParameter("role")) ? "selected" : "" %>>Employee</option>
                    <option value="ADMIN" <%= "ADMIN".equals(request.getParameter("role")) ? "selected" : "" %>>Admin</option>
                </select>
            </div>

            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="termsAgree" required>
                <label class="form-check-label" for="termsAgree">
                    I agree to the
                    <a href="#" class="text-decoration-none">Terms of Service</a> and
                    <a href="#" class="text-decoration-none">Privacy Policy</a>
                </label>
            </div>

            <button type="submit" class="btn btn-primary signup-btn">Sign Up</button>
        </form>

        <div class="login-link">
            <p class="text-muted">
                Already have an account?
                <a href="${pageContext.request.contextPath}/signin.jsp" class="text-decoration-none">Sign in</a>
            </p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/signup.js"></script>
</body>
</html>