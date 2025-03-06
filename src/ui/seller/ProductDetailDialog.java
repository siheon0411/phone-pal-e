package ui.seller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.PhoneDao;
import dto.Phone;

public class ProductDetailDialog extends JDialog {
    // 제품ID는 라벨로 표시
    private JLabel idLabel;
    private JTextField manufacturerField;
    private JTextField phoneNameField;
    private JTextField cpuField;
    private JTextField ramField;
    private JTextField storageField;
    private JTextField screenSizeField;
    private JTextField manufacturedYearField;
    private JTextField priceField;
    private JTextField stockField;
    private JTextField salesField;

    /**
     * @param parent 부모 프레임
     * @param rowData 선택된 행의 데이터 배열
     *                {제품ID, 제조사, 제품명, CPU, RAM, 용량, 화면크기, 제조년도, 가격, 재고수량, 판매수량}
     */
    public ProductDetailDialog(Frame parent, Object[] rowData) {
        super(parent, "제품 상세 정보 수정", true);
        setSize(400, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // 입력 폼 패널 (GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // 제품ID: 라벨로 표시 (수정 불가)
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제품ID:"), gbc);
        idLabel = new JLabel(rowData[0].toString());
        gbc.gridx = 1;
        formPanel.add(idLabel, gbc);

        // 제조사
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제조사:"), gbc);
        manufacturerField = new JTextField(20);
        manufacturerField.setText(rowData[1].toString());
        gbc.gridx = 1;
        formPanel.add(manufacturerField, gbc);

        // 제품명
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제품명:"), gbc);
        phoneNameField = new JTextField(20);
        phoneNameField.setText(rowData[2].toString());
        gbc.gridx = 1;
        formPanel.add(phoneNameField, gbc);

        // CPU
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("CPU:"), gbc);
        cpuField = new JTextField(20);
        cpuField.setText(rowData[3].toString());
        gbc.gridx = 1;
        formPanel.add(cpuField, gbc);

        // RAM(GB)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("RAM(GB):"), gbc);
        ramField = new JTextField(20);
        ramField.setText(rowData[4].toString());
        gbc.gridx = 1;
        formPanel.add(ramField, gbc);

        // 용량(GB)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("용량(GB):"), gbc);
        storageField = new JTextField(20);
        storageField.setText(rowData[5].toString());
        gbc.gridx = 1;
        formPanel.add(storageField, gbc);

        // 화면크기(인치)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("화면크기(인치):"), gbc);
        screenSizeField = new JTextField(20);
        screenSizeField.setText(rowData[6].toString());
        gbc.gridx = 1;
        formPanel.add(screenSizeField, gbc);

        // 제조년도
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("제조년도:"), gbc);
        manufacturedYearField = new JTextField(20);
        manufacturedYearField.setText(rowData[7].toString());
        gbc.gridx = 1;
        formPanel.add(manufacturedYearField, gbc);

        // 가격
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("가격:"), gbc);
        priceField = new JTextField(20);
        priceField.setText(rowData[8].toString());
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        // 재고수량: 라벨로 표시 (수정 불가)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("재고수량:"), gbc);
        idLabel = new JLabel(rowData[9].toString());
        gbc.gridx = 1;
        formPanel.add(idLabel, gbc);

        // 판매수량: 라벨로 표시 (수정 불가)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("판매수량:"), gbc);
        idLabel = new JLabel(rowData[10].toString());
        gbc.gridx = 1;
        formPanel.add(idLabel, gbc);

        // // 재고수량
        // row++;
        // gbc.gridx = 0; gbc.gridy = row;
        // formPanel.add(new JLabel("재고수량:"), gbc);
        // stockField = new JTextField(20);
        // stockField.setText(rowData[9].toString());
        // gbc.gridx = 1;
        // formPanel.add(stockField, gbc);
        //
        // // 판매수량
        // row++;
        // gbc.gridx = 0; gbc.gridy = row;
        // formPanel.add(new JLabel("판매수량:"), gbc);
        // salesField = new JTextField(20);
        // salesField.setText(rowData[10].toString());
        // gbc.gridx = 1;
        // formPanel.add(salesField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("수정하기");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // "수정하기" 버튼 이벤트 처리
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Phone phone = new Phone();
                try {
                    // 제품ID는 idLabel에서 읽어옴
                    phone.setPhoneId(Integer.parseInt(idLabel.getText().trim()));
                    phone.setManufacturer(manufacturerField.getText().trim());
                    phone.setPhoneName(phoneNameField.getText().trim());
                    phone.setCpu(cpuField.getText().trim());
                    phone.setRam(Integer.parseInt(ramField.getText().trim()));
                    phone.setStorage(Integer.parseInt(storageField.getText().trim()));
                    phone.setScreenSize(Double.parseDouble(screenSizeField.getText().trim()));
                    phone.setManufacturedYear(Integer.parseInt(manufacturedYearField.getText().trim()));
                    phone.setPrice(Integer.parseInt(priceField.getText().trim()));
                    // phone.setStockQuantity(Integer.parseInt(stockField.getText().trim()));
                    // phone.setSalesQuantity(Integer.parseInt(salesField.getText().trim()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ProductDetailDialog.this,
                            "입력된 숫자 형식이 올바르지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // DB 업데이트 처리 (PhoneDao의 updatePhone 메서드가 있다고 가정)
                PhoneDao phoneDao = new PhoneDao();
                int result = phoneDao.updatePhone(phone);
                if(result > 0) {
                    JOptionPane.showMessageDialog(ProductDetailDialog.this, "제품 정보가 수정되었습니다.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ProductDetailDialog.this, "제품 정보 수정에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // "취소" 버튼 이벤트 처리: 다이얼로그 닫기
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
