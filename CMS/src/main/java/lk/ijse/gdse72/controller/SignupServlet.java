package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String role = request.getParameter("role");

            System.out.println("[DEBUG] Signup attempt: " + email + " as " + role);

            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match");
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
                return;
            }

            ServletContext context = getServletContext();
            BasicDataSource dataSource = (BasicDataSource) context.getAttribute("ds");

            if (dataSource == null) {
                throw new RuntimeException("DataSource is null! Check DataSource Listener.");
            }

            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement checkStmt = connection.prepareStatement(
                        "SELECT email FROM users WHERE email = ?");
                checkStmt.setString(1, email);

                if (checkStmt.executeQuery().next()) {
                    request.setAttribute("error", "Email already exists");
                    request.getRequestDispatcher("/signup.jsp").forward(request, response);
                    return;
                }

                PreparedStatement insertStmt = connection.prepareStatement(
                        "INSERT INTO users (full_name, email, password, role) VALUES (?, ?, ?, ?)");
                insertStmt.setString(1, fullName);
                insertStmt.setString(2, email);
                insertStmt.setString(3, password);
                insertStmt.setString(4, role);

                int rowsAffected = insertStmt.executeUpdate();

                if (rowsAffected > 0) {
                    request.setAttribute("success", "Registration successful!");
                    request.getRequestDispatcher("/signin.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Registration failed");
                    request.getRequestDispatcher("/signup.jsp").forward(request, response);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Database error: " + e.getMessage());
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }
}
