package ui.seller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SalesHistoryPanel extends JPanel {
    private DefaultListModel<String> listModel;
    private JList<String> salesList;
    private JButton backButton;

    /**
     * @param backListener 뒤로가기 버튼 클릭 시 MAIN 화면으로 전환하는 동작을 전달받음
     */
    public SalesHistoryPanel(ActionListener backListener) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("판매이력 페이지");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        salesList = new JList<>(listModel);
        salesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(salesList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = new JButton("뒤로가기");
        backButton.addActionListener(backListener);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // 더미 판매이력 데이터 (실제 구현 시 DB 조회 결과로 대체)
        listModel.addElement("Samsung Galaxy S24 - 판매수량: 5");
        listModel.addElement("Apple iPhone 16 - 판매수량: 3");
        listModel.addElement("Xiaomi Redmi Note 11 - 판매수량: 2");
    }
}
