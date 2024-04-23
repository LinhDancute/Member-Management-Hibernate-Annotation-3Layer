/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import BLL.DTO.device;
import BLL.DTO.member;
import BLL.DTO.usage_information;
import BLL.member_BLL;
import BLL.usage_information_BLL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author ACER
 */
public class usage_information_management extends javax.swing.JInternalFrame {

    /**
     * Creates new form usage_information_management
     */
    private DefaultTableModel model;
    private usage_information_BLL usageInformationBll;
    private member_BLL memberBll;

    public usage_information_management() {
        initComponents();
        try {
            usageInformationBll = new usage_information_BLL();
            memberBll = new member_BLL();
            show_database();
            updateDeviceCombobox();
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void show_database () throws Exception{
        try{
            if (usage_information_BLL.get_usage_information_list() == null){
                ArrayList<usage_information> usage_information_list = usageInformationBll.load_usage_information_list();
                usage_information_BLL.setUsage_information_list(usage_information_list);
            }
            insert_header();
            out_model(model, usage_information_BLL.get_usage_information_list());
        }catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }

    private void insert_header() {
        Vector header = new Vector();
        header.add("Mã thông tin");
        header.add("Mã thành viên");
        header.add("Mã thiết bị");
        header.add("Thời gian vào");
        header.add("Thời gian mượn");
        header.add("Thời gian trả");
        header.add("Thời gian đặt chỗ");

        model = new DefaultTableModel(header, 0);
    }

    private void out_model(DefaultTableModel model, ArrayList<usage_information> usage_information){
        Vector data;
        model.setRowCount(0);
        for (usage_information usage : usage_information){
            data = new Vector();
            data.add(usage.getMaTT());
            data.add(usage.getThanhvien().MaTV);
            if (usage.getThietbi() == null){
                data.add("");
            }else
                data.add(usage.getThietbi().MaTB);
            data.add(usage.getTGVao());
            data.add(usage.getTGMuon());
            data.add(usage.getTGTra());
            data.add(usage.getTGDatcho());

            model.addRow(data);
        }
        table_usage_information.setModel(model);
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
        text_find_usage_information = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        date_from_income = new com.toedter.calendar.JDateChooser();
        date_to_income = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        button_find_date_income = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_usage_information = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        text_service_id1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        text_member_id_listen = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        combobox_service_name = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        text_service_id_listen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        textThoiGianVao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textThoiGianMuon = new javax.swing.JTextField();
        textThoiGianTra = new javax.swing.JTextField();
        text_member_name = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        button_add_usage_information = new javax.swing.JButton();
        button_TraThietBi = new javax.swing.JButton();
        button_import_excel_service1 = new javax.swing.JButton();
        button_refresh_service1 = new javax.swing.JButton();
        button_close_service = new javax.swing.JButton();

        jLabel18.setText("QUẢN LÝ THÔNG TIN SỬ DỤNG");

        jLabel19.setText("Thông tin tìm kiếm:");

        jLabel7.setText("Từ:");

        jLabel8.setText("Đến:");

        button_find_date_income.setText("Lọc");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(text_find_usage_information))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(date_from_income, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date_to_income, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button_find_date_income, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(text_find_usage_information, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(date_to_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(date_from_income, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(button_find_date_income))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_usage_information.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã thông tin", "Tên thành viên", "Tên thiết bị", "Thời gian vào", "Thời gian mượn", "Thời gian trả", "Thời gian đặt chỗ"
            }
        ));
        table_usage_information.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_usage_information);

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setText("Mã thông tin: ");

        text_service_id1.setEnabled(false);

        jLabel22.setText("Tên thành viên:");

        jLabel24.setText("Mã thành viên:");

        text_member_id_listen.setEnabled(false);
        text_member_id_listen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_member_id_listenActionPerformed(evt);
            }
        });

        jLabel27.setText("Tên thiết bị:");

        combobox_service_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_service_nameActionPerformed(evt);
            }
        });

        jLabel28.setText("Mã thiết bị:");

        text_service_id_listen.setEnabled(false);
        text_service_id_listen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_service_id_listenActionPerformed(evt);
            }
        });

        jLabel1.setText("Thời gian vào:");

        textThoiGianVao.setEnabled(false);

        jLabel2.setText("Thời gian đặt chỗ:");

        jLabel3.setText("Thời gian mượn:");

        jLabel4.setText("Thời gian trả:");

        textThoiGianMuon.setEnabled(false);

        textThoiGianTra.setEnabled(false);

        text_member_name.setEnabled(false);
        text_member_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_member_nameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel27))
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(text_service_id1))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(combobox_service_name, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel18Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(text_member_name)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel28))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_member_id_listen)
                                    .addComponent(text_service_id_listen)))))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textThoiGianMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(textThoiGianVao, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(textThoiGianTra, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(text_service_id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24)
                    .addComponent(text_member_id_listen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_member_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(text_service_id_listen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox_service_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(textThoiGianVao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(textThoiGianMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(textThoiGianTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        button_add_usage_information.setText("Mượn thiết bị");
        button_add_usage_information.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_add_usage_informationActionPerformed(evt);
            }
        });

        button_TraThietBi.setText("Trả thiết bị");
        button_TraThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_TraThietBi(evt);
            }
        });

        button_import_excel_service1.setText("Import excel");

        button_refresh_service1.setText("Làm mới");
        button_refresh_service1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_refresh_service1(evt);
            }
        });

        button_close_service.setText("Đóng");
        button_close_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_add_usage_information, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_import_excel_service1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_TraThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_refresh_service1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_close_service, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_import_excel_service1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_add_usage_information)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_TraThietBi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_refresh_service1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_close_service)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(264, 264, 264)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void updateDeviceCombobox(){
        if (usageInformationBll != null){
            usageInformationBll.update_device_combobox(combobox_service_name);
        }else {
            System.out.println("BLL is null!");
        }
    }

    private void table_MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = table_usage_information.getSelectedRow();
        if (selectedRow != -1) {
            if (table_usage_information.getRowSorter() != null){
                selectedRow = table_usage_information.getRowSorter().convertRowIndexToModel(selectedRow);
            }
        }

        //check value get from selected row
        System.out.println("Row selected: ");
        for (int column = 0; column < table_usage_information.getColumnCount(); column++) {
            Object value = table_usage_information.getValueAt(selectedRow, column);
            if (value != null){
                System.out.println(value.toString() + " ");
            }else{
                System.out.println("null");
            }
        }
        System.out.println();

        text_service_id1.setText(table_usage_information.getValueAt(selectedRow, 0).toString());
        text_member_id_listen.setText(table_usage_information.getValueAt(selectedRow, 1).toString());
        int memberID = Integer.parseInt(table_usage_information.getValueAt(selectedRow, 1).toString());
        try {
            String memberName = memberBll.getMemberNameById(memberID);
            text_member_name.setText(memberName.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (table_usage_information.getValueAt(selectedRow, 2) != ""){
            text_service_id_listen.setText(table_usage_information.getValueAt(selectedRow, 2).toString());
            int deviceId = Integer.parseInt(table_usage_information.getValueAt(selectedRow, 2).toString());
            String deviceName = usageInformationBll.getDeviceNameById(deviceId);
            combobox_service_name.setSelectedItem(deviceName.toString());
        }else {
            text_service_id_listen.setText("");
        }

        if (table_usage_information.getValueAt(selectedRow, 3) != null){
            textThoiGianVao.setText(table_usage_information.getValueAt(selectedRow, 3).toString());
        }else {
            textThoiGianVao.setText("");
        }

        if (table_usage_information.getValueAt(selectedRow, 4) != null){
            textThoiGianMuon.setText(table_usage_information.getValueAt(selectedRow, 4).toString());
        }else {
            textThoiGianMuon.setText("");
        }

        if (table_usage_information.getValueAt(selectedRow,5) != null){
            textThoiGianTra.setText(table_usage_information.getValueAt(selectedRow, 5).toString());
        }else {
            textThoiGianTra.setText("");
        }
    }

    private void refresh_database() throws Exception{
        try {
            ArrayList<usage_information> usageInformationArrayList = usageInformationBll.load_usage_information_list();
            usageInformationBll.setUsage_information_list(usageInformationArrayList);
            insert_header();
            out_model(model, usage_information_BLL.get_usage_information_list());
        }catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Không thể load dữ liệu ",
                    "Thông Báo Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_TraThietBi(java.awt.event.ActionEvent evt) {
        usage_information obj       =   new usage_information();
        Date currentTime            =   new Date();
        SimpleDateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String MaTT                 =   text_service_id1.getText();
        String TimeReturn           =   dateFormat.format(currentTime);

        obj.setMaTT(Integer.parseInt(MaTT));

        try {
            obj.setTGTra(dateFormat.parse(TimeReturn));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            usage_information_BLL usageBLL = new usage_information_BLL();
            usageBLL.update_return_device_time(obj.MaTT, obj.getTGTra());
            JOptionPane.showMessageDialog(this, "Trả thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            textThoiGianTra.setText(TimeReturn.toString());
            refresh_database();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể trả",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void button_add_usage_informationActionPerformed(java.awt.event.ActionEvent evt) {
        usage_information obj       =   new usage_information();
        Date currentTime            =   new Date();
        SimpleDateFormat dateFormat =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String MaTT                 =   text_service_id1.getText();
        String MaTB                 =   text_member_id_listen.getText();
        String TenTB                =   combobox_service_name.getSelectedItem().toString();
        String TimeIn               =   textThoiGianVao.getText();
        String TimeBorrow           =   dateFormat.format(currentTime);
        System.out.println(TimeBorrow);

        device deviceObj = new device();
        deviceObj.setTenTB(TenTB);

        obj.setMaTT(Integer.parseInt(MaTT));
        obj.setThietbi(deviceObj);

        try {
            obj.setTGVao(dateFormat.parse(TimeIn));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            obj.setTGMuon(dateFormat.parse(TimeBorrow));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            usage_information_BLL usageBLL = new usage_information_BLL();
            usageBLL.update_usage_information(obj.MaTT, obj.getThietbi().TenTB, obj.getTGMuon());
            JOptionPane.showMessageDialog(this, "Mượn thành công",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            refresh_database();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể mượn",
                    "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void combobox_service_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox_service_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combobox_service_nameActionPerformed

    private void text_service_id_listenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_service_id_listenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_service_id_listenActionPerformed

    private void text_member_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_member_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_member_nameActionPerformed

    private void button_refresh_service1(java.awt.event.ActionEvent evt) {
        text_service_id1.setText("");
        text_member_name.setText("");
        text_service_id_listen.setText("");
        text_member_id_listen.setText("");
        text_service_id_listen.setText("");
        textThoiGianVao.setText("");
        textThoiGianMuon.setText("");
        textThoiGianTra.setText("");
    }

    private void button_import_excel_usage_information_ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_add_usage_information;
    private javax.swing.JButton button_close_service;
    private javax.swing.JButton button_find_date_income;
    private javax.swing.JButton button_import_excel_service1;
    private javax.swing.JButton button_refresh_service1;
    private javax.swing.JButton button_TraThietBi;
    private javax.swing.JComboBox<String> combobox_service_name;
    private com.toedter.calendar.JDateChooser date_from_income;
    private com.toedter.calendar.JDateChooser date_to_income;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField textThoiGianVao;
    private javax.swing.JTextField textThoiGianMuon;
    private javax.swing.JTextField textThoiGianTra;
    private javax.swing.JTable table_usage_information;
    private javax.swing.JTextField text_find_usage_information;
    private javax.swing.JTextField text_member_id_listen;
    private javax.swing.JTextField text_member_name;
    private javax.swing.JTextField text_service_id1;
    private javax.swing.JTextField text_service_id_listen;
    // End of variables declaration//GEN-END:variables
}
