package ui.buyer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import dao.CartDao;
import dao.PhoneDao;
import dto.Cart;
import dto.Phone;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CartPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton purchaseButton;
    private JButton backButton;
    private long buyerId;
    private CartDao cartDao;
    private PhoneDao phoneDao;

    public CartPanel(long buyerId, ActionListener backListener) {
        this.buyerId = buyerId;
        cartDao = new CartDao();
        phoneDao = new PhoneDao();

        setLayout(new BorderLayout());

        // 상단: 뒤로가기 버튼
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("뒤로가기");
        backButton.addActionListener(backListener);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // 중앙: 장바구니 내용을 표시할 테이블
        // 컬럼: 제품ID, 제조사, 제품명, RAM(GB), 용량(GB), 가격, 수량, 총액
        String[] columnNames = {"제품ID", "제조사", "제품명", "RAM(GB)", "용량(GB)", "가격", "수량", "총액"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // "수량" 칼럼(index 6)만 편집 가능하도록 허용
                return column == 6;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 하단: "구입하기" 버튼
        purchaseButton = new JButton("구입하기");
        add(purchaseButton, BorderLayout.SOUTH);

        // 테이블 모델 리스너 추가: 수량 변경 시 총액 업데이트
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 편집 이벤트이고, 편집된 칼럼이 "수량"(인덱스 6)인 경우
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 6) {
                    int row = e.getFirstRow();
                    try {
                        // 가격은 칼럼 5, 수량은 칼럼 6
                        int price = Integer.parseInt(tableModel.getValueAt(row, 5).toString());
                        int quantity = Integer.parseInt(tableModel.getValueAt(row, 6).toString());
                        int total = price * quantity;
                        tableModel.setValueAt(total, row, 7);
                    } catch (NumberFormatException ex) {
                        // 숫자 형식이 올바르지 않은 경우 무시하거나 오류 처리
                        ex.printStackTrace();
                    }
                }
            }
        });

        loadCartData();

        // "구입하기" 버튼 이벤트: 선택된 행의 데이터를 확인하는 다이얼로그 띄움
        purchaseButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1) {
                    JOptionPane.showMessageDialog(CartPanel.this, "구입할 제품을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 선택된 행의 최신 값 읽기
                int modelRow = table.convertRowIndexToModel(selectedRow);
                Object prodIdObj = tableModel.getValueAt(modelRow, 0);
                Object manufacturerObj = tableModel.getValueAt(modelRow, 1);
                Object prodNameObj = tableModel.getValueAt(modelRow, 2);
                Object ramObj = tableModel.getValueAt(modelRow, 3);
                Object storageObj = tableModel.getValueAt(modelRow, 4);
                Object priceObj = tableModel.getValueAt(modelRow, 5);
                Object quantityObj = tableModel.getValueAt(modelRow, 6);

                // 파싱
                long phoneId = Long.parseLong(prodIdObj.toString());
                String manufacturer = manufacturerObj.toString();
                String phoneName = prodNameObj.toString();
                int ram = Integer.parseInt(ramObj.toString());
                int storage = Integer.parseInt(storageObj.toString());
                int price = Integer.parseInt(priceObj.toString());
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityObj.toString());
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CartPanel.this, "수량 입력이 올바르지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int total = price * quantity;

                // 구매 확인 다이얼로그 띄우기
                PurchaseConfirmationDialog dialog = new PurchaseConfirmationDialog(
                        (Frame) SwingUtilities.getWindowAncestor(CartPanel.this),
                        phoneId, manufacturer, phoneName, ram, storage, price, quantity, total
                );
                dialog.setVisible(true);
            }
        });

        // 패널이 다시 보일 때마다 최신 데이터 로드
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadCartData();
            }
        });
    }

    private void loadCartData() {
        tableModel.setRowCount(0);
        List<Cart> cartItems = cartDao.listCartByBuyerId(buyerId);
        for(Cart cart : cartItems) {
            Phone phone = phoneDao.getPhoneById(cart.getPhoneId());
            if(phone != null) {
                int quantity = cart.getQuantity();
                int price = phone.getPrice();
                int total = price * quantity;
                Object[] row = new Object[] {
                        phone.getPhoneId(),
                        phone.getManufacturer(),
                        phone.getPhoneName(),
                        phone.getRam(),
                        phone.getStorage(),
                        price,
                        quantity,
                        total
                };
                tableModel.addRow(row);
            }
        }
    }
}
