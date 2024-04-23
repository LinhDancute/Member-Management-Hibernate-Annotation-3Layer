/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import BLL.DTO.usage_information;
import BLL.member_BLL;
import BLL.usage_information_BLL;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import BLL.DTO.handle_violations_DTO;
import BLL.violation_statistic_BLL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author ACER
 */
public class statistical extends javax.swing.JInternalFrame {
    private DefaultTableModel model;
    private member_BLL mem_BLL;
    private usage_information_BLL information_BLL;
    private final violation_statistic_BLL bll = new violation_statistic_BLL();
    

    public statistical() throws Exception {
        initComponents();
        information_BLL = new usage_information_BLL();
        mem_BLL = new member_BLL();
        mem_BLL = new member_BLL();
        show_database_member();
        text_find_statistical_member.addActionListener((ActionEvent e) -> {
            filter_table_member();
        });
        load_batch_member(combobox_statistical_batch);
        
        //LẮNG NGHE KHÓA -> HIỆN KHOA PHÙ HỢP
        combobox_statistical_batch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == combobox_statistical_batch) {
                        try {
                            String selected_department = combobox_statistical_batch.getSelectedItem().toString();
                            List<String> department_listen_list = mem_BLL.load_departments_by_batch(selected_department);
                            combobox_statistical_department.removeAllItems();
                            for (String department : department_listen_list) {
                                combobox_statistical_department.addItem(department);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(member_management.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }
    
    //HIỂN THỊ DỮ LIỆU BATCH lên COMBOBOX (INCOME MEMBER)
    public void load_batch_member(JComboBox cb) throws Exception {
        refresh_database_member();
        if (mem_BLL.get_list_member()== null) {
            mem_BLL.load_batch();
        }
        List<String> batch_list = mem_BLL.load_batch(); 
        for (String value : batch_list) {
            cb.addItem(value); 
        }
    }

    
    //EXPORT EXCEL (INCOME MEMBER)
    private boolean export_excel_member(String file_path, List<usage_information> usage_information) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Dữ liệu");
            
            Row labelRow = sheet.createRow(0);
            labelRow.createCell(0).setCellValue("STT");
            labelRow.createCell(1).setCellValue("Mã thông tin");
            labelRow.createCell(2).setCellValue("Mã thành viên");
            labelRow.createCell(3).setCellValue("Tên thành viên");
            labelRow.createCell(4).setCellValue("SDT");
            labelRow.createCell(5).setCellValue("Khoa");
            labelRow.createCell(6).setCellValue("Thời gian vào");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
            int rowIndex = 1;
            int stt = 1;
            for (usage_information income_member : usage_information) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue(income_member.getMaTT());
                row.createCell(2).setCellValue(income_member.getThanhvien().MaTV);
                row.createCell(3).setCellValue(income_member.getThanhvien().HoTen);
                row.createCell(4).setCellValue(income_member.getThanhvien().SDT);
                row.createCell(5).setCellValue(income_member.getThanhvien().Khoa);
                row.createCell(6).setCellValue(income_member.getTGVao());
                
                LocalDateTime date = income_member.getTGVao();
                String formatted_date = formatter.format(date);
                row.createCell(6).setCellValue(formatted_date);
            }
            
            try (FileOutputStream file_out = new FileOutputStream(file_path)) {
                workbook.write(file_out);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //HIỂN THỊ DỮ LIỆU LÊN TABLE (INCOME MEMBER)
    private void show_database_member() throws Exception {
        try {
            List<usage_information> usage_information_list = usage_information_BLL.get_list_usage_information();
            if (usage_information_list == null) {
                usage_information_list = information_BLL.load_usage_information();
                ArrayList<usage_information> usage_information_array_list = new ArrayList<>(usage_information_list);
                usage_information_BLL.set_list_usage_information(usage_information_array_list);
            }

            List<Object[]> usage_information_array_list = convert_usage_information_to_arraylist(usage_information_list);

            insert_header_member();
            out_model_member(model, usage_information_array_list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }

    //HIỂN THỊ DỮ LIỆU LÊN TABLE SAU KHI LỌC NGÀY
    private List<usage_information> statistical_member(Date start_date, Date end_date, String batch, String department) {
        List<usage_information> filtered_income_member = information_BLL.statistical_income_member(start_date, end_date, batch, department);
        model.setRowCount(0);
        List<Object[]> usage_information_array_list = convert_usage_information_to_arraylist(filtered_income_member);
        insert_header_member();
        out_model_member(model, usage_information_array_list);
        return filtered_income_member;
    }


    //LỌC THÔNG TIN (INCOME MEMBER)
    private void filter_table_member() {
        String keyword = text_find_statistical_member.getText().trim();

        if (!keyword.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) table_statistical_member.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table_statistical_member.setRowSorter(sorter);

            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + keyword);
            sorter.setRowFilter(rowFilter);
        } else {
            table_statistical_member.setRowSorter(null);
        }
    }

    //CLEAR DỮ LIỆU TRÊN FIELD (INCOME MEMBER)
    private void clear_all_member() {
        text_find_statistical_member.setText("");
        combobox_statistical_batch.setSelectedItem(null);
        date_statistical_from_income.setDate(null);
        date_statistical_to_income.setDate(null);
    }
    
    //LOAD LẠI DỮ LIỆU (INCOME MEMBER)
    private void refresh_database_member() throws Exception {
        try {
            usage_information_BLL.set_list_usage_information(information_BLL.load_usage_information());
            List<Object[]> usage_information_array_list = convert_usage_information_to_arraylist(information_BLL.load_usage_information());

            insert_header_member();
            out_model_member(model, usage_information_array_list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu ",
                    "Thông Báo Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //ĐƯA VỀ ARRAYLIST (INCOME MEMBER)
    private List<Object[]> convert_usage_information_to_arraylist(List<usage_information> usage_information_list) {
        List<Object[]> result = new ArrayList<>();
        for (usage_information usage : usage_information_list) {
            Object[] data = new Object[] {
                usage.getMaTT(),
                usage.getThanhvien().MaTV,
                usage.getThanhvien().HoTen,
                usage.getThanhvien().SDT,
                usage.getThanhvien().Khoa,
                usage.getTGVao()
            };
            result.add(data);
        }
        return result;
    }

    //CHÈN HEADER (INCOME MEMBER)
    private void insert_header_member() {
        Vector<String> header = new Vector<>();
        header.add("STT");
        header.add("Mã thông tin");
        header.add("Mã thành viên");
        header.add("Tên thành viên");
        header.add("SDT");
        header.add("Khoa");
        header.add("Thời gian vào");

        model = new DefaultTableModel(header, 0); 
        table_statistical_member.setModel(model); 
    }
    
    // XUẤT RA TABLE TỪ ARRAYLIST (INCOME MEMBER)
    private void out_model_member(DefaultTableModel model, List<Object[]> usage_information) {
        model.setRowCount(0); 
        int stt = 1;
        for (Object[] usage : usage_information) {
            Vector<Object> data = new Vector<>();
            data.add(stt++);
            data.add(usage[0]);
            data.add(usage[1]);
            data.add(usage[2]);
            data.add(usage[3]);
            data.add(usage[4]);
            data.add(usage[5]);
            model.addRow(data); 
        }
        loadDataToTable();
        combobox_statistical_status.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedStatus = combobox_statistical_status.getSelectedItem().toString();
            if (selectedStatus.equals("Đang xử lý")) {
                // Hiển thị tất cả thông tin
                loadDataToTable();
                text_statistical_money.setText("");
            } else if (selectedStatus.equals("Đã xử lý")) {
                // Hiển thị thông tin những xử lý có trạng thái là 0
                loadResolvedDataToTable();
                double totalMoney = violation_statistic_BLL.calculateTotalMoneyOfResolvedViolations();
                text_statistical_money.setText(String.valueOf(totalMoney));
            }
        }
     });
    }
    
   
    
    private void loadResolvedDataToTable(){
        // Trong hàm khởi tạo hoặc hàm khởi động của giao diện
        violation_statistic_BLL bll = new violation_statistic_BLL();
        ArrayList<handle_violations_DTO> resolvedViolations = bll.getResolvedHandleViolations();
        // Hiển thị dữ liệu trên bảng

        // Ví dụ về cách hiển thị dữ liệu lên bảng (cần điều chỉnh phù hợp với giao diện của bạn)
        DefaultTableModel model = (DefaultTableModel) table_statistical_handle_violations.getModel();
        model.setRowCount(0);
        for (handle_violations_DTO violation : resolvedViolations) {
            Object[] row = {violation.getMaXL(), violation.getHinhThucXL(), violation.getSoTien(), violation.getNgayXL()};
            model.addRow(row);
        }
        
    }
    private void loadDataToTable(){
        // Trong hàm khởi tạo hoặc hàm khởi động của giao diện
        violation_statistic_BLL bll = new violation_statistic_BLL();
        ArrayList<handle_violations_DTO> unresolvedViolations = bll.getUnresolvedHandleViolations();
        // Hiển thị dữ liệu trên bảng

        // Ví dụ về cách hiển thị dữ liệu lên bảng (cần điều chỉnh phù hợp với giao diện của bạn)
        DefaultTableModel model = (DefaultTableModel) table_statistical_handle_violations.getModel();
        model.setRowCount(0);
        for (handle_violations_DTO violation : unresolvedViolations) {
            Object[] row = {violation.getMaXL(), violation.getHinhThucXL(), violation.getSoTien(), violation.getNgayXL()};
            model.addRow(row);
        }
    }
    
    
    // Phương thức để xuất dữ liệu vào tệp Excel
    private boolean exportDataToExcel(String filePath, ArrayList<handle_violations_DTO> violations) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Dữ liệu");
            
            // Viết dữ liệu vào các ô trong bảng
            int rowIndex = 0;
            for (handle_violations_DTO violation : violations) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(violation.getMaXL());
                row.createCell(1).setCellValue(violation.getHinhThucXL());
                row.createCell(2).setCellValue(violation.getSoTien());
                // Tiếp tục thêm dữ liệu cho các cột khác nếu cần
            }
            
            // Lưu workbook vào tệp
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        text_find_statistical_member = new javax.swing.JTextField();
        button_statistical_member = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        date_statistical_from_income = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        date_statistical_to_income = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        combobox_statistical_batch = new javax.swing.JComboBox<>();
        combobox_statistical_department = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_statistical_member = new javax.swing.JTable();
        button_export_excel_statistical_member = new javax.swing.JButton();
        button_close_statistical_member = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        button_statistical_refresh = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        text_find_statistical_available_device = new javax.swing.JTextField();
        button_statistical_available_device = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        combobox_statistical_device_name = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        text_statistical_device_id = new javax.swing.JTextField();
        date_statistical_to_available_device = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        date_statistical_from_available_device = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_statistical_available_device = new javax.swing.JTable();
        button_statistical_export_excel_available_device = new javax.swing.JButton();
        button_close_statistical_device = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        text_find_statistical_device = new javax.swing.JTextField();
        button_statistical_device = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        date_statistical_to_device = new com.toedter.calendar.JDateChooser();
        jLabel22 = new javax.swing.JLabel();
        date_statistical_from_device = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_statistical_device = new javax.swing.JTable();
        button_statistical_export_excel_device = new javax.swing.JButton();
        button_close_device = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        combobox_statistical_status = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        text_statistical_money = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_statistical_handle_violations = new javax.swing.JTable();
        button_statistical_export_excel_handle_violations = new javax.swing.JButton();
        button_close_statistical_handle_violations = new javax.swing.JButton();

        jLabel1.setText("THỐNG KÊ");

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Thông tin:");

        button_statistical_member.setText("Thống kê");
        button_statistical_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_statistical_memberActionPerformed(evt);
            }
        });

        jLabel6.setText("CHỌN ĐIỀU KIỆN:");

        jLabel26.setText("Từ:");

        jLabel27.setText("Đến:");

        jLabel4.setText("Khóa:");

        jLabel10.setText("Khoa:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_find_statistical_member))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(30, 30, 30)
                                .addComponent(combobox_statistical_batch, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combobox_statistical_department, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date_statistical_from_income, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date_statistical_to_income, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button_statistical_member, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(text_find_statistical_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_statistical_member))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(combobox_statistical_batch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(combobox_statistical_department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26))
                    .addComponent(date_statistical_to_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_statistical_from_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(5, 5, 5))
        );

        table_statistical_member.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table_statistical_member);

        button_export_excel_statistical_member.setText("Export Excel");
        button_export_excel_statistical_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_export_excel_statistical_memberActionPerformed(evt);
            }
        });

        button_close_statistical_member.setText("Đóng");
        button_close_statistical_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_close_statistical_memberActionPerformed(evt);
            }
        });

        jLabel7.setText("SỐ LƯỢNG SINH VIÊN VÀO KHU HỌC TẬP ");

        button_statistical_refresh.setText("Toàn bộ dữ liệu");
        button_statistical_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    button_statistical_member_refreshActionPerformed(evt);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(button_export_excel_statistical_member)
                .addGap(135, 135, 135)
                .addComponent(button_statistical_refresh)
                .addGap(114, 114, 114)
                .addComponent(button_close_statistical_member, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(259, 259, 259))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_export_excel_statistical_member)
                    .addComponent(button_close_statistical_member)
                    .addComponent(button_statistical_refresh))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thành viên", jPanel2);

        jLabel8.setText("THIẾT BỊ ĐƯỢC MƯỢN - CÓ SẴN");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Thông tin:");

        button_statistical_available_device.setText("Thống kê");

        jLabel11.setText("Mã thiết bị:");

        jLabel12.setText("Tên thiết bị:");

        jLabel13.setText("CHỌN ĐIỀU KIỆN:");

        text_statistical_device_id.setEnabled(false);

        jLabel24.setText("Đến:");

        jLabel25.setText("Từ:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date_statistical_from_available_device, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(date_statistical_to_available_device, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox_statistical_device_name, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_statistical_device_id, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_statistical_available_device))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_find_statistical_available_device)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(text_find_statistical_available_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combobox_statistical_device_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date_statistical_to_available_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(date_statistical_from_available_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(text_statistical_device_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button_statistical_available_device))
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        table_statistical_available_device.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table_statistical_available_device);

        button_statistical_export_excel_available_device.setText("Export Excel");

        button_close_statistical_device.setText("Đóng");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(button_statistical_export_excel_available_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_close_statistical_device)
                .addGap(199, 199, 199))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_statistical_export_excel_available_device)
                    .addComponent(button_close_statistical_device))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thiết bị được mượn", jPanel3);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Thông tin:");

        button_statistical_device.setText("Thống kê");

        jLabel18.setText("CHỌN ĐIỀU KIỆN:");

        jLabel22.setText("Đến:");

        jLabel23.setText("Từ:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(date_statistical_from_device, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date_statistical_to_device, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(button_statistical_device, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(text_find_statistical_device, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(text_find_statistical_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_statistical_device)
                    .addComponent(jLabel15)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel22)
                        .addComponent(date_statistical_to_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23))
                    .addComponent(date_statistical_from_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        table_statistical_device.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(table_statistical_device);

        button_statistical_export_excel_device.setText("Export Excel");

        button_close_device.setText("Đóng");

        jLabel19.setText("THIẾT BỊ ĐANG ĐƯỢC MƯỢN - KHÔNG CÓ SẴN");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(button_statistical_export_excel_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_close_device)
                .addGap(173, 173, 173))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addGap(255, 255, 255))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_statistical_export_excel_device)
                    .addComponent(button_close_device))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thiết bị đang được mượn", jPanel4);

        jLabel16.setText("XỬ LÝ VI PHẠM");

        jLabel17.setText("CHỌN ĐIỀU KIỆN:");

        jLabel20.setText("Tình trạng xử lý:");

        combobox_statistical_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang xử lý"  ,"Đã xử lý"}));

        jLabel21.setText("Tổng bồi thường:");

        text_statistical_money.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(combobox_statistical_status, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_statistical_money, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(combobox_statistical_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(text_statistical_money, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        table_statistical_handle_violations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã xử lý", "Hình thức xử lý", "Số tiền", "Ngày xử lý"
            }
        ));
        jScrollPane4.setViewportView(table_statistical_handle_violations);

        button_statistical_export_excel_handle_violations.setText("Export Excel");
        button_statistical_export_excel_handle_violations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_statistical_export_excel_handle_violationsActionPerformed(evt);
            }
        });

        button_close_statistical_handle_violations.setText("Đóng");
        button_close_statistical_handle_violations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_close_statistical_handle_violationsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(button_statistical_export_excel_handle_violations)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_close_statistical_handle_violations)
                .addGap(207, 207, 207))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_statistical_export_excel_handle_violations)
                    .addComponent(button_close_statistical_handle_violations))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Xử lý vi phạm", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_close_statistical_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_close_statistical_memberActionPerformed
        setVisible(false);
    }//GEN-LAST:event_button_close_statistical_memberActionPerformed
    private void button_close_statistical_handle_violationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_close_statistical_handle_violationsActionPerformed
        this.dispose();
    }//GEN-LAST:event_button_close_statistical_handle_violationsActionPerformed

    private void button_statistical_export_excel_handle_violationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_statistical_export_excel_handle_violationsActionPerformed
        // TODO add your handling code here:
        // Khởi tạo JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu tệp");
        
        // Tạo bộ lọc để chỉ hiển thị các tệp Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(filter);
        
        // Hiển thị hộp thoại chọn tệp và lấy đường dẫn của thư mục lưu
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            // Kiểm tra phần mở rộng của tệp
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx"; // Nếu không có phần mở rộng, tự động thêm phần mở rộng .xlsx
            }
            
            // Lấy dữ liệu từ BLL
            ArrayList<handle_violations_DTO> violations = bll.getViolationStatistics(1); // Thay 1 bằng trạng thái mong muốn
            
            // Xuất dữ liệu vào tệp Excel
            boolean exportSuccess = exportDataToExcel(filePath, violations);
            
            // Hiển thị thông báo kết quả cho người dùng
            if (exportSuccess) {
                JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Xuất dữ liệu không thành công.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_button_statistical_export_excel_handle_violationsActionPerformed

    private void button_export_excel_statistical_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_export_excel_statistical_memberActionPerformed
        try {
            String file_path = "";
            JFileChooser file_chooser = new JFileChooser();
            file_chooser.setDialogTitle("Chọn nơi lưu tệp");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
            file_chooser.setFileFilter(filter);
            int user_selection = file_chooser.showSaveDialog(this);
            if (user_selection == JFileChooser.APPROVE_OPTION) {
                File file_to_save = file_chooser.getSelectedFile();
                file_path = file_to_save.getAbsolutePath();

                File existing_file = new File(file_path);
                if (existing_file.exists()) {
                    int response = JOptionPane.showConfirmDialog(this, "Tệp đã tồn tại. Bạn có muốn ghi đè lên tệp hiện có không?", "Xác nhận ghi đè", JOptionPane.YES_NO_OPTION);
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                if (!file_path.toLowerCase().endsWith(".xlsx")) {
                    file_path += ".xlsx";
                }

                Date start_date = date_statistical_from_income.getDate();
                Date end_date = date_statistical_to_income.getDate();
                String batch = combobox_statistical_batch.getSelectedItem().toString();
                String department = combobox_statistical_department.getSelectedItem() != null ? combobox_statistical_department.getSelectedItem().toString() : null;

                List<usage_information> filtered_data;

                if (start_date == null && end_date == null && department == null) {
                    ArrayList<usage_information> income_member = (ArrayList<usage_information>) information_BLL.export_excel();
                    boolean export_success = export_excel_member(file_path, income_member);
                    if (export_success) {
                        JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Xuất dữ liệu không thành công.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                } else if (start_date != null && end_date != null && start_date.before(end_date)) {
                    LocalDate startDate = start_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate endDate = end_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    filtered_data = statistical_member(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            batch, department);
                } else {
                    JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean export_success = export_excel_member(file_path, filtered_data);

                if (export_success) {
                    JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Xuất dữ liệu không thành công.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(income_member_management.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_export_excel_statistical_memberActionPerformed

    private void button_statistical_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_statistical_memberActionPerformed
        try {
            Date start_date = date_statistical_from_income.getDate();
            Date end_date = date_statistical_to_income.getDate();
            String department = combobox_statistical_batch.getSelectedItem().toString();
            String major = combobox_statistical_department.getSelectedItem().toString();

            if (start_date == null && end_date == null) {
                LocalDate currentDate = LocalDate.now();
                Date today = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                statistical_member(today, today, department, major);
            } else if (start_date != null && end_date != null && start_date.before(end_date)) {
                statistical_member(start_date, end_date, department, major);
            } else {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            Logger.getLogger(income_member_management.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_statistical_memberActionPerformed

    private void button_statistical_member_refreshActionPerformed(java.awt.event.ActionEvent evt) throws Exception {//GEN-FIRST:event_button_statistical_refreshActionPerformed
        refresh_database_member();
    }//GEN-LAST:event_button_statistical_refreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_close_device;
    private javax.swing.JButton button_close_statistical_device;
    private javax.swing.JButton button_close_statistical_handle_violations;
    private javax.swing.JButton button_close_statistical_member;
    private javax.swing.JButton button_export_excel_statistical_member;
    private javax.swing.JButton button_statistical_available_device;
    private javax.swing.JButton button_statistical_device;
    private javax.swing.JButton button_statistical_export_excel_available_device;
    private javax.swing.JButton button_statistical_export_excel_device;
    private javax.swing.JButton button_statistical_export_excel_handle_violations;
    private javax.swing.JButton button_statistical_member;
    private javax.swing.JButton button_statistical_refresh;
    private javax.swing.JComboBox<String> combobox_statistical_batch;
    private javax.swing.JComboBox<String> combobox_statistical_department;
    private javax.swing.JComboBox<String> combobox_statistical_device_name;
    private javax.swing.JComboBox<String> combobox_statistical_status;
    private com.toedter.calendar.JDateChooser date_statistical_from_available_device;
    private com.toedter.calendar.JDateChooser date_statistical_from_device;
    private com.toedter.calendar.JDateChooser date_statistical_from_income;
    private com.toedter.calendar.JDateChooser date_statistical_to_available_device;
    private com.toedter.calendar.JDateChooser date_statistical_to_device;
    private com.toedter.calendar.JDateChooser date_statistical_to_income;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable table_statistical_available_device;
    private javax.swing.JTable table_statistical_device;
    private javax.swing.JTable table_statistical_handle_violations;
    private javax.swing.JTable table_statistical_member;
    private javax.swing.JTextField text_find_statistical_available_device;
    private javax.swing.JTextField text_find_statistical_device;
    private javax.swing.JTextField text_find_statistical_member;
    private javax.swing.JTextField text_statistical_device_id;
    private javax.swing.JTextField text_statistical_money;
    // End of variables declaration//GEN-END:variables
}
