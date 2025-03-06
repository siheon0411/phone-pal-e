package dto;

import java.time.LocalDateTime;

public class User {
    private int userId;     // auto-increment
    private String userName;
    private String role;    // buyer or seller
    private LocalDateTime createdAt;    // auto-created by NOW() in mysql

    public User() {}
    public User(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    // auto-increment
    // public void setUserId(int userId) {
    //     this.userId = userId;
    // }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // auto-created by NOW() in mysql
    // public void setCreatedAt(LocalDateTime createdAt) {
    //     this.createdAt = createdAt;
    // }
}
