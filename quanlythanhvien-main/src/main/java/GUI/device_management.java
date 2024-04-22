/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package GUI;

import BLL.DTO.device;
import BLL.device_BLL;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.PlainDocument;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ACER
 */
public class device_management extends javax.swing.JInternalFrame {
    
    private final device_BLL deviceBLL = new device_BLL();



    /**
     * Creates new form service_management
     */
    public device_management() {
        initComponents();
        loadDevicesToTable();
        
        text_device_id.setDocument(new PlainDocument());
        ((AbstractDocument) text_device_id.getDocument()).setDocumentFilter(new IntegerFiiter());

         
        updateComboBox();
        // Gắn sự kiện lắng nghe khi chọn hàng trong bảng
        table_device.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = table_device.getSelectedRow();
                if (selectedRow != -1) { // Đảm bảo rằng có ít nhất một hàng được chọn
                    showDeviceInfo(selectedRow);
                }
            }
        });
        
        
        cbCategory_device.addActionListener((ActionEvent e) -> {
            String selectedPrefix = (String) cbCategory_device.getSelectedItem(); // Lấy tiền tố được chọn từ comboBox
            try {
                filterDevicesByPrefix(selectedPrefix); // Lọc dữ liệu và cập nhật bảng
            } catch (Exception ex) {
                Logger.getLogger(device_management.class.getName()).log(Level.SEVERE, null, ex);
            }
        });  
       
    }
    
    private void cbCategory_deviceActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        String selectedPrefix = (String) cbCategory_device.getSelectedItem();
        if (selectedPrefix != null) {
            filterDevicesByPrefix(selectedPrefix);
        }
    }

    private void filterDevicesByPrefix(String prefix) throws Exception {
        DefaultTableModel model = (DefaultTableModel) table_device.getModel();
        model.setRowCount(0);   
        try {
            ArrayList<device> devices = deviceBLL.getDevicesByPrefix(prefix); // Lấy danh sách thiết bị dựa trên tiền tố
            for (device d : devices) {
                // Thêm các dòng mới vào bảng hiển thị
                model.addRow(new Object[]{d.getMaTB(), d.getTenTB(), d.getMotaTB()});
            }
        } catch (Exception ex) {
            // Xử lý lỗi khi không thể lấy danh sách thiết bị dựa trên tiền tố
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc thiết bị theo tiền tố: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Cập nhật dữ liệu cho comboBox
    private void updateComboBox() {
//        cbCategory_device.removeAllItems();
        // Thêm một dòng thông báo vào đầu comboBox
         cbCategory_device.addItem(""); // Dòng trống hoặc thông báo
        // Lấy danh sách số đầu tiên của các mã thiết bị
        ArrayList<String> prefixes = getDevicePrefixes();

        // Loại bỏ các giá trị trùng lặp
        Set<String> uniquePrefixes = new LinkedHashSet<>(prefixes);

        // Chuyển danh sách đã loại bỏ vào mảng để cập nhật vào comboBox
        String[] prefixArray = uniquePrefixes.toArray(new String[0]);

        // Cập nhật comboBox với mảng dữ liệu mới
        cbCategory_device.setModel(new DefaultComboBoxModel<>(prefixArray));

        // Chọn số đầu tiên làm giá trị mặc định nếu danh sách không trống
        if (prefixArray.length > 0) {
            cbCategory_device.setSelectedIndex(0);
        }
    }
    
    // Lấy danh sách số đầu tiên của các mã thiết bị từ bảng
    private ArrayList<String> getDevicePrefixes() {
        ArrayList<String> prefixes = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table_device.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String deviceID = (String) model.getValueAt(i, 0); // Lấy mã thiết bị từ hàng đã chọn
            String prefix = deviceID.substring(0, 1); // Lấy số đầu tiên của mã thiết bị
            prefixes.add(prefix);
        }
        return prefixes;
    }

    
    private void loadDevicesByCategoryId(int categoryId) {
        DefaultTableModel model = (DefaultTableModel) table_device.getModel();
        model.setRowCount(0); // Clear table

        ArrayList<device> devices = deviceBLL.getAllDevices();
        for (device d : devices) {
            if (String.valueOf(d.getMaTB()).startsWith(String.valueOf(categoryId))) {
                model.addRow(new Object[]{
                    d.getMaTB(),
                    d.getTenTB(),
                    d.getMotaTB()
                });
            }
        }
    }

    private void showDeviceInfo(int row) {
        DefaultTableModel model = (DefaultTableModel) table_device.getModel();
        String deviceID = String.valueOf(model.getValueAt(row, 0)); // Lấy mã thiết bị từ hàng đã chọn
        device selectedDevice = null;

        // Lấy thông tin của thiết bị đã chọn từ BLL
        ArrayList<device> devices = deviceBLL.getAllDevices();
        for (device d : devices) {
            if (d.getMaTB().equals(deviceID)) {
                selectedDevice = d;
                break;
            }
        }

        // Hiển thị thông tin của thiết bị đã chọn trên các textfield
        if (selectedDevice != null) {
            text_device_id.setText(selectedDevice.getMaTB());
            text_device_name.setText(selectedDevice.getTenTB());
            text_device_description.setText(selectedDevice.getMotaTB());
        }
    }

        
    
    private void loadDevicesToTable() {
        DefaultTableModel model = (DefaultTableModel) table_device.getModel();
        model.setRowCount(0); // Clear table

        ArrayList<device> devices = deviceBLL.getAllDevices();
        for (device d : devices) {
            model.addRow(new Object[]{
                d.getMaTB(),
                d.getTenTB(),
                d.getMotaTB()
            });
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        text_find_device = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_device = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        text_device_id = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        text_device_name = new javax.swing.JTextField();
        text_device_description = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        button_add_device = new javax.swing.JButton();
        button_update_device = new javax.swing.JButton();
        button_import_excel_device = new javax.swing.JButton();
        button_delete_device = new javax.swing.JButton();
        button_refresh_device = new javax.swing.JButton();
        button_close_device = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        button_delete_all_device = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        cbCategory_device = new javax.swing.JComboBox<>();

        jLabel14.setText("QUẢN LÝ THIẾT BỊ");

        jLabel15.setText("Thông tin tìm kiếm:");

        text_find_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_find_deviceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(text_find_device, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(text_find_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        table_device.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã thiết bị", "Tên thiết bị", "Mô tả "
            }
        ));
        jScrollPane2.setViewportView(table_device);

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setText("Mã thiết bị: ");

        text_device_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_device_idActionPerformed(evt);
            }
        });

        jLabel17.setText("Tên thiết bị:");

        jLabel20.setText("Mô tả:");

        text_device_description.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_device_descriptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(text_device_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(text_device_description, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(text_device_id, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(text_device_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(text_device_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(text_device_description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        button_add_device.setText("Thêm mới");
        button_add_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_add_deviceActionPerformed(evt);
            }
        });

        button_update_device.setText("Cập nhật");
        button_update_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_deviceActionPerformed(evt);
            }
        });

        button_import_excel_device.setText("Import excel");
        button_import_excel_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_import_excel_deviceActionPerformed(evt);
            }
        });

        button_delete_device.setText("Xóa");
        button_delete_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_deviceActionPerformed(evt);
            }
        });

        button_refresh_device.setText("Làm mới");
        button_refresh_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_refresh_deviceActionPerformed(evt);
            }
        });

        button_close_device.setText("Đóng");
        button_close_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_close_deviceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_import_excel_device, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_add_device, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_update_device, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_delete_device, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_refresh_device, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_close_device, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_import_excel_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_add_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_update_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_delete_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_refresh_device)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_close_device)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel23.setText("Loại thiết bị:");

        button_delete_all_device.setText("Xóa tất cả");
        button_delete_all_device.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_delete_all_deviceActionPerformed(evt);
            }
        });

        jLabel26.setText("CHỌN ĐIỀU KIỆN:");

        cbCategory_device.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(258, 258, 258))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel23)
                        .addGap(51, 51, 51)
                        .addComponent(cbCategory_device, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(button_delete_all_device, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(button_delete_all_device)
                    .addComponent(cbCategory_device, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 748, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 10, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     private void clearFields() {
        text_device_id.setText("");
        text_device_name.setText("");
        text_device_description.setText("");
        cbCategory_device.addItem("");
        updateComboBox();
        text_find_device.setText("");

    }
    private void text_device_descriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_device_descriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_device_descriptionActionPerformed

    private void button_delete_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_deviceActionPerformed
        // TODO add your handling code here:
        try {
            int selectedRow = table_device.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) table_device.getModel();
                String deviceID = (String) model.getValueAt(selectedRow, 0); // Lấy mã thiết bị từ hàng đã chọn
                
                // Xác nhận xóa thiết bị
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thiết bị này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deviceBLL.deleteDevice(deviceID);
                    model.removeRow(selectedRow); // Xóa hàng khỏi bảng
                    clearFields(); // Xóa nội dung trên các textfield
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một thiết bị để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa thiết bị: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button_delete_deviceActionPerformed

    private void text_device_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_device_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_device_idActionPerformed

    private void button_add_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_add_deviceActionPerformed
        // TODO add your handling code here:
       
        try {
            String deviceID = text_device_id.getText();
            String deviceName = text_device_name.getText();
            String deviceDescription = text_device_description.getText();
            
            // Kiểm tra xem tên và mô tả thiết bị có được nhập hay không
            if(deviceName.isEmpty() || deviceDescription.isEmpty() || deviceID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã tên và mô tả thiết bị không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem mã thiết bị đã tồn tại chưa
            for (device d : deviceBLL.getAllDevices()) {
                if (d.getMaTB().equals(deviceID)) {
                    JOptionPane.showMessageDialog(this, "Mã thiết bị đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return; // Kết thúc phương thức nếu mã đã tồn tại
                }
            }

            device newDevice = new device(deviceID, deviceName, deviceDescription);

            deviceBLL.addDevice(newDevice);

            loadDevicesToTable();
            
            // Sau khi thêm thành công, làm trống các textfield
            clearFields();
            JOptionPane.showMessageDialog(this, "Device added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid device ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding device: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button_add_deviceActionPerformed

    private void button_close_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_close_deviceActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_button_close_deviceActionPerformed

    private void button_update_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_update_deviceActionPerformed
        // TODO add your handling code here:
        try {
        // Lấy thông tin từ các textfield
        String deviceID = text_device_id.getText();
        String deviceName = text_device_name.getText();
        String deviceDescription = text_device_description.getText();
        
        // Kiểm tra xem tên và mô tả thiết bị có được nhập hay không
        if(deviceName.isEmpty() || deviceDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên và mô tả thiết bị không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra xem thiết bị có tồn tại trong danh sách hay không
        boolean deviceExists = false;
        for (device d : deviceBLL.getAllDevices()) {
            if (d.getMaTB().equals(deviceID)) {
                deviceExists = true;
                break;
            }
        }
        if (!deviceExists) {
            JOptionPane.showMessageDialog(this, "Thiết bị không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo đối tượng thiết bị mới với thông tin được cập nhật
        device updatedDevice = new device(deviceID, deviceName, deviceDescription);

        // Gọi phương thức cập nhật thiết bị từ BLL
        deviceBLL.updateDevice(deviceID, updatedDevice);

        // Cập nhật lại bảng hiển thị thiết bị
        loadDevicesToTable();
         // Sau khi thêm thành công, làm trống các textfield
            clearFields();
        
        // Hiển thị thông báo thành công
        JOptionPane.showMessageDialog(this, "Thông tin thiết bị đã được cập nhật thành công.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một ID thiết bị hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin thiết bị: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button_update_deviceActionPerformed

    private void button_refresh_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_refresh_deviceActionPerformed
        // TODO add your handling code here:
        clearFields();
        loadDevicesToTable();
    }//GEN-LAST:event_button_refresh_deviceActionPerformed

    private void button_import_excel_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_import_excel_deviceActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);

                ArrayList<device> newDevices = new ArrayList<>();

                for (org.apache.poi.ss.usermodel.Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue; // Bỏ qua dòng tiêu đề
                    }
                    int deviceID; // Số nguyên để lưu giá trị của mã thiết bị
                    try {
                        deviceID = (int) row.getCell(0).getNumericCellValue(); // Lấy giá trị số từ ô
                    } catch (IllegalStateException e) {
                        // Nếu không thể lấy giá trị số, thử lấy giá trị chuỗi
                        try {
                            deviceID = Integer.parseInt(row.getCell(0).getStringCellValue());
                        } catch (NumberFormatException ex) {
                            // Xử lý lỗi nếu không thể chuyển đổi thành số hoặc chuỗi
                            System.err.println("Lỗi: không thể lấy giá trị từ ô " + row.getRowNum() + ", cột 0");
                            continue; // Bỏ qua dòng này và tiếp tục với dòng tiếp theo
                        }
                    }

                    String deviceName = row.getCell(1).getStringCellValue();
                    String deviceDescription = row.getCell(2).getStringCellValue();

                    String deviceIDString = String.valueOf(deviceID);
                    device newDevice = new device(deviceIDString, deviceName, deviceDescription);
                    newDevices.add(newDevice);
                }


                // Thêm danh sách thiết bị mới vào cơ sở dữ liệu
                deviceBLL.addDevices(newDevices);

                // Cập nhật bảng hiển thị
                loadDevicesToTable();

                JOptionPane.showMessageDialog(this, "Import thành công từ file Excel.");

                workbook.close();
                fileInputStream.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi import dữ liệu từ Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_button_import_excel_deviceActionPerformed

    private void button_delete_all_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_delete_all_deviceActionPerformed
        // TODO add your handling code here:
        String selectedPrefix = (String) cbCategory_device.getSelectedItem();
            if (selectedPrefix != null && !selectedPrefix.isEmpty()) {
                try {
                    deviceBLL.deleteDevicesByPrefix(selectedPrefix);
//                    updateComboBox();
                    loadDevicesToTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa thiết bị: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiền tố để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
         // Lấy số nguyên từ txtCategory_device
//        String prefixText = txtCategory_device.getText().trim();
//        if (!prefixText.isEmpty()) {
//            try {
//               String prefix = prefixText;
//                // Gọi phương thức xóa từ DAL
//                deviceBLL.deleteDevicesWithPrefix(prefix);
//
//                // Cập nhật lại bảng sau khi xóa
//                loadDevicesToTable();
//            } catch (NumberFormatException e) {
//                JOptionPane.showMessageDialog(this, "Vui lòng chỉ nhập số nguyên dương");
//                txtCategory_device.setText(""); // Xóa nội dung nhập trong trường hợp nhập sai định dạng
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa thiết bị");
//                ex.printStackTrace(); // Xử lý lỗi khi xóa thiết bị
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên dương vào trường");
//        }

        
      
    }//GEN-LAST:event_button_delete_all_deviceActionPerformed

    private void text_find_deviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_find_deviceActionPerformed
        // TODO add your handling code here:
        String keyword = text_find_device.getText().trim();
        DefaultTableModel model = (DefaultTableModel) table_device.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table_device.setRowSorter(sorter);

        if (keyword.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword)); // Tìm kiếm không phân biệt chữ hoa thường
        }
    }//GEN-LAST:event_text_find_deviceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_add_device;
    private javax.swing.JButton button_close_device;
    private javax.swing.JButton button_delete_all_device;
    private javax.swing.JButton button_delete_device;
    private javax.swing.JButton button_import_excel_device;
    private javax.swing.JButton button_refresh_device;
    private javax.swing.JButton button_update_device;
    private javax.swing.JComboBox<String> cbCategory_device;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_device;
    private javax.swing.JTextField text_device_description;
    private javax.swing.JTextField text_device_id;
    private javax.swing.JTextField text_device_name;
    private javax.swing.JTextField text_find_device;
    // End of variables declaration//GEN-END:variables
}
