/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import BLL.DTO.handle_violations_DTO;
import BLL.DTO.member;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import BLL.handle_violations_BLL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author ACER
 */
public class handle_violations extends javax.swing.JInternalFrame {
    
    private final handle_violations_BLL handleViolationBLL = new handle_violations_BLL();

    /**
     * Creates new form handle_violations
     */
    public handle_violations() {
        initComponents();
        loadDataToTable();
        loadMembersIntoComboBox();
         // Gán sự kiện cho bảng
        table_handle_violation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_handle_violationMouseClicked(evt);
            }
        });
        combobox_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_memberActionPerformed(evt);
            }
        });        
    }
    
    private void table_handle_violationMouseClicked(java.awt.event.MouseEvent evt) {                                                     
        // Lấy chỉ số hàng được chọn
        int selectedRowIndex = table_handle_violation.getSelectedRow();

        // Kiểm tra xem có hàng nào được chọn không
        if (selectedRowIndex != -1) {
            // Lấy dữ liệu từ hàng được chọn
            String handleViolationId = table_handle_violation.getValueAt(selectedRowIndex, 0).toString(); // Mã xử lý
            String memberName = table_handle_violation.getValueAt(selectedRowIndex, 1).toString(); // Tên thành viên
            String disposalForms = table_handle_violation.getValueAt(selectedRowIndex, 2).toString(); // Hình thức xử lý
            String money = table_handle_violation.getValueAt(selectedRowIndex, 3).toString(); // Số tiền
            String status = table_handle_violation.getValueAt(selectedRowIndex, 5).toString(); // Trạng thái xử lý
            // Hiển thị thông tin lên các thành phần giao diện người dùng tương ứng
            text_handle_violation_id.setText(handleViolationId); // Mã xử lý
            combobox_member.setSelectedItem(memberName); // Tên thành viên
            text_disposal_forms.setText(disposalForms); // Hình thức xử lý
            text_money_handle_violation.setText(money); // Số tiền
            // Thiết lập giá trị cho combobox dựa trên giá trị của trạng thái từ bảng
            if (status.equals("Đã xử lý")) {
                combobox_disposal_forms.setSelectedItem("Đã xử lý");
            } else if (status.equals("Đang xử lý")) {
                combobox_disposal_forms.setSelectedItem("Đang xử lý");
            } else {
                // Xử lý trường hợp khác nếu cần
                System.out.println("Giá trị status không hợp lệ: " + status);
            }

            // Lấy mã thành viên tương ứng với tên thành viên và hiển thị nó
            String memberId = handleViolationBLL.getMemberId(memberName);
            text_member_id_listen.setText(memberId);

            // Lấy ngày xử lý và hiển thị nó
            // Lấy ngày xử lý từ bảng dữ liệu
            String dateString = table_handle_violation.getValueAt(selectedRowIndex, 4).toString(); // Giả sử cột thứ 5 là cột chứa ngày xử lý

            // Chuyển đổi chuỗi ngày thành đối tượng Date
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString); // Định dạng ngày tháng phù hợp với ngày trong bảng dữ liệu
                // Hiển thị ngày xử lý lên JDateChooser
                date_handle_violation.setDate(date);
            } catch (ParseException ex) {
                // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi định dạng ngày
                ex.printStackTrace();
            }

        }
    }

    
    // Phương thức để lắng nghe sự kiện thay đổi trong combobox
    private void combobox_memberActionPerformed(java.awt.event.ActionEvent evt) {
        // Lấy tên thành viên được chọn từ combobox
        String selectedMemberName = (String) combobox_member.getSelectedItem();

        // Sử dụng phương thức getMemberId() trong lớp handle_violations_BLL để lấy ID tương ứng từ CSDL
        String memberId = handleViolationBLL.getMemberId(selectedMemberName);

        // Cập nhật trường memberId trong giao diện người dùng với ID tìm được
        text_member_id_listen.setText(memberId);
    }
    
    private void loadDataToTable() {
        ArrayList<handle_violations_DTO> handleViolations_DTO = handleViolationBLL.getAllHandleViolations();
        DefaultTableModel model = (DefaultTableModel) table_handle_violation.getModel();
        model.setRowCount(0);
        for (handle_violations_DTO hv : handleViolations_DTO) {   
            String status = convertStatusToString(hv.getTrangThaiXL());
            model.addRow(new Object[]{
                hv.getMaXL(),
                hv.getThanhvien().getHoTen(), // Assuming there's a method to get member's name
                hv.getHinhThucXL(),
                hv.getSoTien(),
                hv.getNgayXL(),
                status
            });
        }
    }
    public String convertStatusToString(int status) {
        return (status == 0) ? "Đang xử lý" : "Đã xử lý";
    }

    // Phương thức đổ dữ liệu tên thành viên vào comboBox
    private void loadMembersIntoComboBox() {
        ArrayList<String> memberNames = handleViolationBLL.getMemberNames();
        for (String memberName : memberNames) {
            combobox_member.addItem(memberName);
        }
    }
    
 
    
    // Phương thức để xóa dữ liệu trong các trường nhập liệu sau khi thêm mới thành công
    private void clearInputFields() {
        // Code để xóa dữ liệu trong các trường nhập liệu sau khi thêm mới thành công
        text_money_handle_violation.setText("");
        combobox_disposal_forms.setSelectedIndex(0);
        date_handle_violation.setDate(null);
        combobox_member.setSelectedIndex(0);
        text_member_id_listen.setText("");
        text_disposal_forms.setText("");
        text_handle_violation_id.setText("");
        
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        text_find_handle_violation = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_handle_violation = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        text_handle_violation_id = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        text_member_id_listen = new javax.swing.JTextField();
        combobox_member = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        text_money_handle_violation = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        date_handle_violation = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        combobox_disposal_forms = new javax.swing.JComboBox<>();
        text_disposal_forms = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        button_add_handle_violation = new javax.swing.JButton();
        button_update_handle_violation = new javax.swing.JButton();
        button_refresh_handle_violation = new javax.swing.JButton();
        button_close_handle_violation = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();

        jLabel18.setText("XỬ LÝ VI PHẠM");

        jLabel19.setText("Thông tin tìm kiếm:");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(text_find_handle_violation, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(text_find_handle_violation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        table_handle_violation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã xử lý", "Tên thành viên", "Hình thức xử lý", "Số tiền", "Ngày xử lý", "Trạng thái xử lý"
            }
        ));
        jScrollPane3.setViewportView(table_handle_violation);

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setText("Mã xử lý: ");

        text_handle_violation_id.setEnabled(false);

        jLabel22.setText("Tên thành viên:");

        jLabel24.setText("Mã thành viên:");

        text_member_id_listen.setEnabled(false);
        text_member_id_listen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_member_id_listenActionPerformed(evt);
            }
        });

        jLabel27.setText("Hình thức xử lý:");

        jLabel1.setText("Số tiền:");

        jLabel2.setText("Ngày xử lý:");

        jLabel3.setText("Trạng thái xử lý:");

        combobox_disposal_forms.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang xử lý" ,"Đã xử lý"}));
        combobox_disposal_forms.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combobox_disposal_formsItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(63, 63, 63)
                        .addComponent(text_money_handle_violation, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel27)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(text_disposal_forms)
                                    .addComponent(combobox_member, 0, 189, Short.MAX_VALUE))
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(combobox_disposal_forms, 0, 233, Short.MAX_VALUE))
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(date_handle_violation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(text_member_id_listen)))))
                            .addComponent(text_handle_violation_id, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
                        .addGap(11, 11, 11))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(text_handle_violation_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(combobox_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(combobox_disposal_forms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(text_disposal_forms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(text_member_id_listen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(text_money_handle_violation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(date_handle_violation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        button_add_handle_violation.setText("Thêm mới");
        button_add_handle_violation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_add_handle_violationActionPerformed(evt);
            }
        });

        button_update_handle_violation.setText("Cập nhật");
        button_update_handle_violation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_handle_violationActionPerformed(evt);
            }
        });

        button_refresh_handle_violation.setText("Làm mới");
        button_refresh_handle_violation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_refresh_handle_violationActionPerformed(evt);
            }
        });

        button_close_handle_violation.setText("Đóng");
        button_close_handle_violation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_close_handle_violationActionPerformed(evt);
            }
        });

        btn_delete.setText("Xóa");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_add_handle_violation, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(button_update_handle_violation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button_refresh_handle_violation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button_close_handle_violation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_delete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_add_handle_violation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_update_handle_violation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_refresh_handle_violation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete)
                .addGap(11, 11, 11)
                .addComponent(button_close_handle_violation)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addGap(0, 9, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 9, Short.MAX_VALUE)))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_member_id_listenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_member_id_listenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_member_id_listenActionPerformed

    private void button_add_handle_violationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_add_handle_violationActionPerformed
        // TODO add your handling code here:
        // Lấy dữ liệu từ các thành phần GUI
        String memberName = (String) combobox_member.getSelectedItem();
        String disposalForms = text_disposal_forms.getText();
        int status = combobox_disposal_forms.getSelectedIndex(); // 0 hoặc 1
        Date date = date_handle_violation.getDate(); // Đã import java.util.Date

        
        if (disposalForms.isEmpty() || text_money_handle_violation.getText().isEmpty() || combobox_member.getSelectedItem()== null || date == null) {
            // Hiển thị thông báo lỗi nếu có trường dữ liệu trống
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else{
                try {
                    double money = Double.parseDouble(text_money_handle_violation.getText());
                    try {
                        // Lấy mã thành viên tương ứng với tên thành viên được chọn
                        String memberId = handleViolationBLL.getMemberId(memberName);     
                        // Lấy đối tượng thành viên tương ứng với tên thành viên được chọn
                        member member = handleViolationBLL.getMemberByName(memberName);
                         // Tạo đối tượng handle_violations từ dữ liệu đã nhập
                         handle_violations_DTO newHandleViolation = new handle_violations_DTO(member, disposalForms, money, date, status);
                        // Gọi phương thức thêm mới từ lớp BLL
                        handleViolationBLL.addHandleViolation(newHandleViolation);
                        // Hiển thị thông báo thành công
                        JOptionPane.showMessageDialog(this, "Thêm mới xử lý vi phạm thành công!");
                        // Xóa dữ liệu trong các trường nhập liệu sau khi thêm mới thành công
                        clearInputFields();

                        // Cập nhật lại bảng dữ liệu
                        loadDataToTable();
                    } catch (Exception ex) {
                        // Hiển thị thông báo lỗi nếu có lỗi xảy ra
                        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(NumberFormatException ex) {
                    // Hiển thị thông báo lỗi nếu tiền nhập vào không phải là số
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        
    }//GEN-LAST:event_button_add_handle_violationActionPerformed

    private void combobox_disposal_formsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combobox_disposal_formsItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_combobox_disposal_formsItemStateChanged

    private void button_update_handle_violationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_update_handle_violationActionPerformed
        // TODO add your handling code here:
        // Lấy thông tin từ các trường nhập liệu trên giao diện
        String handleViolationId = text_handle_violation_id.getText();
        String memberName = combobox_member.getSelectedItem().toString();
        String disposalForms = text_disposal_forms.getText();
        String money_text = text_money_handle_violation.getText();
        double money = Double.parseDouble(text_money_handle_violation.getText());
        String status_text = combobox_disposal_forms.getSelectedItem().toString();
        int status = combobox_disposal_forms.getSelectedIndex(); // 0 hoặc 1
        Date date = date_handle_violation.getDate();

        // Kiểm tra xem các trường thông tin có rỗng không
        if (memberName.isEmpty() || disposalForms.isEmpty() || money_text.isEmpty() || status_text.isEmpty() || date == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Không thực hiện cập nhật nếu thông tin còn thiếu
        }
        // Lấy mã thành viên tương ứng với tên thành viên được chọn
        String memberId = handleViolationBLL.getMemberId(memberName);     
        // Lấy đối tượng thành viên tương ứng với tên thành viên được chọn
        member member = handleViolationBLL.getMemberByName(memberName);

        // Tạo một đối tượng handle_violations_DTO để cập nhật
         handle_violations_DTO handleViolation = new handle_violations_DTO(Integer.parseInt(handleViolationId),member, disposalForms, money, date, status);

        try {
            // Gọi phương thức cập nhật từ lớp BLL
            handleViolationBLL.updateHandleViolation(handleViolation);

            // Hiển thị thông báo cập nhật thành công
            JOptionPane.showMessageDialog(this, "Cập nhật thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Load lại dữ liệu vào bảng
            loadDataToTable();
            clearInputFields();
        } catch (Exception ex) {
            // Hiển thị thông báo lỗi nếu có lỗi xảy ra
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_button_update_handle_violationActionPerformed

    private void button_refresh_handle_violationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_refresh_handle_violationActionPerformed
        // TODO add your handling code here:
        clearInputFields();
        loadDataToTable();
    }//GEN-LAST:event_button_refresh_handle_violationActionPerformed

    private void button_close_handle_violationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_close_handle_violationActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_button_close_handle_violationActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        // Lấy ID của xử lý vi phạm cần xóa từ giao diện người dùng
        int handleViolationId = Integer.parseInt(text_handle_violation_id.getText());

        // Xác nhận xóa bằng hộp thoại xác nhận hoặc các biện pháp bảo mật khác nếu cần thiết
        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                // Gọi phương thức xóa từ lớp BLL
                handleViolationBLL.deleteHandleViolation(handleViolationId);

                // Hiển thị thông báo xóa thành công
                JOptionPane.showMessageDialog(this, "Xóa thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Load lại dữ liệu vào bảng sau khi xóa
                loadDataToTable();
                clearInputFields();
            } catch (Exception ex) {
                // Hiển thị thông báo lỗi nếu có lỗi xảy ra
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton button_add_handle_violation;
    private javax.swing.JButton button_close_handle_violation;
    private javax.swing.JButton button_refresh_handle_violation;
    private javax.swing.JButton button_update_handle_violation;
    private javax.swing.JComboBox<String> combobox_disposal_forms;
    private javax.swing.JComboBox<String> combobox_member;
    private com.toedter.calendar.JDateChooser date_handle_violation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable table_handle_violation;
    private javax.swing.JTextField text_disposal_forms;
    private javax.swing.JTextField text_find_handle_violation;
    private javax.swing.JTextField text_handle_violation_id;
    private javax.swing.JTextField text_member_id_listen;
    private javax.swing.JTextField text_money_handle_violation;
    // End of variables declaration//GEN-END:variables
}
