/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import DomainModels.ChatLieu;
import DomainModels.ChiTietDep;
import DomainModels.DanhMuc;
import DomainModels.De;
import DomainModels.MauSac;
import DomainModels.NhaSanXuat;
import DomainModels.SanPham;
import DomainModels.Size;
import Repository.ChiTietSanPhamRepository;
import Service.ChatLieuService;
import Service.ChiTietSanPhamService;
import Service.DanhMucService;
import Service.DeService;
import Service.MauSacService;
import Service.NsxService;
import Service.SanPhamService;
import Service.SizeService;
import ViewModel.ChiTietSPModel;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.poi.hpsf.Decimal;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class Form_SanPham extends javax.swing.JPanel {

    private ChiTietSanPhamService chiTietSanPhamService;
    private ChiTietSanPhamRepository chiTietSanPhamRepository;
    DanhMucService danhMucService = new DanhMucService();
    SanPhamService sanPhamService = new SanPhamService();
    ChatLieuService chatLieuService = new ChatLieuService();
    MauSacService mauSacService = new MauSacService();
    SizeService sizeService = new SizeService();
    DeService deService = new DeService();
    NsxService nsxService = new NsxService();
    List<ChiTietDep> listSanPham;

    private int currentPage = 0;
    private int pageSize = 5;

    /**
     * Creates new form Form_SanPham
     */
    List<String> listDanhMuc;
    DefaultTableModel model;
    String imageUrl;
    int index = 0;

    public Form_SanPham() {
        initComponents();
        chiTietSanPhamService = new ChiTietSanPhamService();
        listSanPham = chiTietSanPhamService.getAllSanPham();
        fillTableSanPham(listSanPham);
        fillCboSanPham();

    }

    public String conVertTinhTrangToString(int tinhTrang) {
        if (tinhTrang == 0) {
            return "Ngung Ban";
        }
        return "Dang Ban";
    }

    public int conVertTinhTrangToInt(String tinhTrang) {
        int number = 0;
        if (tinhTrang.equalsIgnoreCase("Dang Ban")) {
            return 0;
        }
        return 1;
    }

    public void fillTableSanPham(List<ChiTietDep> chiTietDeps) {

        DefaultTableModel def = (DefaultTableModel) tbDanhSach.getModel();
        def.setRowCount(0);

        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, chiTietDeps.size());

        for (int i = start; i < end; i++) {
            ChiTietDep sp = chiTietDeps.get(i);
            Object[] rowData = {
                sp.getId(),
                sp.getIdSanPham().getMa(),
                sp.getIdSanPham().getTen(),
//                sp.getIdDanhMuc() != null ? sp.getIdDanhMuc().getTen() : "",
                sp.getIdDanhMuc().getTen(),
                sp.getGiaBan(),
                sp.getMoTa(),
                conVertTinhTrangToString(sp.getTrangThai()),
                sp.getHinhAnh()
            };
            def.addRow(rowData);
        }

        ButtonState();
    }

    private void ButtonState() {
        int pageCount = (int) Math.ceil((double) listSanPham.size() / pageSize);

        btNhoMax.setEnabled(currentPage > 0);
        btLonMax.setEnabled(currentPage < pageCount - 1);
        lbTrang.setText("Page: " + (currentPage + 1));
    }

    public void fillCboSanPham() {
        listDanhMuc = chiTietSanPhamService.getDanhMuc();
        if (listDanhMuc.isEmpty()) {
            System.out.println("List Danh Muc Rong");
        } else {
            cboDanhMuc.removeAllItems();
            for (String string : listDanhMuc) {
                cboDanhMuc.addItem(string);
            }
            listDanhMuc.add("All");
            cboLocDanhMuc.removeAllItems();
            for (String string : listDanhMuc) {
                cboLocDanhMuc.addItem(string);
            }
        }
    }

    public boolean checkRongTxt(JTextField txt) {
        if (txt.getText().trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    public boolean checkRongTxtArea(JTextArea txtA) {
        if (txtA.getText().trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    public boolean checkPhaiLaSo(JTextField txt) {
        try {
            Integer.parseInt(txt.getText().trim());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkPhaiDuong(JTextField txt) {
        try {
            int value = Integer.parseInt(txt.getText().trim());

            return value >= 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getImageUrl() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        fileChooser.setDialogTitle("Chon Anh");
        File img = fileChooser.getSelectedFile();
        System.out.println(img.getAbsoluteFile());
        return img.getAbsolutePath();
    }

    public void showImage(String imageUrl) {
        if (imageUrl != null) {
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            Image image = imageIcon.getImage();
            Image newImg = image.getScaledInstance(anhChiTiet.getWidth(), anhChiTiet.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imgAfter = new ImageIcon(newImg);
            System.out.println(imageUrl);
            anhChiTiet.setIcon(imgAfter);
        } else {
            System.out.println("Khong Co Anh");
            anhChiTiet.setIcon(null);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        anhChiTiet = new javax.swing.JLabel();
        btnUpLoad = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        rdoConHang = new javax.swing.JRadioButton();
        rdoHetHang = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnNhapExcel = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnChiTiet = new javax.swing.JButton();
        cboDanhMuc = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cboLocDanhMuc = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();
        txtLocGia = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDanhSach = new javax.swing.JTable();
        btLonMax = new javax.swing.JButton();
        btNhoMax = new javax.swing.JButton();
        lbTrang = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)), "Thông Tin Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        btnUpLoad.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/UpLoad_Image.png"))); // NOI18N
        btnUpLoad.setText("UpLoad");
        btnUpLoad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpLoadMouseClicked(evt);
            }
        });

        jLabel2.setText("Tên Sản Phẩm");

        jLabel5.setText("Danh Mục");

        jLabel12.setText("Mô Tả");

        jLabel13.setText("Trạng Thái");

        buttonGroup1.add(rdoConHang);
        rdoConHang.setText("Đang Bán");

        buttonGroup1.add(rdoHetHang);
        rdoHetHang.setText("Ngừng Bán");

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

        btnNhapExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNhapExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/iconExcel.png"))); // NOI18N
        btnNhapExcel.setText("Nhập Excel");
        btnNhapExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExcelActionPerformed(evt);
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

        jLabel3.setText("Giá");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-eye-16.png"))); // NOI18N
        btnChiTiet.setText("Xem Chi Tiết");
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });

        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        jLabel4.setText("Mã Sản Phẩm");

        jLabel6.setText("Id");

        lblId.setText("______________________");

        txtMaSanPham.setText("______________________");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(anhChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnUpLoad)))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnCapNhat)
                        .addGap(38, 38, 38)
                        .addComponent(btnNhapExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(btnReset)
                        .addGap(165, 165, 165))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblId)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                                .addComponent(cboDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtMaSanPham))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel3))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(197, 197, 197)
                                .addComponent(btnChiTiet))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(rdoConHang)
                                .addGap(18, 18, 18)
                                .addComponent(rdoHetHang)))))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(anhChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpLoad)
                    .addComponent(btnThem)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhapExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(lblId)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaSanPham))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(41, 41, 41))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(rdoConHang)
                                    .addComponent(rdoHetHang))
                                .addGap(42, 42, 42)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)), "Lọc Sản Phẩm"));

        jLabel14.setText("Danh Mục");

        jLabel15.setText("Giá Tiền");

        cboLocDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocDanhMucActionPerformed(evt);
            }
        });

        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Untitled-1.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(cboLocDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtLocGia, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLocDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLocGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Mã Sản Phẩm", "Tên Sản Phẩm", "Danh Mục", "Giá", "Mô Tả", "Trạng Thái", "Image"
            }
        ));
        tbDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDanhSach);

        btLonMax.setText(">>");
        btLonMax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btLonMaxMouseClicked(evt);
            }
        });

        btNhoMax.setText("<<");
        btNhoMax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btNhoMaxMouseClicked(evt);
            }
        });

        lbTrang.setText("jLabel4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(387, 387, 387)
                .addComponent(btNhoMax)
                .addGap(18, 18, 18)
                .addComponent(lbTrang)
                .addGap(18, 18, 18)
                .addComponent(btLonMax)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNhoMax)
                    .addComponent(btLonMax)
                    .addComponent(lbTrang))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpLoadMouseClicked
        imageUrl = getImageUrl();
        showImage(imageUrl);
    }//GEN-LAST:event_btnUpLoadMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int selected = -1;
        if (checkRongTxtArea(txtMoTa) == false || checkRongTxt(txtGia) == false
                || checkRongTxt(txtTenSanPham) == false) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin.");
            return;
        }

        if (!checkPhaiLaSo(txtGia)) {
            JOptionPane.showMessageDialog(this, "Gia phai la so.");
            return;
        }

        BigDecimal gia = new BigDecimal(txtGia.getText());
        if (gia.compareTo(BigDecimal.ZERO) < 0) {
            JOptionPane.showMessageDialog(this, "Gia khong duoc am.");
            return;
        }

        SanPham sanPham = new SanPham();
        sanPham.setMa(String.valueOf(txtMaSanPham.getText()));
        sanPham.setTen(String.valueOf(txtTenSanPham.getText()));
        if (rdoConHang.isSelected()) {
            sanPham.setTrangThai(1);
        } else {
            sanPham.setTrangThai(0);
        }

        Integer sanPhamMa = sanPhamService.insert(sanPham);
        if (sanPhamMa <= 0) {
            JOptionPane.showMessageDialog(this, "Them that bai");
            return;
        }

        ChiTietSPModel chiTietSPModel = new ChiTietSPModel();
        chiTietSPModel.setId(sanPhamMa);
        chiTietSPModel.setTenSanPham(sanPhamService.getSanPhamMa(sanPham.getMa()));
        chiTietSPModel.setTenDanhMuc(FillTenDanhMuc((String) cboDanhMuc.getSelectedItem()));
        chiTietSPModel.setGiaBan(new BigDecimal(txtGia.getText()));
        chiTietSPModel.setMoTa(String.valueOf(txtMoTa.getText()));
        if (rdoConHang.isSelected()) {
            chiTietSPModel.setTrangThai(1);
        } else {
            chiTietSPModel.setTrangThai(0);
        }
        chiTietSPModel.setImageUrl(imageUrl);
        JOptionPane.showMessageDialog(this, chiTietSanPhamService.insertChiTietSanPham(chiTietSPModel));
        listSanPham = chiTietSanPhamService.getAllSanPham();
        fillTableSanPham(listSanPham);
        clearFrom();
        showImage(imageUrl);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        int selected = -1;
        if (checkRongTxtArea(txtMoTa) == false || checkRongTxt(txtGia) == false
                || checkRongTxt(txtTenSanPham) == false) {
            JOptionPane.showMessageDialog(this, "Vui long chon ban ghi de cap nhat.");
        } else if (checkPhaiLaSo(txtGia) == false) {
            JOptionPane.showMessageDialog(this, "Mot so thong tin phai la so.");
        } else if (checkPhaiDuong(txtGia) == false) {
            JOptionPane.showMessageDialog(this, "Gia ban khong duoc am.");
        }

        SanPham sanPham = new SanPham();
        sanPham.setMa(String.valueOf(txtMaSanPham.getText()));
        sanPham.setTen(String.valueOf(txtTenSanPham.getText()));
        if (rdoConHang.isSelected()) {
            sanPham.setTrangThai(1);
        } else {
            sanPham.setTrangThai(0);
        }
        if (!sanPhamService.update(sanPham)) {
            JOptionPane.showMessageDialog(this, "Sua that bai");
            return;
        }

        ChiTietSPModel chiTietSPModel = new ChiTietSPModel();

        chiTietSPModel.setTenSanPham(sanPhamService.getSanPhamMa(sanPham.getMa()));
        chiTietSPModel.setTenDanhMuc(FillTenDanhMuc((String) cboDanhMuc.getSelectedItem()));
        chiTietSPModel.setGiaBan(new BigDecimal(txtGia.getText()));
        chiTietSPModel.setMoTa(String.valueOf(txtMoTa.getText()));
        if (rdoConHang.isSelected()) {
            chiTietSPModel.setTrangThai(1);
        } else {
            chiTietSPModel.setTrangThai(0);
        }
        chiTietSPModel.setImageUrl(imageUrl);
        chiTietSPModel.setId(Integer.parseInt(lblId.getText()));
        JOptionPane.showMessageDialog(this, chiTietSanPhamService.updateChiTietSanPham(chiTietSPModel));
        listSanPham = chiTietSanPhamService.getAllSanPham();
        fillTableSanPham(listSanPham);
        clearFrom();
        showImage(imageUrl);
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnNhapExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExcelActionPerformed
        File excelFile;
        FileInputStream excelInputStream = null;
        BufferedInputStream excelBufferedInputStream = null;
        XSSFWorkbook excelFWorkbook = null;
        String defaultString = "C:\\New folder\\FileExcel";
        JFileChooser fileChooser = new JFileChooser(defaultString);
        int excelChooser = fileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = fileChooser.getSelectedFile();
                excelInputStream = new FileInputStream(excelFile);
                excelBufferedInputStream = new BufferedInputStream(excelInputStream);
                excelFWorkbook = new XSSFWorkbook(excelBufferedInputStream);
                XSSFSheet excelSheet = excelFWorkbook.getSheetAt(0);
                for (int row = 1; row < excelSheet.getLastRowNum() + 1; row++) {

                }
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnNhapExcelActionPerformed

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        try {
            Form_ChiTiet fctsp = new Form_ChiTiet();
            fctsp.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnChiTietActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        String keyword = (String) cboLocDanhMuc.getSelectedItem();
        Float gia = txtLocGia.getText().trim().equals("") ? null : Float.parseFloat(txtLocGia.getText());
        if (keyword.equalsIgnoreCase("all")) {
            listSanPham = chiTietSanPhamService.getAll();
        } else {
            listSanPham = chiTietSanPhamService.searchChiTietDep(keyword, gia);
        }

        System.out.println(keyword);
        if (listSanPham.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm");
        }
        fillTableSanPham(listSanPham);
    }//GEN-LAST:event_btnLocActionPerformed

    private void tbDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDanhSachMouseClicked
        int index = tbDanhSach.getSelectedRow();
        if (index < 0) {
            return;
        }
        lblId.setText(tbDanhSach.getValueAt(index, 0).toString());
        txtMaSanPham.setText(tbDanhSach.getValueAt(index, 1).toString());
        txtTenSanPham.setText(tbDanhSach.getValueAt(index, 2).toString());
        cboDanhMuc.setSelectedItem(tbDanhSach.getValueAt(index, 3).toString());
        txtGia.setText(tbDanhSach.getValueAt(index, 4).toString());
        txtMoTa.setText(tbDanhSach.getValueAt(index, 5).toString());
        if (tbDanhSach.getValueAt(index, 6).toString() == "Dang Ban") {
            rdoConHang.setSelected(true);
        } else {
            rdoHetHang.setSelected(true);
        }
        imageUrl = tbDanhSach.getValueAt(index, 7).toString();
        showImage(imageUrl);
    }//GEN-LAST:event_tbDanhSachMouseClicked

    private void btLonMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btLonMaxMouseClicked
        if (currentPage < (int) Math.ceil((double) listSanPham.size() / pageSize) - 1) {
            currentPage++;
            fillTableSanPham(listSanPham);
        }
    }//GEN-LAST:event_btLonMaxMouseClicked

    private void btNhoMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btNhoMaxMouseClicked
        if (currentPage > 0) {
            currentPage--;
            fillTableSanPham(listSanPham);
        }
    }//GEN-LAST:event_btNhoMaxMouseClicked

    private void cboLocDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLocDanhMucActionPerformed

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDanhMucActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearFrom();
    }//GEN-LAST:event_btnResetActionPerformed

    public void clearFrom() {
        lblId.setText("");
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtLocGia.setText("");
        txtGia.setText("");
        txtMoTa.setText("");
        cboDanhMuc.setSelectedIndex(0);
        rdoConHang.setSelected(true);
        cboLocDanhMuc.setSelectedIndex(0);
        anhChiTiet.setIcon(null);
    }

    private SanPham FillTenSanPham(String ten) {
        return sanPhamService.getIDByName(ten);
    }

    private DanhMuc FillTenDanhMuc(String ten) {
        return danhMucService.getIDByName(ten);
    }

    private SanPham FillMaSanPham(String ma) {
        return sanPhamService.getSanPhamMa(ma);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anhChiTiet;
    private javax.swing.JButton btLonMax;
    private javax.swing.JButton btNhoMax;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnNhapExcel;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnUpLoad;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboLocDanhMuc;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTrang;
    private javax.swing.JLabel lblId;
    private javax.swing.JRadioButton rdoConHang;
    private javax.swing.JRadioButton rdoHetHang;
    private javax.swing.JTable tbDanhSach;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtLocGia;
    private javax.swing.JLabel txtMaSanPham;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables

    private Decimal Decimal(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
