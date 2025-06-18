package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lk.ijse.gdse72.dao.ComplaintDAO;
import lk.ijse.gdse72.model.Complaint;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin_complaints", "/updateComplaint", "/deleteComplaint"})
public class AdminComplaintServlet extends HttpServlet {

    private ComplaintDAO complaintDAO;

    @Override
    public void init() {
        BasicDataSource ds = (BasicDataSource) getServletContext().getAttribute("ds");
        complaintDAO = new ComplaintDAO(ds);
    }

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //     try {
    //         List<Complaint> complaints = complaintDAO.findAllWithUserDetails();
    //         req.setAttribute("complaints", complaints);
    //         req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, resp);
    //     } catch (SQLException e) {
    //         throw new ServletException("Failed to load complaints", e);
    //     }
    // }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Complaint> complaints = complaintDAO.findAllWithUserDetails();

            // Count complaint statuses
            int resolvedCount = 0;
            int pendingCount = 0;
            int inProgressCount = 0;

            for (Complaint c : complaints) {
                String status = c.getStatus();
                if ("RESOLVED".equalsIgnoreCase(status)) {
                    resolvedCount++;
                } else if ("PENDING".equalsIgnoreCase(status)) {
                    pendingCount++;
                } else if ("IN_PROGRESS".equalsIgnoreCase(status)) {
                    inProgressCount++;
                }
            }

            req.setAttribute("complaints", complaints);
            req.setAttribute("resolvedCount", resolvedCount);
            req.setAttribute("pendingCount", pendingCount);
            req.setAttribute("inProgressCount", inProgressCount);

            req.getRequestDispatcher("/admin_dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load complaints", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/updateComplaint" -> updateComplaint(req, resp);
            case "/deleteComplaint" -> deleteComplaint(req, resp);
        }
    }

    private void updateComplaint(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String status = req.getParameter("status");
        String remarks = req.getParameter("remarks");

        try {
            complaintDAO.updateStatus(id, status, remarks);
            resp.sendRedirect("admin_complaints");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update complaint", e);
        }
    }

    private void deleteComplaint(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            complaintDAO.delete(id);
            resp.sendRedirect("admin_complaints");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete complaint", e);
        }
    }
}
