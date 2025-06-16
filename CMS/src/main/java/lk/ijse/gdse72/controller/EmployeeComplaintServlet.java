package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lk.ijse.gdse72.dao.ComplaintDAO;
import lk.ijse.gdse72.model.Complaint;
import lk.ijse.gdse72.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/employee_complaints", "/submitComplaint", "/editComplaint", "/edeleteComplaint"})
public class EmployeeComplaintServlet extends HttpServlet {

    private ComplaintDAO complaintDAO;

    @Override
    public void init() {
        BasicDataSource ds = (BasicDataSource) getServletContext().getAttribute("ds");
        complaintDAO = new ComplaintDAO(ds);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("full_name") == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            List<Complaint> complaints = complaintDAO.findByUserId(user.getId());
            request.setAttribute("complaints", complaints);
            request.getRequestDispatcher("/employee_dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Failed to load user complaints", e);
        }
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    if (session == null || session.getAttribute("full_name") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }

    User user = (User) session.getAttribute("user");
    String action = request.getServletPath(); // gets /submitComplaint, /editComplaint, or /deleteComplaint

    try {
        switch (action) {
            case "/submitComplaint": {
                String title = request.getParameter("title");
                String description = request.getParameter("description");

                if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
                    response.sendRedirect("employee_complaints?error=Missing+data");
                    return;
                }

                Complaint complaint = new Complaint();
                complaint.setUserId(user.getId());
                complaint.setTitle(title);
                complaint.setDescription(description);
                complaint.setStatus("PENDING");
                complaint.setRemarks("");

                complaintDAO.save(complaint);
                break;
            }
            case "/editComplaint": {
                String idStr = request.getParameter("id");
                String title = request.getParameter("title");
                String description = request.getParameter("description");

                if (idStr == null || title == null || description == null || title.isEmpty() || description.isEmpty()) {
                    response.sendRedirect("employee_complaints?error=Missing+update+data");
                    return;
                }

                int id = Integer.parseInt(idStr);
                Complaint complaint = complaintDAO.findById(id);

                if (complaint != null && complaint.getUserId() == user.getId()) {
                    complaint.setTitle(title);
                    complaint.setDescription(description);
                    complaintDAO.update(complaint);
                }
                break;
            }
            case "/edeleteComplaint": {
                String idStr = request.getParameter("id");

                if (idStr == null) {
                    response.sendRedirect("employee_complaints?error=Missing+delete+id");
                    return;
                }

                int id = Integer.parseInt(idStr);
                Complaint complaint = complaintDAO.findById(id);

                if (complaint != null && complaint.getUserId() == user.getId()) {
                    complaintDAO.delete(id);
                }
                break;
            }
        }

        response.sendRedirect("employee_complaints");

    } catch (SQLException e) {
        throw new ServletException("Complaint operation failed", e);
        }
    }

}
