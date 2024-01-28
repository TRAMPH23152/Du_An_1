/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DomainModels.NhaSanXuat;
import Service.NsxService;
import Utilities.DBConnection;
import ViewModel.NsxModel;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Form_NSX extends javax.swing.JFrame {

    /**
     * Creates new form Form_NSX
     */
    NsxService nsxService = new NsxService();
    NsxModel nm;
    long count, soTrang, trang = 1;
    Connection cn;
    Statement st;
    ResultSet rs;

    public Form_NSX() {
        initComponents();
        setLocationRelativeTo(null);
        titleTable();
        countDB();
        if (count % 5 == 0) {
            soTrang = count / 5;
        } else {
            soTrang = count / 5 + 1;
        }
        loadData(1);
        lbSoTrang.setText("1/" + soTrang);
        lbTrang.setText("1");
    }

    public void titleTable() {
        nm = new NsxModel();
        tblDanhSach.setModel(nm);
        tblDanhSach.setShowHorizontalLines(true);
        tblDanhSach.setShowVerticalLines(true);
    }

    public void countDB() {
        try {
            String query = "Select count(*) from NSX";
            cn = DBConnection.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                count = rs.getLong(1);
            }
            rs.close();
            st.close();
            cn.close();
        } catch (Exception ex) {
            Logger.getLogger(Form_NSX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadData(long trang) {
        titleTable();
        nm.getDataVector().removeAllElements();
        try {
            String query = "Select top 5 * from NSX where Ten not in (Select top " + (trang * 5 - 5) + " Ten from NSX)";
            cn = DBConnection.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Vector v = new Vector();
                Integer id = rs.getInt(1);
                String Ma = rs.getString(2);
                String ten = rs.getString(3);
                Integer trangThai = rs.getInt(4);
                v.add(id);
                v.add(Ma);
                v.add(ten);
                v.add(trangThai);
                nm.addRow(v);
            }

            rs.close();
            st.close();
            cn.close();
        } catch (Exception ex) {
            Logger.getLogger(Form_NSX.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTenNsx = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        btLonMax = new javax.swing.JButton();
        btNhoMax = new javax.swing.JButton();
        btNho = new javax.swing.JButton();
        btLon = new javax.swing.JButton();
        lbTrang = new javax.swing.JLabel();
        lbSoTrang = new javax.swing.JLabel();
        lbClose = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtMaNSX = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        rdoCon = new javax.swing.JRadioButton();
        rdoHet = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        jLabel17.setText("Mã NSX");

        jLabel18.setText("Tên NSX");

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Them.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Sua.png"))); // NOI18N
        btnCapNhat.setText("Cập Nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Chất Liệu", "Tên Chất liệu"
            }
        ));
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhSach);

        btLonMax.setText(">>");
        btLonMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLonMaxActionPerformed(evt);
            }
        });

        btNhoMax.setText("<<");
        btNhoMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNhoMaxActionPerformed(evt);
            }
        });

        btNho.setText("<");
        btNho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNhoActionPerformed(evt);
            }
        });

        btLon.setText(">");
        btLon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLonActionPerformed(evt);
            }
        });

        lbTrang.setText("jLabel1");

        lbSoTrang.setText("jLabel2");

        lbClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-back-40.png"))); // NOI18N
        lbClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbCloseMouseClicked(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-clear-16.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel19.setText("Mã NSX");

        lblId.setText("_____");

        txtMaNSX.setText("___________");

        jLabel20.setText("Trạng Thái");

        buttonGroup1.add(rdoCon);
        rdoCon.setText("Còn ");

        buttonGroup1.add(rdoHet);
        rdoHet.setText("Hết");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(lbClose)
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabel17)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblId)
                                        .addGap(102, 102, 102)
                                        .addComponent(jLabel19)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMaNSX))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoCon)
                                                .addGap(18, 18, 18)
                                                .addComponent(rdoHet))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtTenNsx, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(btNhoMax)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btNho, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbTrang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btLon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btLonMax)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbSoTrang))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnCapNhat)
                                .addGap(49, 49, 49)
                                .addComponent(btnReset)))
                        .addGap(0, 69, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbClose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19)
                    .addComponent(lblId)
                    .addComponent(txtMaNSX))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTenNsx, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(rdoCon)
                    .addComponent(rdoHet))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNhoMax)
                    .addComponent(btNho)
                    .addComponent(lbTrang)
                    .addComponent(btLon)
                    .addComponent(btLonMax)
                    .addComponent(lbSoTrang))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btLonMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLonMaxActionPerformed
        trang = soTrang;
        loadData(trang);
        lbTrang.setText("" + soTrang);
        lbSoTrang.setText(soTrang + "/" + soTrang);
    }//GEN-LAST:event_btLonMaxActionPerformed

    private void btNhoMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNhoMaxActionPerformed
        trang = 1;
        loadData(trang);
        lbTrang.setText("1");
        lbSoTrang.setText("1/" + soTrang);
    }//GEN-LAST:event_btNhoMaxActionPerformed

    private void btNhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNhoActionPerformed
        if (trang > 1) {
            trang--;
            loadData(trang);
            lbTrang.setText("" + trang);
            lbSoTrang.setText(trang + "/" + soTrang);
        }
    }//GEN-LAST:event_btNhoActionPerformed

    private void btLonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLonActionPerformed
        if (trang < soTrang) {
            trang++;
            loadData(trang);
            lbTrang.setText("" + trang);
            lbSoTrang.setText(trang + "/" + soTrang);
        }
    }//GEN-LAST:event_btLonActionPerformed

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearFrom();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        int index = tblDanhSach.getSelectedRow();

        lblId.setText(tblDanhSach.getValueAt(index, 0).toString());
        txtMaNSX.setText(tblDanhSach.getValueAt(index, 1).toString());
        txtTenNsx.setText(tblDanhSach.getValueAt(index, 2).toString());
        if (tblDanhSach.getValueAt(index, 3).toString().equals("1")) {
            rdoCon.setSelected(true);
        } else {
            rdoHet.setSelected(true);
        }
        System.out.println(tblDanhSach.getValueAt(index, 3).toString());
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (txtMaNSX.getText().trim().equals("") || txtTenNsx.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Moi ban nhap day du thong tin.");
            return;
        }
        try {
            NhaSanXuat nsx = new NhaSanXuat();
            nsx.setMa(String.valueOf(txtMaNSX.getText()));
            nsx.setTen(String.valueOf(txtTenNsx.getText()));
            if (rdoCon.isSelected()) {
                nsx.setTrangThai(1);
            } else {
                nsx.setTrangThai(0);
            }
            nsx.setId(Integer.parseInt(lblId.getText()));
            nsxService.insert(nsx);
            loadData(trang);
            clearFrom();
            JOptionPane.showMessageDialog(this, "Add thanh cong.");
            Form_ChiTiet form_ChiTiet = new Form_ChiTiet();
            form_ChiTiet.fillCboChiTiet();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Add that bai.");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (txtMaNSX.getText().trim().equals("") || txtTenNsx.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Moi ban chon ban ghi.");
            return;
        }
        NhaSanXuat nsx = new NhaSanXuat();
        nsx.setMa(String.valueOf(txtMaNSX.getText()));
        nsx.setTen(String.valueOf(txtTenNsx.getText()));
        if (rdoCon.isSelected()) {
            nsx.setTrangThai(1);
        } else {
            nsx.setTrangThai(0);
        }
        nsx.setId(Integer.parseInt(lblId.getText()));
        JOptionPane.showMessageDialog(this, "Cap nhat thanh cong.");
        if (!nsxService.update(nsx)) {
            JOptionPane.showMessageDialog(this, "Cap nhat that bai.");
            return;
        }
        loadData(trang);
        clearFrom();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    public void clearFrom() {
        lblId.setText("");
        txtMaNSX.setText("");
        txtTenNsx.setText("");
        rdoCon.setSelected(true);
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Form_NSX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Form_NSX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Form_NSX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Form_NSX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Form_NSX().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLon;
    private javax.swing.JButton btLonMax;
    private javax.swing.JButton btNho;
    private javax.swing.JButton btNhoMax;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbSoTrang;
    private javax.swing.JLabel lbTrang;
    private javax.swing.JLabel lblId;
    private javax.swing.JRadioButton rdoCon;
    private javax.swing.JRadioButton rdoHet;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JLabel txtMaNSX;
    private javax.swing.JTextField txtTenNsx;
    // End of variables declaration//GEN-END:variables
}
