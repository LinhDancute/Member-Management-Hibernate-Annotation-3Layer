/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import BLL.DTO.member;
import BLL.member_BLL;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ACER
 */
public class member_management extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    private member_BLL mem_BLL;
    
    public member_management() {
        initComponents();
        try {
            mem_BLL = new member_BLL();
            show_database();
            filter_table();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //HIỂN THỊ DỮ LIỆU LÊN TABLE
    private void show_database() throws Exception {
        try {
            if (member_BLL.get_list_member() == null) {
                ArrayList<member> mem_List = mem_BLL.load_member();
                member_BLL.set_list_member(mem_List); 
            }
            insert_header();
            out_model(model, member_BLL.get_list_member());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }

    // Phương thức để lọc và hiển thị sinh viên dựa trên nội dung của textFind
    private void filter_table() {
        String keyword = text_find_member.getText().trim(); // Lấy từ khoá tìm kiếm từ textFind

        // Kiểm tra nếu từ khoá không rỗng
        if (!keyword.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) table_member.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table_member.setRowSorter(sorter);

            // Thiết lập bộ lọc để lọc dữ liệu dựa trên từ khoá
            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + keyword);
            sorter.setRowFilter(rowFilter);
        } else {
            // Nếu từ khoá rỗng, hiển thị tất cả dữ liệu
            table_member.setRowSorter(null);
        }
    }

    //CLEAR DỮ LIỆU TRÊN FIELD
    private void clear_all() {
        text_member_id.setText("");
        text_member_name.setText("");
        text_phone_number.setText("");
        combobox_department.setSelectedItem("");
        combobox_major.setSelectedItem("");
        text_password.setText("");
        text_email.setText("");
    }
    
    //LOAD LẠI DỮ LIỆU
    private void refresh_database() throws Exception {
        try {
            ArrayList<member> mem_List = mem_BLL.load_member();
            member_BLL.set_list_member(mem_List);
            insert_header();
            out_model(model, member_BLL.get_list_member());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu ",
                    "Thông Báo Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    //CHÈN HEADER
    private void insert_header() {
        Vector header = new Vector();
        header.add("STT");
        header.add("Mã thành viên");
        header.add("Họ tên");
        header.add("Số điện thoại");
        header.add("Ngành");
        header.add("Khoa");
        header.add("Mật khẩu");
        header.add("Email");

        model = new DefaultTableModel(header, 0);
    }
    
    // XUẤT RA TABLE TỪ ARRAYLIST
    private void out_model(DefaultTableModel model, ArrayList<member> member) 
    {
        Vector data;
        model.setRowCount(0);
        int stt = 1;  
        for (member m : member) {
            data = new Vector();
            data.add(stt++);  
            data.add(m.getMaTV());
            data.add(m.getHoTen());
            data.add(m.getSDT());
            data.add(m.getNganh());
            data.add(m.getKhoa());
            data.add(m.getPassword());
            data.add(m.getEmail());
            model.addRow(data);
        }
        table_member.setModel(model);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        text_find_member = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_member = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        text_member_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        text_member_name = new javax.swing.JTextField();
        text_phone_number = new javax.swing.JTextField();
        combobox_department = new javax.swing.JComboBox<>();
        combobox_major = new javax.swing.JComboBox<>();
        text_password = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        text_email = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        button_add_member = new javax.swing.JButton();
        button_update_member = new javax.swing.JButton();
        button_import_excel_member = new javax.swing.JButton();
        button_delete_member = new javax.swing.JButton();
        button_refresh_member = new javax.swing.JButton();
        button_close_member = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        combobox_d_course = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        combobox_d_major = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        combobox_d_department = new javax.swing.JComboBox<>();
        button_delete_all_member = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        jLabel1.setText("QUẢN LÝ THÀNH VIÊN");

        jLabel12.setText("Thông tin tìm kiếm:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(text_find_member)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(text_find_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        table_member.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã thành viên", "Họ tên", "Ngành", "Khoa", "Số điện thoại", "Mật khẩu", "Email"
            }
        ));
        table_member.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_memberMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_member);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Mã thành viên: ");

        jLabel4.setText("Họ tên:");

        jLabel5.setText("Khoa:");

        jLabel6.setText("Ngành:");

        jLabel7.setText("Số điện thoại:");

        text_phone_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_phone_numberActionPerformed(evt);
            }
        });

        combobox_department.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GDTH", "QLGD", "QTKD", "NT", "CNTT", "HTTT", "ATTT", "SP", "KT", "" }));

        combobox_major.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GDTH", "TLH", "QTKD", "CNTT", "SPA", "SPT", "KT", "KTPM", "NNA", "TT", "QTKS", "KDQT", "KDTM" }));

        text_password.setEnabled(false);

        jLabel8.setText("Email:");

        jLabel9.setText("Mật khẩu:");

        text_email.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(text_member_name)
                                    .addComponent(combobox_major, 0, 147, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(text_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(combobox_department, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(text_email)))
                    .addComponent(text_member_id))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(text_member_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(text_member_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(text_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(combobox_department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox_major, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(text_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addGap(16, 16, 16))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        button_add_member.setText("Thêm mới");
        button_add_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_add_memberActionPerformed(evt);
            }
        });

        button_update_member.setText("Cập nhật");
        button_update_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_memberActionPerformed(evt);
            }
        });

        button_import_excel_member.setText("Import excel");
        button_import_excel_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_import_excel_memberActionPerformed(evt);
            }
        });

        button_delete_member.setText("Xóa");
        button_delete_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_memberActionPerformed(evt);
            }
        });

        button_refresh_member.setText("Làm mới");
        button_refresh_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_refresh_memberActionPerformed(evt);
            }
        });

        button_close_member.setText("Đóng");
        button_close_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_close_memberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_close_member, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(button_import_excel_member, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_add_member, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_update_member, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_delete_member, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button_refresh_member, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_import_excel_member)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_add_member)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_update_member)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_delete_member)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_refresh_member)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_close_member)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Khóa:");

        jLabel10.setText("Ngành:");

        combobox_d_major.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GDTH", "TLH", "QTKD", "CNTT", "SPA", "SPT", "KT", "KTPM", "NNA", "TT", "QTKS", "KDQT", "KDTM" }));

        jLabel11.setText("Khoa:");

        combobox_d_department.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GDTH", "QLGD", "QTKD", "NT", "CNTT", "HTTT", "ATTT", "SP", "KT", "" }));

        button_delete_all_member.setText("Xóa tất cả");

        jLabel13.setText("CHỌN ĐIỀU KIỆN:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox_d_course, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox_d_major, 0, 156, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox_d_department, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(button_delete_all_member, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(button_delete_all_member)
                    .addComponent(combobox_d_course, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(combobox_d_major, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(combobox_d_department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_phone_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_phone_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_phone_numberActionPerformed

    private void button_delete_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_memberActionPerformed
        ArrayList<member> mem_list = new ArrayList<>();

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa khóa học này?",
                "Thông báo", JOptionPane.YES_NO_OPTION);
        if (confirm == 0)
        try {
            int member_id = Integer.parseInt(text_member_id.getText());

            mem_BLL.load_member();
            mem_BLL.delete_member(member_id);//gọi Layer BLL xoá 
            JOptionPane.showMessageDialog(this, "Xóa thành công",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            insert_header();// chèn header cho table
            out_model(model, member_BLL.get_list_member());// đổ data vào table
            clear_all();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể xoá khóa học này",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        } else
            return;
    }//GEN-LAST:event_button_delete_memberActionPerformed

    private void button_add_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_add_memberActionPerformed
        int member_id = Integer.parseInt(text_member_id.getText());
        String member_name = text_member_name.getText();
        String department = combobox_department.getSelectedItem().toString();
        String major = combobox_major.getSelectedItem().toString();
        String phone_number = text_phone_number.getText();
        String email = member_id + "@sv.sgu.edu.vn";
        String password = String.valueOf(member_id);

        try {
            member member = new member(member_id, member_name, department, major, phone_number, password, email);
            mem_BLL.add_member(member);
            JOptionPane.showMessageDialog(this, "Thêm mới thành viên thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clear_all();
            refresh_database();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể thêm mới thành viên",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button_add_memberActionPerformed

    private void button_update_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_update_memberActionPerformed
        int member_id = Integer.parseInt(text_member_id.getText());
        String member_name = text_member_name.getText();
        String department = combobox_department.getSelectedItem().toString();
        String major = combobox_major.getSelectedItem().toString();
        String phone_number = text_phone_number.getText();

        try {
            String email = text_email.getText();
            if (email == null || email.isEmpty()) {
                email = member_id + "@sv.sgu.edu.vn";
            }

            String password = text_password.getText();
            if (password == null || password.isEmpty()) {
                password = String.valueOf(member_id);
            }

            member member = new member(member_id, member_name, department, major, phone_number, password, email);
            mem_BLL.update_member(member_id, member);
            JOptionPane.showMessageDialog(this, "Cập nhật thành viên thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            clear_all();
            refresh_database();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật thành viên",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button_update_memberActionPerformed

    private void table_memberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_memberMouseClicked
        int selectedRow = table_member.getSelectedRow();
        clear_all();
        if (selectedRow >= 0) {
            if (table_member.getRowSorter() != null) {
                selectedRow = table_member.getRowSorter().convertRowIndexToModel(selectedRow);
            }

            // Print the entire row data for debugging
            System.out.print("Row data: ");
            for (int column = 0; column < table_member.getModel().getColumnCount(); column++) {
                Object value = table_member.getModel().getValueAt(selectedRow, column);
                if (value != null) {
                    System.out.print(value.toString() + " ");
                } else {
                    System.out.print("null ");
                }
            }
            System.out.println();

            text_member_id.setText(table_member.getModel().getValueAt(selectedRow, 1).toString());
            text_member_name.setText(table_member.getModel().getValueAt(selectedRow, 2).toString());
            text_phone_number.setText(table_member.getModel().getValueAt(selectedRow, 3).toString());
            combobox_major.setSelectedItem(table_member.getModel().getValueAt(selectedRow, 4));           
            combobox_department.setSelectedItem(table_member.getModel().getValueAt(selectedRow, 5));

            Object passwordValue = table_member.getModel().getValueAt(selectedRow, 6);
            text_password.setText(passwordValue != null ? passwordValue.toString() : "");

            Object emailValue = table_member.getModel().getValueAt(selectedRow, 7);
            text_email.setText(emailValue != null ? emailValue.toString() : "");
        }
    }//GEN-LAST:event_table_memberMouseClicked

    private void button_refresh_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_refresh_memberActionPerformed
        try {
            refresh_database();
            clear_all();
        } catch (Exception ex) {
            Logger.getLogger(member_management.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_refresh_memberActionPerformed

    private void button_close_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_close_memberActionPerformed
        setVisible(false);
    }//GEN-LAST:event_button_close_memberActionPerformed

    private void button_import_excel_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_import_excel_memberActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Allow selection of files only

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Call BLL method to import data from Excel file
                mem_BLL.import_excel(selectedFile);
                JOptionPane.showMessageDialog(this, "Data imported successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                refresh_database(); // Refresh the displayed data after importing
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error importing data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_button_import_excel_memberActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_add_member;
    private javax.swing.JButton button_close_member;
    private javax.swing.JButton button_delete_all_member;
    private javax.swing.JButton button_delete_member;
    private javax.swing.JButton button_import_excel_member;
    private javax.swing.JButton button_refresh_member;
    private javax.swing.JButton button_update_member;
    private javax.swing.JComboBox<String> combobox_d_course;
    private javax.swing.JComboBox<String> combobox_d_department;
    private javax.swing.JComboBox<String> combobox_d_major;
    private javax.swing.JComboBox<String> combobox_department;
    private javax.swing.JComboBox<String> combobox_major;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_member;
    private javax.swing.JTextField text_email;
    private javax.swing.JTextField text_find_member;
    private javax.swing.JTextField text_member_id;
    private javax.swing.JTextField text_member_name;
    private javax.swing.JTextField text_password;
    private javax.swing.JTextField text_phone_number;
    // End of variables declaration//GEN-END:variables
}
