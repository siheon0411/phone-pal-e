package ui.buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BuyerPhoneDetailDialog extends JDialog {
    private JLabel idLabel;
    private JLabel manufacturerLabel;
    private JLabel phoneNameLabel;
    private JLabel cpuLabel;
    private JLabel ramLabel;
    private JLabel storageLabel;
    private JLabel screenSizeLabel;
    private JLabel manufacturedYearLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;
    private JLabel salesLabel;

    /**
     * @param parent 부모 프레임
     * @param rowData 선택된 행의 데이터 배열
     *                {제품ID, 제조사, 제품명, CPU, RAM, 용량, 화면크기, 제조년도, 가격, 재고수량, 판매수량}
     */
    public BuyerPhoneDetailDialog(Frame parent, Object[] rowData) {
        super(parent, "제품 상세 정보", true);
        setSize(400, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        // 제품ID
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("제품ID:"), gbc);
        idLabel = new JLabel(rowData[0].toString());
        gbc.gridx = 1;
        infoPanel.add(idLabel, gbc);

        // 제조사
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("제조사:"), gbc);
        manufacturerLabel = new JLabel(rowData[1].toString());
        gbc.gridx = 1;
        infoPanel.add(manufacturerLabel, gbc);

        // 제품명
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("제품명:"), gbc);
        phoneNameLabel = new JLabel(rowData[2].toString());
        gbc.gridx = 1;
        infoPanel.add(phoneNameLabel, gbc);

        // CPU
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("CPU:"), gbc);
        cpuLabel = new JLabel(rowData[3].toString());
        gbc.gridx = 1;
        infoPanel.add(cpuLabel, gbc);

        // RAM(GB)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("RAM(GB):"), gbc);
        ramLabel = new JLabel(rowData[4].toString());
        gbc.gridx = 1;
        infoPanel.add(ramLabel, gbc);

        // 용량(GB)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("용량(GB):"), gbc);
        storageLabel = new JLabel(rowData[5].toString());
        gbc.gridx = 1;
        infoPanel.add(storageLabel, gbc);

        // 화면크기(인치)
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("화면크기(인치):"), gbc);
        screenSizeLabel = new JLabel(rowData[6].toString());
        gbc.gridx = 1;
        infoPanel.add(screenSizeLabel, gbc);

        // 제조년도
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("제조년도:"), gbc);
        manufacturedYearLabel = new JLabel(rowData[7].toString());
        gbc.gridx = 1;
        infoPanel.add(manufacturedYearLabel, gbc);

        // 가격
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("가격:"), gbc);
        priceLabel = new JLabel(rowData[8].toString());
        gbc.gridx = 1;
        infoPanel.add(priceLabel, gbc);

        // 재고수량
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("재고수량:"), gbc);
        stockLabel = new JLabel(rowData[9].toString());
        gbc.gridx = 1;
        infoPanel.add(stockLabel, gbc);

        // 판매수량
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        infoPanel.add(new JLabel("판매수량:"), gbc);
        salesLabel = new JLabel(rowData[10].toString());
        gbc.gridx = 1;
        infoPanel.add(salesLabel, gbc);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addToCartButton = new JButton("장바구니 담기");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(addToCartButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addToCartButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // 실제 장바구니 추가 로직(DAO 연동 등)은 추후 구현
                JOptionPane.showMessageDialog(BuyerPhoneDetailDialog.this, "해당 제품이 장바구니에 담겼습니다.");
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
    }
}
