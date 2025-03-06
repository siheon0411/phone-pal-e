package dao;

import common.DBManager;
import dto.Phone;
import dto.PurchaseHistory;
import dto.PurchaseHistoryDetail;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryDao {

    public int insertPurchaseHistory(long buyerId, long phoneId, int purchasePrice, int purchaseQuantity) {
        int ret = -1;

        String sql = "insert into purchasehistory values (DEFAULT, ?, ?, DEFAULT, ?, ?, DEFAULT); ";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setLong(1, buyerId);
            pstmt.setLong(2, phoneId);
            pstmt.setInt(3, purchasePrice);
            pstmt.setInt(4, purchaseQuantity);

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    // Connection을 인자로 받도록 오버로드
    public int insertPurchaseHistory(Connection con, long buyerId, long phoneId, int purchasePrice, int purchaseQuantity) {
        int ret = -1;
        String sql = "insert into purchasehistory values (DEFAULT, ?, ?, DEFAULT, ?, ?, DEFAULT); ";

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, buyerId);
            pstmt.setLong(2, phoneId);
            pstmt.setInt(3, purchasePrice);
            pstmt.setInt(4, purchaseQuantity);

            ret = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Connection은 닫지 않고, pstmt만 닫음
            DBManager.releaseConnection(pstmt, null);
        }

        return ret;
    }

    // 트랜잭션 메서드: 구매 기록 삽입과 장바구니 삭제를 하나의 트랜잭션으로 처리
    public boolean processPurchase(long cartId, long buyerId, long phoneId, int purchasePrice, int purchaseQuantity) {
        Connection con = null;

        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false); // 트랜잭션 시작

            // 1. PurchaseHistory 삽입
            int insertResult = insertPurchaseHistory(con, buyerId, phoneId, purchasePrice, purchaseQuantity);

            // 2. Cart 항목 삭제: CartDao의 deleteCart() 메서드 (Connection을 인자로 받음)
            CartDao cartDao = new CartDao();
            int deleteResult = cartDao.deleteCart(con, cartId);

            if (insertResult > 0 && deleteResult > 0) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (con != null) {
                try { con.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            return false;
        } finally {
            DBManager.releaseConnection(con);
        }
    }

    // buyerId를 기준으로 구매이력과 함께 phone 테이블 정보를 JOIN하여 조회
    public List<PurchaseHistoryDetail> listPurchaseHistoryDetailByBuyerId(long buyerId) {
        List<PurchaseHistoryDetail> list = new ArrayList<>();
        String sql = "SELECT ph.purchase_id, ph.buyer_id, ph.phone_id, ph.purchase_price, ph.purchase_quantity, ph.purchased_at, " +
                "p.manufacturer, p.phone_name, p.ram, p.storage " +
                "FROM purchasehistory ph " +
                "JOIN phone p ON ph.phone_id = p.phone_id " +
                "WHERE ph.buyer_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, buyerId);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                PurchaseHistoryDetail phd = new PurchaseHistoryDetail();
                phd.setPurchaseId(rs.getLong("purchase_id"));
                phd.setBuyerId(rs.getLong("buyer_id"));
                phd.setPhoneId(rs.getLong("phone_id"));
                phd.setPurchasePrice(rs.getInt("purchase_price"));
                phd.setPurchaseQuantity(rs.getInt("purchase_quantity"));
                Timestamp ts = rs.getTimestamp("purchased_at");
                if(ts != null) {
                    phd.setPurchasedAt(ts.toLocalDateTime());
                }
                phd.setManufacturer(rs.getString("manufacturer"));
                phd.setPhoneName(rs.getString("phone_name"));
                phd.setRam(rs.getInt("ram"));
                phd.setStorage(rs.getInt("storage"));
                list.add(phd);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return list;
    }
}
