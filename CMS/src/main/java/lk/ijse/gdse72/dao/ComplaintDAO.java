
package lk.ijse.gdse72.dao;

import lk.ijse.gdse72.model.Complaint;
import lk.ijse.gdse72.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    private final BasicDataSource dataSource;

    public ComplaintDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Complaint save(Complaint complaint) throws SQLException {
        String sql = "INSERT INTO complaints (user_id, title, description, status, remarks) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, complaint.getUserId());
            stmt.setString(2, complaint.getTitle());
            stmt.setString(3, complaint.getDescription());
            stmt.setString(4, complaint.getStatus());
            stmt.setString(5, complaint.getRemarks());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                complaint.setId(rs.getInt(1));
            }
            return complaint;
        }
    }

    public List<Complaint> findAllWithUserDetails() throws SQLException {
        String sql = """
                SELECT c.*, u.full_name 
                FROM complaints c 
                JOIN users u ON c.user_id = u.id 
                ORDER BY c.created_at DESC
                """;
        List<Complaint> complaints = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                complaints.add(mapComplaintWithUser(rs));
            }
            return complaints;
        }
    }

    public List<Complaint> findByUserId(int userId) throws SQLException {
        String sql = "SELECT c.*, u.full_name FROM complaints c JOIN users u ON c.user_id = u.id WHERE c.user_id = ?";
        List<Complaint> complaints = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                complaints.add(mapComplaintWithUser(rs));
            }
            return complaints;
        }
    }

    public void updateStatus(int id, String status, String remarks) throws SQLException {
        String sql = "UPDATE complaints SET status = ?, remarks = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setString(2, remarks);
            stmt.setInt(3, id);

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM complaints WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Complaint findById(int id) throws SQLException {
        String sql = "SELECT c.*, u.full_name FROM complaints c JOIN users u ON c.user_id = u.id WHERE c.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapComplaintWithUser(rs);
            }
            return null;
        }
    }

    public void update(Complaint complaint) throws SQLException {
        String sql = "UPDATE complaints SET title = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complaint.getTitle());
            stmt.setString(2, complaint.getDescription());
            stmt.setInt(3, complaint.getId());

            stmt.executeUpdate();
        }
    }

    private Complaint mapComplaintWithUser(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt("id"));
        complaint.setUserId(rs.getInt("user_id"));
        complaint.setTitle(rs.getString("title"));
        complaint.setDescription(rs.getString("description"));
        complaint.setStatus(rs.getString("status"));
        complaint.setRemarks(rs.getString("remarks"));
        complaint.setCreatedAt(rs.getTimestamp("created_at"));
        complaint.setUpdatedAt(rs.getTimestamp("updated_at"));

        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setFullName(rs.getString("full_name"));
        complaint.setUser(user);

        return complaint;
    }
}