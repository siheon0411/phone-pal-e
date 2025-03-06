package dao;

import common.DBManager;
import dto.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public int updatePhone(Phone phone) {
        int ret = -1;
        String sql = "update phone set manufacturer = ?, phone_name = ?, cpu = ?, ram = ?, storage = ?, screen_size = ?, manufactured_year = ?, price = ?, where phone_id = ?; ";

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
            pstmt.setLong(9, phone.getPhoneId());

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }


        return ret;
    }

    public int deletePhone(long phoneId) {
        int ret = -1;
        String sql = "delete from phone where phone_id = ?; ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, phoneId);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public List<Phone> listPhone() {
        List<Phone> list = new ArrayList<>();
        String sql = "select * from phone; ";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while(rs.next()) {
                Phone phone = new Phone();
                phone.setPhoneId(rs.getInt("phone_id"));
                phone.setManufacturer(rs.getString("manufacturer"));
                phone.setPhoneName(rs.getString("phone_name"));
                phone.setCpu(rs.getString("cpu"));
                phone.setRam(rs.getInt("ram"));
                phone.setStorage(rs.getInt("storage"));
                phone.setScreenSize(rs.getDouble("screen_size"));
                phone.setManufacturedYear(rs.getInt("manufactured_year"));
                phone.setPrice(rs.getInt("price"));
                phone.setStockQuantity(rs.getInt("stock_quantity"));
                phone.setSalesQuantity(rs.getInt("sales_quantity"));

                list.add(phone);
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return list;
    }

    public Phone getPhoneById(long phoneId) {
        Phone phone = null;
        String sql = "SELECT * FROM phone WHERE phone_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, phoneId);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                phone = new Phone();
                phone.setPhoneId(rs.getInt("phone_id"));
                phone.setManufacturer(rs.getString("manufacturer"));
                phone.setPhoneName(rs.getString("phone_name"));
                phone.setCpu(rs.getString("cpu"));
                phone.setRam(rs.getInt("ram"));
                phone.setStorage(rs.getInt("storage"));
                phone.setScreenSize(rs.getDouble("screen_size"));
                phone.setManufacturedYear(rs.getInt("manufactured_year"));
                phone.setPrice(rs.getInt("price"));
                phone.setStockQuantity(rs.getInt("stock_quantity"));
                phone.setSalesQuantity(rs.getInt("sales_quantity"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return phone;
    }


}
