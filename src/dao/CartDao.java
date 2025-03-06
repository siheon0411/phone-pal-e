package dao;

import common.DBManager;
import dto.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public int insertCart(long buyerId, long phoneId) {
        int ret = -1;
        String sql = "insert into cart values (DEFAULT, ?, ?, DEFAULT, DEFAULT); ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, buyerId);
            pstmt.setLong(2, phoneId);

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public int updateCart() {
        int ret = -1;

        return ret;
    }

    public int deleteCart(long cartId) {
        int ret = -1;

        String sql = "delete from cart where cart_id = ?; ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, cartId);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    public List<Cart> listCartByBuyerId(long buyerId) {
        List<Cart> list = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE buyer_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, buyerId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getLong("cart_id"));
                cart.setBuyerId(rs.getLong("buyer_id"));
                cart.setPhoneId(rs.getLong("phone_id"));
                cart.setQuantity(rs.getInt("quantity"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    cart.setCreatedAt(ts.toLocalDateTime());
                }
                list.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

}
