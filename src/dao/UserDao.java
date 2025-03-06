package dao;

import common.DBManager;
import dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

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
