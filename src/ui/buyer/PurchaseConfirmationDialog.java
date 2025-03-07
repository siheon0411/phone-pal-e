package ui.buyer;

import dao.CartDao;
import dao.PhoneDao;
import dao.PurchaseHistoryDao;
import dto.PurchaseHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PurchaseConfirmationDialog extends JDialog {
    public PurchaseConfirmationDialog(Frame parent, long cartId, long buyerId, long phoneId, String manufacturer, String phoneName, int ram, int storage, int price, int quantity, int total) {
        super(parent, "구매 확인", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // 정보 표시 패널
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("제품ID: " + phoneId));
        infoPanel.add(new JLabel("제조사: " + manufacturer));
        infoPanel.add(new JLabel("제품명: " + phoneName));
        infoPanel.add(new JLabel("RAM:  " + ram + " GB"));
        infoPanel.add(new JLabel("용량:  " + storage + " GB"));
        infoPanel.add(new JLabel("가격:  " + price + " 원"));
        infoPanel.add(new JLabel("수량:  " + quantity + " 개"));
        infoPanel.add(new JLabel("총액:  " + total + " 원"));
        add(infoPanel, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("예");
        JButton noButton = new JButton("취소");
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // "예" 버튼 클릭 시 구매 프로세스 진행 (여기서는 단순 메시지로 처리)
        yesButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // 먼저 재고 확인
                PhoneDao phoneDao = new PhoneDao();
                int availableStock = phoneDao.checkStockQuantity(phoneId);
                if (quantity > availableStock) {
                    JOptionPane.showMessageDialog(PurchaseConfirmationDialog.this,
                            "구매하려는 수량이 재고보다 많습니다.",
                            "재고 부족", JOptionPane.ERROR_MESSAGE);
                    return; // 구매 프로세스를 진행하지 않음
                }

                // 재고가 충분하면 구매 프로세스 진행 (트랜잭션으로 구매 기록 삽입, Cart 삭제, phone 재고 업데이트)
                PurchaseHistoryDao phDao = new PurchaseHistoryDao();
                boolean success = phDao.processPurchase(cartId, buyerId, phoneId, price, quantity);
                if(success) {
                    JOptionPane.showMessageDialog(PurchaseConfirmationDialog.this, "구매가 완료되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(PurchaseConfirmationDialog.this, "구매 처리에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });

        // "취소" 버튼은 다이얼로그 닫기
        noButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
