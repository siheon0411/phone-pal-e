package ui.buyer;

import javax.swing.*;
import java.awt.*;

import dto.User;
import ui.LoginFrame;

public class BuyerFrame extends JFrame {
    private User user;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public BuyerFrame(User user) {
        this.user = user;
        setTitle("구매자 페이지 - " + user.getUserName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 1. 메인 메뉴 패널
        JPanel mainMenuPanel = new JPanel(new BorderLayout());
        // 상단: 환영 메시지와 우측 로그아웃 버튼
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("구매자 " + user.getUserName() + "님 환영합니다");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
        mainMenuPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙: 3개의 큰 버튼 ("핸드폰 검색", "장바구니", "구매이력")
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        JButton searchButton = new JButton("핸드폰 검색");
        JButton cartButton = new JButton("장바구니");
        JButton purchaseHistoryButton = new JButton("구매이력");
        Font bigFont = new Font("SansSerif", Font.BOLD, 24);
        searchButton.setFont(bigFont);
        cartButton.setFont(bigFont);
        purchaseHistoryButton.setFont(bigFont);
        centerPanel.add(searchButton);
        centerPanel.add(cartButton);
        centerPanel.add(purchaseHistoryButton);
        mainMenuPanel.add(centerPanel, BorderLayout.CENTER);

        cardPanel.add(mainMenuPanel, "MAIN");

        // 핸드폰 검색 패널
        PhoneSearchPanel phoneSearchPanel = new PhoneSearchPanel(user.getUserId(), e -> cardLayout.show(cardPanel, "MAIN"));
        cardPanel.add(phoneSearchPanel, "PHONE_SEARCH");

        // 장바구니 패널
        CartPanel cartPanel = new CartPanel(user.getUserId(), e -> cardLayout.show(cardPanel, "MAIN"));
        cardPanel.add(cartPanel, "CART");

        // 구매이력 패널
        PurchaseHistoryPanel phPanel = new PurchaseHistoryPanel(user.getUserId(), e -> cardLayout.show(cardPanel, "MAIN"));
        cardPanel.add(phPanel, "PURCHASE_HISTORY");

        // // 구매이력 패널 추후 구현 (여기서는 placeholder)
        // JPanel purchaseHistoryPanel = new JPanel(new BorderLayout());
        // purchaseHistoryPanel.add(new JLabel("구매이력 페이지", SwingConstants.CENTER), BorderLayout.CENTER);
        // JButton phBackButton = new JButton("뒤로가기");
        // phBackButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN"));
        // purchaseHistoryPanel.add(phBackButton, BorderLayout.SOUTH);
        // cardPanel.add(purchaseHistoryPanel, "PURCHASE_HISTORY");

        add(cardPanel);

        // 메인 메뉴 버튼 이벤트
        searchButton.addActionListener(e -> cardLayout.show(cardPanel, "PHONE_SEARCH"));
        cartButton.addActionListener(e -> cardLayout.show(cardPanel, "CART"));
        purchaseHistoryButton.addActionListener(e -> cardLayout.show(cardPanel, "PURCHASE_HISTORY"));
    }

}
