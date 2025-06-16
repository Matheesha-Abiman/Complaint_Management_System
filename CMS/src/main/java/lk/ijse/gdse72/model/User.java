package lk.ijse.gdse72.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String fullName;
    private String password;
    private String email;
    private String role;
    private Timestamp createdAt;
}
