package ui.seller;

import dao.PhoneDao;
import dto.Phone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class StockViewPanel extends JPanel {
    private JComboBox<String> categoryCombo;
    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;

    // 컬럼 이름
    private String[] columnNames = {"제품ID", "제조사", "제품명", "CPU", "RAM(GB)", "용량(GB)", "화면크기(인치)", "제조년도", "가격", "재고수량", "판매수량"};

    /**
     * @param backListener 뒤로가기 버튼 클릭 시 MAIN 화면으로 전환하는 동작을 전달받음
     */
    public StockViewPanel(ActionListener backListener) {
        setLayout(new BorderLayout());

        // 상단 영역 구성: 뒤로가기 버튼과 검색 영역
        JPanel topPanel = new JPanel(new BorderLayout());

        backButton = new JButton("뒤로가기");
        backButton.addActionListener(backListener);
        topPanel.add(backButton, BorderLayout.WEST);

        JPanel searchPanel = new JPanel();
        String[] categories = {"제조사", "제품명", "CPU", "저장공간", "화면크기", "제조년도", "가격(이하)"};
        categoryCombo = new JComboBox<>(categories);
        searchField = new JTextField(20);
        searchButton = new JButton("검색");
        searchPanel.add(categoryCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // 중앙 영역: JTable 구성
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class;  // 제품ID
                    case 4: return Integer.class;  // RAM(GB)
                    case 5: return Integer.class;  // 용량(GB)
                    case 6: return Double.class;   // 화면크기(인치)
                    case 7: return Integer.class;  // 제조년도
                    case 8: return Integer.class;  // 가격
                    case 9: return Integer.class;  // 재고수량
                    case 10: return Integer.class; // 판매수량
                    default: return String.class;
                }
            }
        };
        table = new JTable(tableModel);

        // TableRowSorter를 적용하여 정렬 기능 활성화
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        // 각 컬럼의 다음 정렬 순서를 저장 (false: 다음은 내림차순, true: 다음은 오름차순)
        Map<Integer, Boolean> sortOrderMap = new HashMap<>();
        for (int i = 0; i < table.getColumnCount(); i++) {
            sortOrderMap.put(i, false);
        }
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                if (col != -1) {
                    boolean ascending = sortOrderMap.get(col);
                    SortOrder order = ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING;
                    sorter.setSortKeys(Collections.singletonList(new SortKey(col, order)));
                    sorter.sort();
                    // 토글하여 다음 번에는 반대 순서로 정렬
                    sortOrderMap.put(col, !ascending);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 초기 데이터 로드 (검색 전에는 모든 데이터 표시)
        loadTableData(null, null);

        // 검색 버튼 이벤트: 선택된 기준과 키워드에 따라 테이블 데이터를 필터링
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryCombo.getSelectedItem();
                String keyword = searchField.getText().trim();
                loadTableData(category, keyword);
            }
        });

        // 테이블 행 더블 클릭 이벤트: 선택한 행의 데이터를 추출하여 상세 정보 수정 다이얼로그(ProductDetailDialog) 띄우기
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int viewRow = table.getSelectedRow();
                    if(viewRow != -1) {
                        // RowSorter를 사용하는 경우 모델 인덱스로 변환
                        int modelRow = table.convertRowIndexToModel(viewRow);
                        Object[] rowData = new Object[tableModel.getColumnCount()];
                        for (int i = 0; i < tableModel.getColumnCount(); i++) {
                            rowData[i] = tableModel.getValueAt(modelRow, i);
                        }
                        // ProductDetailDialog를 모달로 띄움 (제품ID는 라벨로 표시되어 수정 불가)
                        new ProductDetailDialog((Frame) SwingUtilities.getWindowAncestor(StockViewPanel.this), rowData)
                                .setVisible(true);
                    }
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadTableData(null, null); // 패널이 다시 보일 때마다 데이터를 새로 로드
            }
        });
    }

    /**
     * 테이블 데이터를 로드한다.
     * @param category 선택된 검색 기준 (null이면 전체 데이터 로드)
     * @param keyword 검색 키워드 (null 또는 empty면 전체 데이터 로드)
     */
    private void loadTableData(String category, String keyword) {
        tableModel.setRowCount(0);
        PhoneDao phoneDao = new PhoneDao();
        List<Phone> phones = phoneDao.listPhone();
        boolean isSearch = (keyword != null && !keyword.isEmpty());

        for (Phone p : phones) {
            Object[] row = new Object[] {
                    p.getPhoneId(),
                    p.getManufacturer(),
                    p.getPhoneName(),
                    p.getCpu(),
                    p.getRam(),
                    p.getStorage(),
                    p.getScreenSize(),
                    p.getManufacturedYear(),
                    p.getPrice(),
                    p.getStockQuantity(),
                    p.getSalesQuantity()
            };
            if (isSearch) {
                boolean matches = false;
                switch(category) {
                    case "제조사":
                        matches = p.getManufacturer().toLowerCase().contains(keyword.toLowerCase());
                        break;
                    case "제품명":
                        matches = p.getPhoneName().toLowerCase().contains(keyword.toLowerCase());
                        break;
                    case "CPU":
                        matches = p.getCpu().toLowerCase().contains(keyword.toLowerCase());
                        break;
                    case "저장공간":
                        matches = String.valueOf(p.getStorage()).contains(keyword);
                        break;
                    case "화면크기":
                        matches = String.valueOf(p.getScreenSize()).contains(keyword);
                        break;
                    case "제조년도":
                        matches = String.valueOf(p.getManufacturedYear()).contains(keyword);
                        break;
                    case "가격(이하)":
                        try {
                            int priceLimit = Integer.parseInt(keyword);
                            matches = (p.getPrice() <= priceLimit);
                        } catch(NumberFormatException ex) {
                            matches = false;
                        }
                        break;
                    default:
                        matches = false;
                }
                if (!matches) {
                    continue; // 조건에 맞지 않으면 건너뛰기
                }
            }
            tableModel.addRow(row);
        }
    }
}
