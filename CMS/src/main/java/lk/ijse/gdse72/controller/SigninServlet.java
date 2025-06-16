package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lk.ijse.gdse72.dao.ComplaintDAO;
import lk.ijse.gdse72.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;

@WebServlet("/signin")
public class SigninServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ServletContext context = getServletContext();
        BasicDataSource dataSource = (BasicDataSource) context.getAttribute("ds");
        ComplaintDAO complaintDAO=new ComplaintDAO(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ?");
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                // Successful login
                HttpSession session = request.getSession();
                session.setAttribute("full_name", rs.getString("full_name"));
                session.setAttribute("role", rs.getString("role"));
                User user=new User();
                user.setId(rs.getInt("id"));
                session.setAttribute("user", user);

                String role = rs.getString("role");
                if ("ADMIN".equals(role)) {

                    response.sendRedirect(request.getContextPath() + "/admin_complaints");
                } else {
                    response.sendRedirect(request.getContextPath() + "/employee_complaints");
                }
            } else {
                // Failed login
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/signin.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/signin.jsp").forward(request, response);
        }
    }
}
