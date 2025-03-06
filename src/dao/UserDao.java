package dao;

import common.DBManager;
import dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    // user_id로 로그인하는 메소드
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT user_id, user_name, role FROM user WHERE user_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return user;
    }


    public int insertUser(User user) {
        int ret = -1;
        String sql = "insert into user (user_name, role) values (?, ?); ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getRole());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public int updateUser(User user) {
        int ret = -1;

        return ret;
    }

    public int deleteUser(User user) {
        int ret = -1;

        return ret;
    }


}
