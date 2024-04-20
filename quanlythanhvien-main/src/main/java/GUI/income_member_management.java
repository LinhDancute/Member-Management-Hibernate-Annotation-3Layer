/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import BLL.DTO.member;
import BLL.DTO.usage_information;
import BLL.usage_information_BLL;
import BLL.member_BLL;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ACER
 */
public class income_member_management extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    private usage_information_BLL information_BLL;
    private member_BLL member_BLL;
    
    public income_member_management() {
        initComponents();
        try {
            information_BLL = new usage_information_BLL();
            show_database();
            text_find_income.addActionListener((ActionEvent e) -> {
                filter_table();
            });
            
//            text_member_id.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {}
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    int member_id = Integer.parseInt(text_member_id.getText());
//        
//                    if (member_BLL.is_member_existed(member_id)) {
//                        String member_name = member_BLL.get_member_name(member_id);
//                        text_member_name_listen.setText(member_name);
//                    } else {
//                        JOptionPane.showMessageDialog(text_member_id, "Thành viên không tồn tại");
//                        text_member_name_listen.setText("");
//                    }
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {}
//        });
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //LỌC THÔNG TIN
    private void filter_table() {
        String keyword = text_find_income.getText().trim(); 

        if (!keyword.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) table_income_member.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table_income_member.setRowSorter(sorter);

            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + keyword);
            sorter.setRowFilter(rowFilter);
        } else {
            table_income_member.setRowSorter(null);
        }
    }

    //CLEAR DỮ LIỆU TRÊN FIELD
    private void clear_all() {
        text_usage_information_id.setText("");
        text_member_id.setText("");
        text_member_name_listen.setText("");
    }
    
    //HIỂN THỊ DỮ LIỆU LÊN TABLE
    private void show_database() throws Exception {
        try {
            if (usage_information_BLL.get_list_usage_information() == null) {
                ArrayList<usage_information> usage_information_list = information_BLL.load_usage_information();
                usage_information_BLL.set_list_usage_information(usage_information_list); 
            }
            insert_header();
            out_model(model, usage_information_BLL.get_list_usage_information());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }
    
    //LOAD LẠI DỮ LIỆU
    private void refresh_database() throws Exception {
        try {
            ArrayList<usage_information> usage_information_list = information_BLL.load_usage_information();
            usage_information_BLL.set_list_usage_information(usage_information_list);
            insert_header();
            out_model(model, usage_information_BLL.get_list_usage_information());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu ",
                    "Thông Báo Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    //CHÈN HEADER
    private void insert_header() {
        Vector header = new Vector();
        header.add("STT");
        header.add("Mã thông tin");
        header.add("Mã thành viên");
        header.add("Thời gian vào");

        model = new DefaultTableModel(header, 0);
        table_income_member.setModel(model);
    }
    
    // XUẤT RA TABLE TỪ ARRAYLIST
    private void out_model(DefaultTableModel model, ArrayList<usage_information> income_member) {
        if (income_member == null) {
            return;
        }

        Vector data;
        model.setRowCount(0);
        int stt = 1;
        for (usage_information i_m : income_member) {
            data = new Vector();
            data.add(stt++);
            data.add(i_m.getMaTT());

            if (i_m.getThanhvien() != null) {
                data.add(i_m.getThanhvien().getMaTV());
            } else {
                data.add(null); 
            }
            data.add(i_m.getTGVao());
            model.addRow(data);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_income_member = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        text_usage_information_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        text_member_id = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        text_member_name_listen = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        button_add_income = new javax.swing.JButton();
        button_delete_income = new javax.swing.JButton();
        button_refresh_income = new javax.swing.JButton();
        button_close_income = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        date_from_income = new com.toedter.calendar.JDateChooser();
        date_to_income = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        button_find_date_income = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        text_find_income = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        jLabel1.setText("QUẢN LÝ THÀNH VIÊN VÀO KHU HỌC TẬP");

        table_income_member.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_income_member.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_income_memberMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_income_member);

        jLabel3.setText("Mã thông tin:");

        text_usage_information_id.setEnabled(false);

        jLabel4.setText("Mã thành viên:");

        jLabel5.setText("Tên thành viên:");

        text_member_name_listen.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(text_usage_information_id))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_member_id, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(text_member_name_listen, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(text_usage_information_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(text_member_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(text_member_name_listen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        button_add_income.setText("Thêm mới vào");
        button_add_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_add_incomeActionPerformed(evt);
            }
        });

        button_delete_income.setText("Xóa");
        button_delete_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_incomeActionPerformed(evt);
            }
        });

        button_refresh_income.setText("Làm mới");
        button_refresh_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_refresh_incomeActionPerformed(evt);
            }
        });

        button_close_income.setText("Đóng");
        button_close_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_close_incomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(button_add_income))
                    .addComponent(button_refresh_income, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(button_delete_income, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(button_close_income, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_add_income)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_delete_income)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_refresh_income)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_close_income)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Từ:");

        jLabel8.setText("Đến:");

        button_find_date_income.setText("Lọc");

        jLabel2.setText("Nhập thông tin:");

        jLabel6.setText("CHỌN ĐIỀU KIỆN:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(date_from_income, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date_to_income, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button_find_date_income, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_find_income))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(text_find_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(date_to_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(date_from_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(button_find_date_income))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_refresh_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_refresh_incomeActionPerformed
        try {
            refresh_database();
            clear_all();
        } catch (Exception ex) {
            Logger.getLogger(member_management.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_refresh_incomeActionPerformed

    private void table_income_memberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_income_memberMouseClicked
        int selectedRow = table_income_member.getSelectedRow();
        clear_all();
        if (selectedRow >= 0) {
            if (table_income_member.getRowSorter() != null) {
                selectedRow = table_income_member.getRowSorter().convertRowIndexToModel(selectedRow);
            }

            System.out.print("Row data: ");
            for (int column = 0; column < table_income_member.getModel().getColumnCount(); column++) {
                Object value = table_income_member.getModel().getValueAt(selectedRow, column);
                if (value != null) {
                    System.out.print(value.toString() + " ");
                } else {
                    System.out.print("null ");
                }
            }
            text_usage_information_id.setText(table_income_member.getModel().getValueAt(selectedRow, 1).toString());
            text_member_id.setText(table_income_member.getModel().getValueAt(selectedRow, 2).toString());
            text_member_name_listen.setText(table_income_member.getModel().getValueAt(selectedRow, 3).toString());
        }
    }//GEN-LAST:event_table_income_memberMouseClicked

    private void button_add_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_add_incomeActionPerformed
        int usage_information_id = Integer.parseInt(text_usage_information_id.getText());
        int member_id = Integer.parseInt(text_member_id.getText());
        LocalDateTime time_in = LocalDateTime.now();
        member mem;
        try {
            usage_information income_member = new usage_information(usage_information_id, member_id, time_in);
            information_BLL.add_income_member_information(income_member);
            JOptionPane.showMessageDialog(this, "Thêm mới thành viên thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clear_all();
            refresh_database();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể thêm mới thành viên",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button_add_incomeActionPerformed

    private void button_delete_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_incomeActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa thông tin vào này?",
                "Thông báo", JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        try {
            int usage_information_id = Integer.parseInt(text_usage_information_id.getText());

            information_BLL.load_usage_information();
            information_BLL.delete_income_member_information(usage_information_id);
            JOptionPane.showMessageDialog(this, "Xóa thành công",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            insert_header();
            out_model(model, usage_information_BLL.get_list_usage_information());
            clear_all();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể xoá thông tin này",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        } else
            return;
    }//GEN-LAST:event_button_delete_incomeActionPerformed

    private void button_close_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_close_incomeActionPerformed
        setVisible(false);
    }//GEN-LAST:event_button_close_incomeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_add_income;
    private javax.swing.JButton button_close_income;
    private javax.swing.JButton button_delete_income;
    private javax.swing.JButton button_find_date_income;
    private javax.swing.JButton button_refresh_income;
    private com.toedter.calendar.JDateChooser date_from_income;
    private com.toedter.calendar.JDateChooser date_to_income;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_income_member;
    private javax.swing.JTextField text_find_income;
    private javax.swing.JTextField text_member_id;
    private javax.swing.JTextField text_member_name_listen;
    private javax.swing.JTextField text_usage_information_id;
    // End of variables declaration//GEN-END:variables
}
