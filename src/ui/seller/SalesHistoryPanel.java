package ui.seller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.PurchaseHistoryDao;
import dto.PurchaseHistoryDetail;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SalesHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private PurchaseHistoryDao phDao;

    public SalesHistoryPanel(ActionListener backListener) {
        phDao = new PurchaseHistoryDao();
        setLayout(new BorderLayout());

        // 상단: 뒤로가기 버튼
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("뒤로가기");
        backButton.addActionListener(backListener);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // 중앙: 구매(판매) 기록 테이블
        // 컬럼: 구매ID, 구매자ID, 제품ID, 제조사, 제품명, RAM(GB), 용량(GB), 구매가격, 구매수량, 구매일시
        String[] columnNames = {"구매ID", "구매자ID", "제품ID", "제조사", "제품명", "RAM(GB)", "용량(GB)", "구매가격", "구매수량", "구매일시"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 1:
                    case 2:
                        return Long.class;
                    case 5:
                    case 6:
                    case 8:
                        return Integer.class;
                    case 7:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        table = new JTable(tableModel);

        // 열 너비 조정
        table.getColumnModel().getColumn(0).setPreferredWidth(20);   // 구매ID
        table.getColumnModel().getColumn(1).setPreferredWidth(20);   // 구매자ID
        table.getColumnModel().getColumn(2).setPreferredWidth(20);   // 제품ID
        table.getColumnModel().getColumn(3).setPreferredWidth(50);   // 제조사
        table.getColumnModel().getColumn(4).setPreferredWidth(80);   // 제품명
        table.getColumnModel().getColumn(5).setPreferredWidth(30);   // RAM(GB)
        table.getColumnModel().getColumn(6).setPreferredWidth(30);   // 용량(GB)
        table.getColumnModel().getColumn(7).setPreferredWidth(80);   // 구매가격
        table.getColumnModel().getColumn(8).setPreferredWidth(30);   // 구매수량
        table.getColumnModel().getColumn(9).setPreferredWidth(130);  // 구매일시

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 데이터 로드
        loadSalesHistoryData();

        // 패널이 다시 보일 때마다 최신 데이터 로드
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadSalesHistoryData();
            }
        });
    }

    private void loadSalesHistoryData() {
        tableModel.setRowCount(0);
        // PurchaseHistoryDao의 listAllPurchaseHistoryDetail() 메서드를 통해 DB에서 데이터를 조회
        List<PurchaseHistoryDetail> historyList = phDao.listAllPurchaseHistoryDetail();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (PurchaseHistoryDetail phd : historyList) {
            String purchasedAtStr = "";
            if (phd.getPurchasedAt() != null) {
                purchasedAtStr = phd.getPurchasedAt().format(dtf);
            }
            Object[] row = new Object[] {
                    phd.getPurchaseId(),
                    phd.getBuyerId(),
                    phd.getPhoneId(),
                    phd.getManufacturer(),
                    phd.getPhoneName(),
                    phd.getRam(),
                    phd.getStorage(),
                    phd.getPurchasePrice(),
                    phd.getPurchaseQuantity(),
                    purchasedAtStr
            };
            tableModel.addRow(row);
        }
    }
}
