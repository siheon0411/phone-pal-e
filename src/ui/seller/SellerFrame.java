package ui.seller;

import javax.swing.*;
import java.awt.*;

import dto.User;
import ui.LoginFrame;

public class SellerFrame extends JFrame {
    private User user;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public SellerFrame(User user) {
        this.user = user;
        setTitle("판매자 페이지 - " + user.getUserName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 1. 메인 메뉴 패널
        JPanel mainMenuPanel = new JPanel(new BorderLayout());

        // 상단: 우측에 로그아웃 버튼
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("판매자 " + user.getUserName() + "님 환영합니다");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
        mainMenuPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙: 3개의 큰 버튼 (제품 추가, 재고 조회, 판매이력)
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JButton addProductButton = new JButton("제품 추가");
        JButton stockViewButton = new JButton("재고 조회");
        JButton salesHistoryButton = new JButton("판매이력");
        Font bigFont = new Font("SansSerif", Font.BOLD, 24);
        addProductButton.setFont(bigFont);
        stockViewButton.setFont(bigFont);
        salesHistoryButton.setFont(bigFont);
        centerPanel.add(addProductButton);
        centerPanel.add(stockViewButton);
        centerPanel.add(salesHistoryButton);
        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);

        // 메인 메뉴 패널을 카드에 등록
        cardPanel.add(mainMenuPanel, "MAIN");

        // 2. 제품 추가 패널
        AddProductPanel addProductPanel = new AddProductPanel(e -> cardLayout.show(cardPanel, "MAIN"));
        cardPanel.add(addProductPanel, "ADD_PRODUCT");

        // 3. 재고 조회 패널
        StockViewPanel stockViewPanel = new StockViewPanel(e -> cardLayout.show(cardPanel, "MAIN"));
        cardPanel.add(stockViewPanel, "STOCK_VIEW");

        // 4. 판매이력 패널
        SalesHistoryPanel salesHistoryPanel = new SalesHistoryPanel(e -> cardLayout.show(cardPanel, "MAIN"));
        cardPanel.add(salesHistoryPanel, "SALES_HISTORY");

        add(cardPanel);

        // 버튼 클릭 시 화면 전환
        addProductButton.addActionListener(e -> cardLayout.show(cardPanel, "ADD_PRODUCT"));
        stockViewButton.addActionListener(e -> cardLayout.show(cardPanel, "STOCK_VIEW"));
        salesHistoryButton.addActionListener(e -> cardLayout.show(cardPanel, "SALES_HISTORY"));
    }

}
