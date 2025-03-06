package dao;

import common.DBManager;
import dto.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhoneDao {

    public int insertPhone(Phone phone) {
        int ret = -1;
        String sql = "insert into phone values (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, phone.getManufacturer());
            pstmt.setString(2, phone.getPhoneName());
            pstmt.setString(3, phone.getCpu());
            pstmt.setInt(4, phone.getRam());
            pstmt.setInt(5, phone.getStorage());
            pstmt.setDouble(6, phone.getScreenSize());
            pstmt.setInt(7, phone.getManufacturedYear());
            pstmt.setInt(8, phone.getPrice());
            pstmt.setInt(9, phone.getStockQuantity());
            pstmt.setInt(10, phone.getSalesQuantity());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }
}
