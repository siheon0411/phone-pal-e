package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UserDao;
import dto.User;
import ui.buyer.BuyerFrame;
import ui.seller.SellerFrame;

public class LoginFrame extends JFrame {

    private JTextField idField;
    private JButton loginButton;
    private UserDao userDao;

    public LoginFrame() {
        // 프로그램 제목을 "Phone Pal E DB"로 변경
        setTitle("Phone Pal E DB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        userDao = new UserDao();

        // 패널 및 레이아웃 설정
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // ID 입력 라벨
        JLabel idLabel = new JLabel("User ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        // ID 입력 텍스트 필드
        idField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(idField, gbc);

        // 로그인 버튼
        loginButton = new JButton("로그인");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(loginButton, gbc);

        // 엔터키 입력 시 로그인 버튼 동작하도록 설정
        getRootPane().setDefaultButton(loginButton);

        add(panel);

        // 로그인 버튼 이벤트 처리
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idField.getText().trim();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "User ID를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int userId;
                try {
                    userId = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "User ID는 숫자여야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // DB에서 user_id로 사용자 조회
                User user = userDao.getUserById(userId);
                if (user == null) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "해당 User ID의 사용자가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 사용자 role에 따라 화면 전환
                if ("seller".equalsIgnoreCase(user.getRole())) {
                    new SellerFrame(user).setVisible(true);
                } else if ("buyer".equalsIgnoreCase(user.getRole())) {
                    new BuyerFrame(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "알 수 없는 사용자 역할입니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
