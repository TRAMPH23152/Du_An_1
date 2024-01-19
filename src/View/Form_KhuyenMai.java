
package View;
import DomainModels.KhuyenMai;
import Service.KhuyenMaiService;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Form_KhuyenMai extends javax.swing.JPanel {
//file ghép code:
    KhuyenMaiService kms = new KhuyenMaiService();
    ArrayList<KhuyenMai> listKM = new ArrayList<>();
    public Form_KhuyenMai() {
        initComponents();
        listKM = kms.getAll();
        loadTable();
    }
    
     public void loadTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhuyenMai.getModel();
        model.setRowCount(0);
        for (KhuyenMai km : listKM) {
            Object[] data = new Object[]{
                km.getMa(),
                km.getTen(),
                km.getPhanTramGiam(),
                km.getSoLuong(),
                km.getNgayBatDau(),
                km.getNgayKetThuc(),
                km.getTrangThai() == 1 ? "Đang hoạt động" : "Ngừng hoạt động",
                km.getMoTa()
            };
            model.addRow(data);
        }
    }
     
     public void clearFrom() {
        txtMaKM.setText("");
        txtTenKM.setText("");
        txtMucGiam.setText("");
        txtSoLuongKM.setText("");
        txtNgayBatDauKM.setText("");
        txtNgayKetThucKM.setText("");
        rdoNgungHoatDong.setSelected(true);
        txtMoTaKM.setText("");
        txtTimKiemKM.setText("");
    }
     
     public KhuyenMai getFromData() {
        KhuyenMai km = new KhuyenMai();
        km.setMa(txtMaKM.getText().trim());
        km.setTen(txtTenKM.getText().trim());
        km.setPhanTramGiam(Float.parseFloat(txtMucGiam.getText().trim()));
        km.setSoLuong(Integer.parseInt(txtSoLuongKM.getText().trim()));
        String ngayBatDau = txtNgayBatDauKM.getText().trim();
        Date nbd = parseDate(ngayBatDau);
        if (ngayBatDau != null) {
            km.setNgayBatDau(nbd);
        } else {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không hợp lệ !");
        }

        String ngayKetThuc = txtNgayKetThucKM.getText().trim();
        Date nkt = parseDate(ngayKetThuc);
        if (ngayKetThuc != null) {
            km.setNgayKetThuc(nkt);
        } else {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không hợp lệ !");
        }

        if (rdoDangHoatDong.isSelected()) {
            km.setTrangThai(1);
        } else {
            km.setTrangThai(0);
        }

        km.setMoTa(txtMoTaKM.getText().trim());
        return km;
    }
     
     private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
     
     //check trống:
    public boolean checkTrong() {
        if (txtMaKM.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống mã khuyến mại!");
            txtMaKM.requestFocus();
            return false;
        } else if (txtTenKM.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống tên khuyến mại!");
            txtTenKM.requestFocus();
            return false;
        } else if (txtMucGiam.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống mức giảm khuyến mại!");
            txtMucGiam.requestFocus();
            return false;
        } else if (txtNgayBatDauKM.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống ngày bắt đầu!");
            txtNgayBatDauKM.requestFocus();
            return false;
        } else if (txtNgayKetThucKM.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống ngày kết thúc!");
            txtNgayKetThucKM.requestFocus();
            return false;
        } else if (txtSoLuongKM.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống số lượng!");
            txtSoLuongKM.requestFocus();
            return false;
        } else if (txtMoTaKM.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống mô tả khuyến mại!");
            txtMoTaKM.requestFocus();
            return false;
        }

        return true;
    }

    // Check ngày bắt đầu và ngày kết thúc:
    public boolean validateDate() {
        // Kiểm tra ngày bắt đầu
        String ngayBatDauStr = txtNgayBatDauKM.getText().trim();
        if (!isValidDate(ngayBatDauStr)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không hợp lệ (định dạng chuẩn: năm-tháng-ngày)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ngày kết thúc
        String ngayKetThucStr = txtNgayKetThucKM.getText().trim();
        if (!isValidDate(ngayKetThucStr)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc không hợp lệ (định dạng chuẩn: năm-tháng-ngày)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra nếu ngày kết thúc sau ngày bắt đầu
        Date ngayBatDau = parseDate(ngayBatDauStr);
        Date ngayKetThuc = parseDate(ngayKetThucStr);

        if (ngayBatDau != null && ngayKetThuc != null && ngayKetThuc.before(ngayBatDau)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải sau ngày bắt đầu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Đảm bảo không chấp nhận ngày không hợp lệ (ví dụ: 31/02/2022)
        try {
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // check mức giảm:
    // số âm và trong khoảng cho phép:
    public boolean validateMucGiam() {
        float mucGiam = Float.parseFloat(txtMucGiam.getText().trim());
        if (mucGiam < 0) {
            JOptionPane.showMessageDialog(this, "Mức giảm không được là số âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (mucGiam > 100) {
            JOptionPane.showMessageDialog(this, "Mức giảm chỉ từ 0 % --> 100 %", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // check mức giảm là số not chữ:
    public boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateDinhDangMucGiam() {
        String mucGiam = txtMucGiam.getText().trim();
        if (!isNumeric(mucGiam)) {
            JOptionPane.showMessageDialog(this, "Mức giảm phải là số !", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenKM = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMucGiam = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNgayBatDauKM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNgayKetThucKM = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdoDangHoatDong = new javax.swing.JRadioButton();
        rdoNgungHoatDong = new javax.swing.JRadioButton();
        btnLuu = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtMaKM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSoLuongKM = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTaKM = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhuyenMai = new javax.swing.JTable();
        txtTimKiemKM = new javax.swing.JTextField();
        btnTimKiemKM = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hình thức khuyến mại", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setText("Tên khuyến mại :");

        jLabel2.setText("Mức giảm giá (%) :");

        jLabel3.setText("Ngày bắt đầu : ");

        jLabel4.setText("Ngày kết thúc : ");

        jLabel5.setText("Trạng thái : ");

        rdoDangHoatDong.setText("Đang hoạt động");

        rdoNgungHoatDong.setSelected(true);
        rdoNgungHoatDong.setText("Ngừng hoạt động");

        btnLuu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel6.setText("Mã khuyến mại :");

        jLabel7.setText("Số lượng : ");

        jLabel8.setText("Mô tả : ");

        txtMoTaKM.setColumns(20);
        txtMoTaKM.setRows(5);
        jScrollPane2.setViewportView(txtMoTaKM);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgayBatDauKM)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaKM)
                        .addGap(6, 6, 6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayKetThucKM)
                            .addComponent(txtSoLuongKM))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdoDangHoatDong, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoNgungHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMucGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNgayBatDauKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNgayKetThucKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSoLuongKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNgungHoatDong)
                    .addComponent(rdoDangHoatDong))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khuyến mại", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KM", "Tên KM", "Mức giảm (%)", "Số lượng", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhuyenMai);

        txtTimKiemKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKMKeyReleased(evt);
            }
        });

        btnTimKiemKM.setText("Tìm kiếm");
        btnTimKiemKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiemKM, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemKM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thêm voucher ?") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            if (checkTrong() == true && validateDate() == true && validateMucGiam() && validateDinhDangMucGiam()) {
                kms.add(getFromData());
                listKM = kms.getAll();
                loadTable();
                JOptionPane.showMessageDialog(this, "Thêm voucher thành công!");
                clearFrom();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        int row = tblKhuyenMai.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Mời chọn dòng cần cập nhật!");
            return;
        }
        String ma = tblKhuyenMai.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thêm voucher ?") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            if (checkTrong() == true && validateDate() == true && validateMucGiam() && validateDinhDangMucGiam()) {
                kms.update(ma, getFromData());
                listKM = kms.getAll();
                loadTable();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                clearFrom();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật voucher thất bại!");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearFrom();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuyenMaiMouseClicked
        try {
            int row = tblKhuyenMai.getSelectedRow();
            if (row < 0) {
                return;
            }

            txtMaKM.setText(tblKhuyenMai.getValueAt(row, 0).toString());
            txtTenKM.setText(tblKhuyenMai.getValueAt(row, 1).toString());
            txtMucGiam.setText(tblKhuyenMai.getValueAt(row, 2).toString());
            txtSoLuongKM.setText(tblKhuyenMai.getValueAt(row, 3).toString());
            txtNgayBatDauKM.setText(tblKhuyenMai.getValueAt(row, 4).toString());
            txtNgayKetThucKM.setText(tblKhuyenMai.getValueAt(row, 5).toString());
            if (tblKhuyenMai.getValueAt(row, 6).toString() == "Đang hoạt động") {
                rdoDangHoatDong.setSelected(true);
            } else {
                rdoNgungHoatDong.setSelected(true);
            }
            txtMoTaKM.setText(tblKhuyenMai.getValueAt(row, 7).toString());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblKhuyenMaiMouseClicked

    private void txtTimKiemKMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKMKeyReleased

    }//GEN-LAST:event_txtTimKiemKMKeyReleased

    private void btnTimKiemKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKMActionPerformed
        float mucGiam = 0;

        if (txtTimKiemKM.getText().isEmpty()) {
            mucGiam = 0;
        } else if (txtTimKiemKM.getText().chars().allMatch(Character::isDigit)) {//kiểm tra xem có phải chuỗi ko nếu chuỗi out
            mucGiam = Float.parseFloat(txtTimKiemKM.getText());
        }

        String ma = txtTimKiemKM.getText().trim();

        String ten = txtTimKiemKM.getText().trim();

        try {
            if (txtTimKiemKM.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "Không để trống ô tìm kiếm!");
                txtTimKiemKM.requestFocus();
                return;
            } else {
                listKM = kms.timKiem(ma, ten, mucGiam);
                if (txtTimKiemKM.getText().trim().equalsIgnoreCase("tất cả")) {
                    listKM = kms.getAll();
                    loadTable();
                } else if (listKM.isEmpty() == true) {
                    JOptionPane.showMessageDialog(this, "Không tồn tại dữ liệu này (Tìm theo mã , tên, mức giảm và tất cả)!");
                    txtTimKiemKM.setText("");
                    listKM = kms.getAll();
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Đã tìm thấy!");
                    loadTable();
                }

            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTimKiemKMActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnTimKiemKM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoDangHoatDong;
    private javax.swing.JRadioButton rdoNgungHoatDong;
    private javax.swing.JTable tblKhuyenMai;
    private javax.swing.JTextField txtMaKM;
    private javax.swing.JTextArea txtMoTaKM;
    private javax.swing.JTextField txtMucGiam;
    private javax.swing.JTextField txtNgayBatDauKM;
    private javax.swing.JTextField txtNgayKetThucKM;
    private javax.swing.JTextField txtSoLuongKM;
    private javax.swing.JTextField txtTenKM;
    private javax.swing.JTextField txtTimKiemKM;
    // End of variables declaration//GEN-END:variables
}
