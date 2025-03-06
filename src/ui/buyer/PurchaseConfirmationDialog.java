package ui.buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PurchaseConfirmationDialog extends JDialog {
    public PurchaseConfirmationDialog(Frame parent, long phoneId, String manufacturer, String phoneName, int ram, int storage, int price, int quantity, int total) {
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
        infoPanel.add(new JLabel("가격: " + price + "원"));
        infoPanel.add(new JLabel("수량: " + quantity));
        infoPanel.add(new JLabel("총액: " + total + "원"));
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
                // 구매 로직(DAO 연동 등)을 구현한 후 처리
                JOptionPane.showMessageDialog(PurchaseConfirmationDialog.this, "구매가 완료되었습니다.");
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
