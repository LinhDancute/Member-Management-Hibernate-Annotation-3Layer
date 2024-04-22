/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class main_menu extends javax.swing.JFrame {

    /**
     * Creates new form main_menu
     */
    public main_menu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesktopShow = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        item_member_management = new javax.swing.JMenuItem();
        item_service_management = new javax.swing.JMenuItem();
        item_income_member_management = new javax.swing.JMenuItem();
        item_usage_information_management = new javax.swing.JMenuItem();
        item_handle_violations = new javax.swing.JMenuItem();
        item_statistical = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout DesktopShowLayout = new javax.swing.GroupLayout(DesktopShow);
        DesktopShow.setLayout(DesktopShowLayout);
        DesktopShowLayout.setHorizontalGroup(
            DesktopShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 930, Short.MAX_VALUE)
        );
        DesktopShowLayout.setVerticalGroup(
            DesktopShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 692, Short.MAX_VALUE)
        );

        jMenu1.setText("Quản lý");

        item_member_management.setText("Quản lý thành viên");
        item_member_management.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_member_managementActionPerformed(evt);
            }
        });
        jMenu1.add(item_member_management);

        item_service_management.setText("Quản lý thiết bị");
        item_service_management.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_service_managementActionPerformed(evt);
            }
        });
        jMenu1.add(item_service_management);

        item_income_member_management.setText("Quản lý thành viên vào");
        item_income_member_management.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_income_member_managementActionPerformed(evt);
            }
        });
        jMenu1.add(item_income_member_management);

        item_usage_information_management.setText("Quản lý thông tin sử dụng");
        item_usage_information_management.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_usage_information_managementActionPerformed(evt);
            }
        });
        jMenu1.add(item_usage_information_management);

        item_handle_violations.setText("Xử lý vi phạm");
        item_handle_violations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_handle_violationsActionPerformed(evt);
            }
        });
        jMenu1.add(item_handle_violations);

        item_statistical.setText("Thống kê");
        item_statistical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_statisticalActionPerformed(evt);
            }
        });
        jMenu1.add(item_statistical);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DesktopShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(DesktopShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void item_service_managementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_service_managementActionPerformed
        DesktopShow.removeAll();
        DesktopShow.repaint();
        
        device_management service = new device_management();
        DesktopShow.add(service);
        service.setVisible(true);
    }//GEN-LAST:event_item_service_managementActionPerformed

    private void item_member_managementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_member_managementActionPerformed
        try {
            DesktopShow.removeAll();
            DesktopShow.repaint();
            
            member_management member = new member_management();
            DesktopShow.add(member);
            member.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(main_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_item_member_managementActionPerformed

    private void item_usage_information_managementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_usage_information_managementActionPerformed
        DesktopShow.removeAll();
        DesktopShow.repaint();
        
        usage_information_management usage_information = new usage_information_management();
        DesktopShow.add(usage_information);
        usage_information.setVisible(true);
    }//GEN-LAST:event_item_usage_information_managementActionPerformed

    private void item_handle_violationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_handle_violationsActionPerformed
        DesktopShow.removeAll();
        DesktopShow.repaint();
        
        handle_violations handle_violations = new handle_violations();
        DesktopShow.add(handle_violations);
        handle_violations.setVisible(true);
    }//GEN-LAST:event_item_handle_violationsActionPerformed

    private void item_statisticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_statisticalActionPerformed
        try {
            DesktopShow.removeAll();
            DesktopShow.repaint();
            
            statistical statistical = new statistical();
            DesktopShow.add(statistical);
            statistical.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(main_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_item_statisticalActionPerformed

    private void item_income_member_managementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_income_member_managementActionPerformed
        DesktopShow.removeAll();
        DesktopShow.repaint();
        
        income_member_management income_member = new income_member_management();
        DesktopShow.add(income_member);
        income_member.setVisible(true);
    }//GEN-LAST:event_item_income_member_managementActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main_menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane DesktopShow;
    private javax.swing.JMenuItem item_handle_violations;
    private javax.swing.JMenuItem item_income_member_management;
    private javax.swing.JMenuItem item_member_management;
    private javax.swing.JMenuItem item_service_management;
    private javax.swing.JMenuItem item_statistical;
    private javax.swing.JMenuItem item_usage_information_management;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
