package ui.seller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.PhoneDao;
import dto.Phone;

public class AddProductPanel extends JPanel {
    private JTextField manufacturerField, phoneNameField, cpuField, ramField, storageField, screenSizeField, manufacturedYearField, priceField;

    /**
     * @param backListener 뒤로가기 버튼 클릭 시 MAIN 화면으로 전환하는 동작을 전달받음
     */
    public AddProductPanel(ActionListener backListener) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("제품 추가 페이지");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // 입력 폼 영역
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // 제조사
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제조사:"), gbc);
        manufacturerField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(manufacturerField, gbc);

        // 제품명
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제품명:"), gbc);
        phoneNameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(phoneNameField, gbc);

        // CPU
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("CPU:"), gbc);
        cpuField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(cpuField, gbc);

        // RAM
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("RAM (GB):"), gbc);
        ramField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(ramField, gbc);

        // 용량
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("용량 (GB):"), gbc);
        storageField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(storageField, gbc);

        // 화면 크기
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("화면 크기 (인치):"), gbc);
        screenSizeField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(screenSizeField, gbc);

        // 제조년도
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제조년도:"), gbc);
        manufacturedYearField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(manufacturedYearField, gbc);

        // 가격
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("가격:"), gbc);
        priceField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 하단 버튼 영역
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("추가");
        JButton backButton = new JButton("뒤로가기");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 추가 버튼 이벤트: 입력 검증 후 PhoneDao를 통해 DB에 삽입 처리
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String manufacturer = manufacturerField.getText().trim();
                String phoneName = phoneNameField.getText().trim();
                String cpu = cpuField.getText().trim();
                String ramStr = ramField.getText().trim();
                String storageStr = storageField.getText().trim();
                String screenSizeStr = screenSizeField.getText().trim();
                String manufacturedYearStr = manufacturedYearField.getText().trim();
                String priceStr = priceField.getText().trim();

                if(manufacturer.isEmpty() || phoneName.isEmpty() || cpu.isEmpty() ||
                        ramStr.isEmpty() || storageStr.isEmpty() || screenSizeStr.isEmpty() ||
                        manufacturedYearStr.isEmpty() || priceStr.isEmpty()){
                    JOptionPane.showMessageDialog(AddProductPanel.this,
                            "모든 필드를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ram, storage, manufacturedYear, price;
                double screenSize;
                try {
                    ram = Integer.parseInt(ramStr);
                    storage = Integer.parseInt(storageStr);
                    manufacturedYear = Integer.parseInt(manufacturedYearStr);
                    price = Integer.parseInt(priceStr);
                    screenSize = Double.parseDouble(screenSizeStr);
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddProductPanel.this,
                            "숫자 필드에 올바른 값을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Phone phone = new Phone();
                phone.setManufacturer(manufacturer);
                phone.setPhoneName(phoneName);
                phone.setCpu(cpu);
                phone.setRam(ram);
                phone.setStorage(storage);
                phone.setScreenSize(screenSize);
                phone.setManufacturedYear(manufacturedYear);
                phone.setPrice(price);
                // stock_quantity, sales_quantity는 DB 기본값 사용

                PhoneDao phoneDao = new PhoneDao();
                int result = phoneDao.insertPhone(phone);
                if(result > 0) {
                    JOptionPane.showMessageDialog(AddProductPanel.this,
                            "제품이 성공적으로 추가되었습니다.");
                    // 입력 필드 초기화 또는 추가 후 작업 수행 가능
                } else {
                    JOptionPane.showMessageDialog(AddProductPanel.this,
                            "제품 추가에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 뒤로가기 버튼: 전달받은 리스너 실행 (메인 메뉴로 전환)
        backButton.addActionListener(backListener);
    }
}
