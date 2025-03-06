package ui.buyer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.PurchaseHistoryDao;
import dto.PurchaseHistoryDetail;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PurchaseHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private long buyerId;
    private PurchaseHistoryDao phDao;

    public PurchaseHistoryPanel(long buyerId, ActionListener backListener) {
        this.buyerId = buyerId;
        phDao = new PurchaseHistoryDao();
        setLayout(new BorderLayout());

        // 상단: 뒤로가기 버튼
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("뒤로가기");
        backButton.addActionListener(backListener);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // 중앙: 구매이력 테이블 구성
        // 컬럼: 구매ID, 제품ID, 제조사, 제품명, RAM(GB), 용량(GB), 구매가격, 구매수량, 구매일시
        String[] columnNames = {"구매ID", "제품ID", "제조사", "제품명", "RAM(GB)", "용량(GB)", "구매가격", "구매수량", "구매일시"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch(columnIndex) {
                    case 0:
                    case 1:
                        return Long.class;
                    case 4:
                    case 5:
                    case 7:
                        return Integer.class;
                    case 6:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadPurchaseHistoryData();

        // 패널이 다시 보일 때마다 최신 데이터 로드
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadPurchaseHistoryData();
            }
        });
    }

    private void loadPurchaseHistoryData() {
        tableModel.setRowCount(0);
        List<PurchaseHistoryDetail> historyList = phDao.listPurchaseHistoryDetailByBuyerId(buyerId);
        for(PurchaseHistoryDetail phd : historyList) {
            Object[] row = new Object[] {
                    phd.getPurchaseId(),
                    phd.getPhoneId(),
                    phd.getManufacturer(),
                    phd.getPhoneName(),
                    phd.getRam(),
                    phd.getStorage(),
                    phd.getPurchasePrice(),
                    phd.getPurchaseQuantity(),
                    phd.getPurchasedAt() != null ? phd.getPurchasedAt().toString() : ""
            };
            tableModel.addRow(row);
        }
    }
}
