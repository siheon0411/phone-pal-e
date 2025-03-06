package dto;

import java.time.LocalDateTime;

public class User {
    private long userId;     // auto-increment
    private String userName;
    private String role;    // buyer or seller
    private LocalDateTime createdAt;    // auto-created default by NOW() in mysql

    public User() {}

    public User(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
