package View;

import DomainModels.HD;
import DomainModels.HoaDonCT;
import Repository.HDReponsitory;
import Service.HDService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.util.LocaleID;

public class Form_QLHoaDon extends javax.swing.JPanel {

    HDService hds = new HDService();
    ArrayList<HD> listHD = new ArrayList<>();
    HDReponsitory hdrepo = new HDReponsitory();

    ArrayList<HoaDonCT> listHD_CT = new ArrayList<>();

    public Form_QLHoaDon() {
        initComponents();
        listHD = hds.getAll();
        loadTableHD();
    }

    public void loadTableHD() {
        DefaultTableModel model = (DefaultTableModel) tbl_HoaDon.getModel();
        ArrayList<HD> listTBHD = hdrepo.getAll();
        model.setRowCount(0);
        for (HD hd : listTBHD) {
            Object[] data = new Object[]{
                hd.getId(),
                hd.getMaHoaDon(),
                hd.getMaNV(),
                hd.getSdtKH(),
                hd.getNgayTao(),
                hd.getTongTien(),
                hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán",
                hd.getGhiChu()
            };
            model.addRow(data);
        }
    }

    public void loadTableHDCT(int maHD) {
        DefaultTableModel model = (DefaultTableModel) tbl_HoaDonCT.getModel();
        model.setRowCount(0);
        for (HoaDonCT hdct : listHD_CT) {
            Object[] data = new Object[]{
                hdct.getMaHD(),
                hdct.getMaNV(),
                hdct.getSdt(),
                hdct.getMaVoucher(),
                hdct.getNgayTao(),
                hdct.getTenSP(),
                hdct.getSoLuong(),
                hdct.getDonGia(),
                hdct.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán",};
            model.addRow(data);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_QLhoadon = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_HoaDonCT = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_HoaDon = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_timKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        txt_NgayBD = new com.toedter.calendar.JDateChooser();
        txtNgayKT = new com.toedter.calendar.JDateChooser();

        jPanel_QLhoadon.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_QLhoadon.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("                               Quản Lý Hóa Đơn ");

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbl_HoaDonCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Mã NV", "Mã KH", "Sdt KH", "Ngày tạo", "Tên SP", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_HoaDonCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HoaDonCTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_HoaDonCT);

        tbl_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã hóa đơn", "Mã nhân viên", "Sdt KH", "Ngày tạo", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_HoaDon);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Bảng hóa đơn:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Bảng hóa đơn chi tiết:");

        txt_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_timKiemActionPerformed(evt);
            }
        });

        jLabel2.setText("Tìm kiếm : ");

        jLabel5.setText("Ngày bắt đầu:");

        jLabel6.setText("Ngày kết thúc :");

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_NgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiem)
                        .addComponent(btn_reset))
                    .addComponent(txt_NgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_QLhoadonLayout = new javax.swing.GroupLayout(jPanel_QLhoadon);
        jPanel_QLhoadon.setLayout(jPanel_QLhoadonLayout);
        jPanel_QLhoadonLayout.setHorizontalGroup(
            jPanel_QLhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE)
            .addGroup(jPanel_QLhoadonLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_QLhoadonLayout.setVerticalGroup(
            jPanel_QLhoadonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_QLhoadonLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_QLhoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_QLhoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_HoaDonCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HoaDonCTMouseClicked

    }//GEN-LAST:event_tbl_HoaDonCTMouseClicked

    private void tbl_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HoaDonMouseClicked
        int row = tbl_HoaDon.getSelectedRow();
        if (row < 0) {
            return;
        }
        int idHD = Integer.parseInt(tbl_HoaDon.getValueAt(row, 0).toString());

        listHD_CT = hds.getAll_HDCT(idHD);
        loadTableHDCT(idHD);

    }//GEN-LAST:event_tbl_HoaDonMouseClicked

    private void txt_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_timKiemActionPerformed
        String input = txt_timKiem.getText();
        if (input == null) {
            loadTableHD();
        }
        ArrayList<HD> listHD = new ArrayList<>();
        for (var x : hdrepo.search(input)) {
            if (x.getMaHoaDon().toLowerCase().contains(input.toLowerCase())
                    || x.getSdtKH().toLowerCase().contains(input.toLowerCase())
                    || x.getMaKH().toLowerCase().contains(input.toLowerCase())) {
                listHD.add(x);
            }
        }
        DefaultTableModel def = (DefaultTableModel) tbl_HoaDon.getModel();
        def.setRowCount(0);
        for (HD hd : listHD) {
            Object[] data = new Object[]{
                hd.getId(),
                hd.getMaHoaDon(),
                hd.getMaNV(),
                hd.getSdtKH(),
                hd.getNgayTao(),
                hd.getTongTien(),
                hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán",
                hd.getGhiChu()
            };
            def.addRow(data);
        }

    }//GEN-LAST:event_txt_timKiemActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        loadTableHD();
        txt_timKiem.setText("");
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        try {
            if (txt_NgayBD.getDate() == null || txtNgayKT.getDate() == null) {
                loadTableHD();
                JOptionPane.showMessageDialog(this, "Bắt buộc phải chọn đủ 2 trường ngày bắt đầu và ngày kết thúc!");
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStart = sdf.format(txt_NgayBD.getDate());
                String dateEnd = sdf.format(txtNgayKT.getDate());
                ArrayList<HD> listKMDate = hdrepo.searchDate(dateStart, dateEnd);
                DefaultTableModel model = (DefaultTableModel) tbl_HoaDon.getModel();
                model.setRowCount(0);
                for (HD hd : listKMDate) {
                    Object[] data = new Object[]{
                        hd.getId(),
                        hd.getMaHoaDon(),
                        hd.getMaNV(),
                        hd.getSdtKH(),
                        hd.getNgayTao(),
                        hd.getTongTien(),
                        hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán",
                        hd.getGhiChu()
                    };
                    model.addRow(data);
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btn_reset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_QLhoadon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbl_HoaDon;
    private javax.swing.JTable tbl_HoaDonCT;
    private com.toedter.calendar.JDateChooser txtNgayKT;
    private com.toedter.calendar.JDateChooser txt_NgayBD;
    private javax.swing.JTextField txt_timKiem;
    // End of variables declaration//GEN-END:variables
}
