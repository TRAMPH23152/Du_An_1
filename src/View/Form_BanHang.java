/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import DomainModels.ChiTietDep;
import DomainModels.GioHang;
import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.KhuyenMai;
import DomainModels.NhaSanXuat;
import Repository.BanHangRepository;
import Repository.DanhMucRepository;
import Repository.HoaDonChoRepository;
import com.microsoft.sqlserver.jdbc.StringUtils;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author asus
 */
public class Form_BanHang extends javax.swing.JPanel {

    private int soTrang = 1;
    private int tongsoTrang = 0;
    private final BanHangRepository banHangRepository = new BanHangRepository();
    private final HoaDonChoRepository hoaDonChoRepository = new HoaDonChoRepository();
    private final DanhMucRepository danhMucRepository = new DanhMucRepository();
    public static HoaDon hoadon = new HoaDon();
    public int selectedRowInBanHang = -1;
    DefaultComboBoxModel<NhaSanXuat> modelNSX = new DefaultComboBoxModel<>();

    DefaultTableModel def = new DefaultTableModel();

    public Form_BanHang() {
        initComponents();
        fillTableSanPham();
        fillTabelHoaDonCho();

    }

    private void fillTableSanPham() {
        def = (DefaultTableModel) tb_HdSp.getModel();

        ArrayList<ChiTietDep> list = banHangRepository.getAllChiTietDepForPage(soTrang);
        def.setRowCount(0);
        for (ChiTietDep sp : list) {
            Object[] rowData = {
                sp.getIdSanPham().getIdSanPham(),
                sp.getIdSanPham().getTen(),
                sp.getSoLuong(),
                sp.getGiaBan(),
                sp.getIdDanhMuc().getTen(),
                sp.getIdChatLieu().getTen(),
                sp.getIdSize().getKichCo(),
                sp.getIdNSX().getTen(),
                sp.getIdDe().getTen(),
                sp.getIdMauSac().getMauSac()
            };
            def.addRow(rowData);
            lbl_page.setText("" + soTrang);
        }
    }

    public void fillTabelHoaDonCho() {
        def = (DefaultTableModel) tb_hoadon.getModel();
        ArrayList<HoaDon> list = hoaDonChoRepository.getAllHoaDonCho();
        def.setRowCount(0);
        for (HoaDon hd : list) {
            String nguoiDung = hd.getIdNguoiDung() != null ? hd.getIdNguoiDung().getTenNguoiDung() : "Tram";
            //  String khachHang = hd.getIdKhachHang() != null ? hd.getIdKhachHang().getTen() : "Khách Lẻ";
            String trangThai = hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chưa thanh toán";
            def.addRow(new Object[]{
                hd.getMa(),
                nguoiDung,
                hd.getIdKhachHang() == null ? "Khách lẻ" : hd.getIdKhachHang(),
                hd.getIdKhuyenMai() == null ? "" : hd.getIdKhuyenMai(),
                hd.getNgayTao(),
                trangThai
            });
            lbl_page.setText("" + soTrang);
        }

    }

    private void fillTableGioHang() {
        def = (DefaultTableModel) tb_giohang.getModel();

        // Kiểm tra xem có dòng nào được chọn không
        int row = tb_hoadon.getSelectedRow();
        if (row >= 0) {
            String maHD = tb_hoadon.getValueAt(row, 0).toString();
            String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
            ArrayList<GioHang> list = hoaDonChoRepository.getAllGioHang(idHD);
            def.setRowCount(0);
            for (GioHang hd : list) {
                def.addRow(new Object[]{
                    hd.getId(),
                    hd.getSanPham(),
                    hd.getSoLuong(),
                    hd.getChatLieu(),
                    hd.getMauSac(),
                    hd.getNsx(),
                    hd.getSize(),
                    hd.getDonGia(),
                    hd.getTrangThai() == 1 ? "Đã thanh toán" : "Chờ thanh toán"
                });
            }
        }
    }

    private ArrayList<ChiTietDep> getAllSanPhamTimKiem(String input) {
        if (input == null) {
            return banHangRepository.search(input);
        }
        ArrayList<ChiTietDep> l = new ArrayList<>();
        for (var x : banHangRepository.search(input)) {
            if (x.getIdSanPham().getTen().toLowerCase().contains(input.toLowerCase())
                    || x.getIdChatLieu().getTen().toLowerCase().contains(input.toLowerCase())
                    || x.getIdDanhMuc().getTen().toLowerCase().contains(input.toLowerCase())
                    || x.getIdMauSac().getMauSac().toLowerCase().contains(input.toLowerCase())
                    || x.getIdDanhMuc().getTen().toLowerCase().contains(input.toLowerCase())
                    || x.getIdSize().getKichCo().toString().toLowerCase().contains(input.toLowerCase())) {
                l.add(x);
            }
        }
        return l;
    }

    private void showDetailHDC() {
        int row = tb_hoadon.getSelectedRow();

        // Kiểm tra xem có hàng nào được chọn không
        if (row != -1) {
            jlb_maHoaDon.setText(tb_hoadon.getValueAt(row, 0).toString());
            String maHD = tb_hoadon.getValueAt(row, 0).toString();
            String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
            Double tongtien = hoaDonChoRepository.getGiaTienHDCTtoTongTienHDbyIdHD(idHD);
            hoaDonChoRepository.capNhatTongTienHoaDon(idHD, tongtien);
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String formattedTongTien = decimalFormat.format(tongtien);
            txt_tongTien.setText(formattedTongTien);

        } else {
            // Xử lý khi không có hàng nào được chọn, có thể hiển thị thông báo hoặc thực hiện các hành động khác
            System.out.println("Không có hàng nào được chọn!");
        }
    }

    public void loadChonKH(String tenKh, String sdt) {
        txt_tenKH.setText(tenKh);
        txt_sdtKH.setText(sdt);
    }

    public void loadChonKhuyenMai(int rowHD, String maKM, Double phanTramGiam, Double giaGiam) {
        txt_maVoucher.setText(maKM);

        if (rowHD >= 0) {

            String maKM1 = tb_hoadon.getValueAt(rowHD, 3).toString();
            KhuyenMai khuyenMai = hoaDonChoRepository.getHinThucGiamibyMa(maKM1);

            if (khuyenMai != null) {
                int hinhThucGiam = khuyenMai.getHinhThucGiam();
                if (hinhThucGiam != 0) {
                    System.out.println("Giam Theo Phan Tram: " + phanTramGiam);
                    txt_phanTramGiam.setText(Double.valueOf(phanTramGiam).toString());
                } else {
                    System.out.println("Giam Theo Tien: " + hinhThucGiam);
                    txt_phanTramGiam.setText(Double.valueOf(giaGiam).toString());
                }
            } else {
                // Xử lý khi không có đối tượng KhuyenMai được trả về
            }
        } else {
            // Xử lý khi không có hàng nào được chọn trong bảng
        }
    }

    private Boolean CheckTrung() {
        int row = tb_HdSp.getSelectedRow();
        int rowHD = tb_hoadon.getSelectedRow();
        String idCTSP = tb_HdSp.getValueAt(row, 0).toString();
        String maHD = tb_hoadon.getValueAt(rowHD, 0).toString();
        String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
        if (hoaDonChoRepository.checkTrungCTSPinGH(idHD, idCTSP) == 0) {
            return false;
        } else {
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_BanHang = new javax.swing.JPanel();
        jPanel_TableHD = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_hoadon = new javax.swing.JTable();
        jPanel_GioHang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_giohang = new javax.swing.JTable();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        jPanel_SanPham = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_HdSp = new javax.swing.JTable();
        txt_timkiemsp = new javax.swing.JTextField();
        btn_prev = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        btn_timkiemsp = new javax.swing.JButton();
        lbl_page = new javax.swing.JLabel();
        btn_them = new javax.swing.JButton();
        jPanel_HoaDon = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_sdtKH = new javax.swing.JTextField();
        jlb_voucher = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_thanhtien = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_tienKhachTra = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_tongTien = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btn_taoHoaDon = new javax.swing.JButton();
        jlb_maHoaDon = new javax.swing.JLabel();
        btn_thanhToan = new javax.swing.JButton();
        btn_huyHoaDon = new javax.swing.JButton();
        txt_tienThua = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_tenKH = new javax.swing.JTextField();
        btn_tru = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_maVoucher = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_phanTramGiam = new javax.swing.JTextField();
        jlb_giamgia = new javax.swing.JLabel();
        btn_apDung = new javax.swing.JButton();
        btn_huyKM = new javax.swing.JButton();
        btn_huyKH = new javax.swing.JButton();
        btn_chonKH = new javax.swing.JButton();

        jPanel_BanHang.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_BanHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel_TableHD.setBackground(new java.awt.Color(255, 255, 204));
        jPanel_TableHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setBackground(new java.awt.Color(255, 153, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Hóa Đơn Chờ");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_hoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Hóa Đơn ", "Tên Nhân Viên ", "Tên Khách Hàng", "Mã KM", "Ngày Tạo ", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_hoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_hoadonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_hoadon);

        javax.swing.GroupLayout jPanel_TableHDLayout = new javax.swing.GroupLayout(jPanel_TableHD);
        jPanel_TableHD.setLayout(jPanel_TableHDLayout);
        jPanel_TableHDLayout.setHorizontalGroup(
            jPanel_TableHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel_TableHDLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_TableHDLayout.setVerticalGroup(
            jPanel_TableHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TableHDLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jPanel_GioHang.setBackground(new java.awt.Color(255, 255, 204));
        jPanel_GioHang.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setBackground(new java.awt.Color(255, 153, 102));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Giỏ Hàng");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_giohang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id ctsp", "Tên Sản Phẩm", "Số Lượng ", "Chất Liệu", "Màu Sắc", "Hãng", "Size", "Đơn Giá", "Trạng Thái "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_giohang);
        if (tb_giohang.getColumnModel().getColumnCount() > 0) {
            tb_giohang.getColumnModel().getColumn(0).setMaxWidth(10);
        }

        btn_update.setText("Chỉnh Sửa");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setText("Xóa");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_GioHangLayout = new javax.swing.GroupLayout(jPanel_GioHang);
        jPanel_GioHang.setLayout(jPanel_GioHangLayout);
        jPanel_GioHangLayout.setHorizontalGroup(
            jPanel_GioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_GioHangLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_GioHangLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_clear)
                .addGap(18, 18, 18)
                .addComponent(btn_update)
                .addGap(47, 47, 47)
                .addComponent(btn_delete)
                .addGap(110, 110, 110))
        );
        jPanel_GioHangLayout.setVerticalGroup(
            jPanel_GioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_GioHangLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel_GioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_update)
                    .addComponent(btn_delete)
                    .addComponent(btn_clear))
                .addContainerGap())
        );

        jPanel_SanPham.setBackground(new java.awt.Color(255, 255, 204));
        jPanel_SanPham.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setBackground(new java.awt.Color(255, 153, 102));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Sản Phẩm ");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_HdSp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên Sản Phẩm", "Số Lượng ", "Giá Bán", "Danh Mục ", "Chất liệu", "Size", "Hãng", "Đế", "Màu Sắc "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_HdSp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_HdSpMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tb_HdSp);
        if (tb_HdSp.getColumnModel().getColumnCount() > 0) {
            tb_HdSp.getColumnModel().getColumn(0).setMaxWidth(10);
        }

        btn_prev.setText("<<");
        btn_prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prevActionPerformed(evt);
            }
        });

        btn_next.setText(">>");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_timkiemsp.setText("Tìm Kiếm");
        btn_timkiemsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timkiemspActionPerformed(evt);
            }
        });

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_SanPhamLayout = new javax.swing.GroupLayout(jPanel_SanPham);
        jPanel_SanPham.setLayout(jPanel_SanPhamLayout);
        jPanel_SanPhamLayout.setHorizontalGroup(
            jPanel_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel_SanPhamLayout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(btn_prev)
                .addGap(18, 18, 18)
                .addComponent(lbl_page)
                .addGap(18, 18, 18)
                .addComponent(btn_next)
                .addGap(279, 279, Short.MAX_VALUE))
            .addGroup(jPanel_SanPhamLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addComponent(btn_timkiemsp, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_timkiemsp, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_them, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel_SanPhamLayout.setVerticalGroup(
            jPanel_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_SanPhamLayout.createSequentialGroup()
                .addGroup(jPanel_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel_SanPhamLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_timkiemsp)
                            .addComponent(txt_timkiemsp, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_them))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel_SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_prev)
                    .addComponent(btn_next)
                    .addComponent(lbl_page))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_BanHangLayout = new javax.swing.GroupLayout(jPanel_BanHang);
        jPanel_BanHang.setLayout(jPanel_BanHangLayout);
        jPanel_BanHangLayout.setHorizontalGroup(
            jPanel_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BanHangLayout.createSequentialGroup()
                .addGroup(jPanel_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel_SanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_TableHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_GioHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_BanHangLayout.setVerticalGroup(
            jPanel_BanHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BanHangLayout.createSequentialGroup()
                .addComponent(jPanel_TableHD, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel_GioHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_SanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_HoaDon.setBackground(new java.awt.Color(255, 255, 204));
        jPanel_HoaDon.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Form Hóa Đơn");

        jLabel6.setText("Mã Hóa Đơn :");

        jLabel7.setText("Sđt Khách Hàng:");

        jlb_voucher.setText("Chọn Voucher");
        jlb_voucher.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 255, 102), null));
        jlb_voucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlb_voucherMouseClicked(evt);
            }
        });

        jLabel11.setText("Thành Tiền :");

        txt_thanhtien.setEditable(false);

        jLabel12.setText("Tiền Khách Trả :");

        jLabel13.setText("Tiền Thừa");

        jLabel14.setText("Tổng Tiền :");

        txt_tongTien.setEditable(false);

        jLabel15.setText("VND");

        jLabel16.setText("VND");

        jLabel17.setText("VND");

        jLabel18.setText("VND");

        btn_taoHoaDon.setBackground(new java.awt.Color(102, 255, 102));
        btn_taoHoaDon.setText("Tạo Hóa Đơn");
        btn_taoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taoHoaDonActionPerformed(evt);
            }
        });

        btn_thanhToan.setBackground(new java.awt.Color(102, 255, 102));
        btn_thanhToan.setText("Thanh Toán");
        btn_thanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thanhToanActionPerformed(evt);
            }
        });

        btn_huyHoaDon.setBackground(new java.awt.Color(255, 102, 102));
        btn_huyHoaDon.setText("Hủy Hóa Đơn");
        btn_huyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyHoaDonActionPerformed(evt);
            }
        });

        txt_tienThua.setEditable(false);

        jLabel8.setText("Tên Khách Hàng : ");

        btn_tru.setText("Trừ");
        btn_tru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_truActionPerformed(evt);
            }
        });

        jLabel4.setText("Mã :");

        jLabel9.setText("Giảm:");

        btn_apDung.setBackground(new java.awt.Color(204, 255, 204));
        btn_apDung.setText("Áp Dụng");
        btn_apDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apDungActionPerformed(evt);
            }
        });

        btn_huyKM.setBackground(new java.awt.Color(255, 204, 204));
        btn_huyKM.setText("Hủy");
        btn_huyKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyKMActionPerformed(evt);
            }
        });

        btn_huyKH.setBackground(new java.awt.Color(255, 204, 204));
        btn_huyKH.setText("Hủy");
        btn_huyKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyKHActionPerformed(evt);
            }
        });

        btn_chonKH.setBackground(new java.awt.Color(204, 255, 204));
        btn_chonKH.setText("Chọn Khách Hàng");
        btn_chonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chonKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_HoaDonLayout = new javax.swing.GroupLayout(jPanel_HoaDon);
        jPanel_HoaDon.setLayout(jPanel_HoaDonLayout);
        jPanel_HoaDonLayout.setHorizontalGroup(
            jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_maVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_phanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlb_giamgia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addComponent(txt_tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15))
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addComponent(txt_thanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16))
                            .addComponent(jLabel14)
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addGap(107, 107, 107)
                                .addComponent(jLabel5))
                            .addComponent(jLabel6)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addComponent(jlb_voucher)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_apDung)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_huyKM))
                            .addComponent(jLabel11)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_HoaDonLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btn_taoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_huyHoaDon))
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                        .addComponent(txt_tienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_tru, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_tienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17))))
                    .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(btn_thanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel_HoaDonLayout.createSequentialGroup()
                                .addComponent(btn_chonKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_huyKH))
                            .addComponent(jlb_maHoaDon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel_HoaDonLayout.setVerticalGroup(
            jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_HoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlb_maHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_huyKH)
                    .addComponent(btn_chonKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_sdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(11, 11, 11)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlb_voucher, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_apDung)
                    .addComponent(btn_huyKM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_maVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_phanTramGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jlb_giamgia, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_thanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tienKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(btn_tru))
                    .addComponent(txt_tienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_HoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_huyHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_taoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(btn_thanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_BanHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel_HoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_BanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel_HoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_taoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taoHoaDonActionPerformed
        // TODO add your handling code here:

        ArrayList<HoaDon> list = hoaDonChoRepository.getAllHoaDonCho();
        if (list.size() >= 5) {
            JOptionPane.showMessageDialog(this, "Số lượng tối đa 5 hóa đơn !");
            return;
        } else {
            hoaDonChoRepository.addHoaDonCho();
        }
        DefaultTableModel model = (DefaultTableModel) tb_giohang.getModel();
        model.setRowCount(0);

        fillTabelHoaDonCho();
        fillTableGioHang();
        clearFormHoaDon();
        JOptionPane.showMessageDialog(this, "Thêm thành công !");

    }//GEN-LAST:event_btn_taoHoaDonActionPerformed

    private void btn_timkiemspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timkiemspActionPerformed
        // TODO add your handling code here:
        String search = txt_timkiemsp.getText();
        if (search.equals("")) {
            return;
        }
        ArrayList<ChiTietDep> list = getAllSanPhamTimKiem(search);

        DefaultTableModel def = (DefaultTableModel) tb_HdSp.getModel();
        def.setRowCount(0);
        for (ChiTietDep sp : list) {
            Object[] data = {
                sp.getIdSanPham(),
                sp.getIdSanPham().getTen(), sp.getSoLuong(), sp.getGiaBan(),
                sp.getIdDanhMuc().getTen(), sp.getIdChatLieu().getTen(),
                sp.getIdSize().getKichCo(), sp.getIdNSX().getTen(), sp.getIdDe().getTen(), sp.getIdMauSac().getMauSac()
            };
            def.addRow(data);
        }

    }//GEN-LAST:event_btn_timkiemspActionPerformed

    private void btn_prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prevActionPerformed
        if (soTrang > 1) {
            soTrang--;
            fillTableSanPham();
            lbl_page.setText("" + soTrang);
            System.out.println(soTrang);
        }

    }//GEN-LAST:event_btn_prevActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        int tong = banHangRepository.tongSoItem();
        tongsoTrang = tong / 2;
        if (soTrang < tongsoTrang) {
            soTrang++;
            lbl_page.setText("" + soTrang);
            fillTableSanPham();
            System.out.println(soTrang);
        }
    }//GEN-LAST:event_btn_nextActionPerformed

    private void tb_HdSpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_HdSpMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tb_HdSpMouseClicked

    private void btn_huyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyHoaDonActionPerformed
        // TODO add your handling code here:
        int row = tb_hoadon.getSelectedRow();
        if (row <= -1) {
            JOptionPane.showMessageDialog(this, "Phải chọn 1 dòng !");
        } else {
            JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa hóa đơn '" + tb_hoadon.getValueAt(row, 0) + "' không ???", "Xóa hóa đơn chờ !", JOptionPane.YES_NO_OPTION);
            String maHoaDon = tb_hoadon.getValueAt(row, 0).toString();
            clearFormGioHang();
            hoaDonChoRepository.xoaHoaDonCho(maHoaDon);

            DefaultTableModel model = (DefaultTableModel) tb_giohang.getModel();
            model.setRowCount(0);

            fillTabelHoaDonCho();
            fillTableGioHang();
            clearFormHoaDon1();

            JOptionPane.showMessageDialog(this, "Xóa thành công !");
        }
    }//GEN-LAST:event_btn_huyHoaDonActionPerformed

    private void tb_hoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_hoadonMouseClicked
        // TODO add your handling code here:
        showDetailHDC();
        fillTableGioHang();

        int rowHD = tb_hoadon.getSelectedRow();

        if (rowHD >= 0) {
            // Kiểm tra xem giá trị có phải là null hay không trước khi sử dụng
            Object tenKHObject = tb_hoadon.getValueAt(rowHD, 2);
            String tenKH = (tenKHObject != null) ? tenKHObject.toString() : "";
            txt_tenKH.setText(tenKH);

            Object maKMObject = tb_hoadon.getValueAt(rowHD, 3);
            String maKM = (maKMObject != null) ? maKMObject.toString() : "";
            txt_maVoucher.setText(maKM);

            int row = tb_hoadon.getSelectedRow();
            String maHD = tb_hoadon.getValueAt(row, 0).toString();
            String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
            String sdt = hoaDonChoRepository.getSDTKHbyIDHD(idHD);
            txt_sdtKH.setText(sdt);

            int row1 = tb_hoadon.getSelectedRow();
            String maKM1 = tb_hoadon.getValueAt(row1, 3).toString();
            KhuyenMai khuyenMai = hoaDonChoRepository.getHinThucGiamibyMa(maKM1);

            if (khuyenMai != null) {
                int hinhThucGiam = khuyenMai.getHinhThucGiam();
                if (hinhThucGiam == 1) {

                    String ptg = hoaDonChoRepository.getPhanTramGiambyIdHD(idHD);
                    txt_phanTramGiam.setText(ptg);
                    jlb_giamgia.setText("%");

                    try {
                        String tongTienStr = txt_tongTien.getText().trim().replace(",", "");
                        String phanTramGiamStr = txt_phanTramGiam.getText().trim();

                        if (!tongTienStr.isEmpty() && !phanTramGiamStr.isEmpty()) {
                            Double tongtien = Double.parseDouble(tongTienStr);
                            Double phanTramGiam = Double.parseDouble(phanTramGiamStr);

                            Double thanhTien = tongtien - ((tongtien * phanTramGiam) / 100);

                            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                            String formattedThanhTien = decimalFormat.format(thanhTien);
                            System.out.println("thanh tien: " + thanhTien);

                            txt_thanhtien.setText(formattedThanhTien);

                        } else {

                        }
                    } catch (NumberFormatException e) {

                    }

                } else if (hinhThucGiam == 0) {

                    String ptg = hoaDonChoRepository.getGiaGiambyIdHD(idHD);
                    txt_phanTramGiam.setText(ptg);
                    jlb_giamgia.setText("VND");

                    try {
                        String tongTienStr = txt_tongTien.getText().trim().replace(",", "");
                        String phanTramGiamStr = txt_phanTramGiam.getText().trim();

                        if (!tongTienStr.isEmpty() && !phanTramGiamStr.isEmpty()) {
                            Double tongtien = Double.parseDouble(tongTienStr);
                            Double phanTramGiam = Double.parseDouble(phanTramGiamStr);

                            Double thanhTien = tongtien - phanTramGiam;

                            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                            String formattedThanhTien = decimalFormat.format(thanhTien);
                            System.out.println("thanh tien: " + thanhTien);

                            txt_thanhtien.setText(formattedThanhTien);

                        } else {

                        }
                    } catch (NumberFormatException e) {

                    }

                }
            } else {
                String tongTienStr = txt_tongTien.getText().trim().replace(",", "");
                Double tongtien = Double.parseDouble(tongTienStr);
                Double thanhTien = tongtien;
                DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                String formattedThanhTien = decimalFormat.format(thanhTien);
                System.out.println("thanh tien: " + thanhTien);
                txt_thanhtien.setText(formattedThanhTien);
            }

            btn_chonKH.setEnabled(true);
            jlb_voucher.setEnabled(true);
            if (row != -1) {
                selectedRowInBanHang = row;
                System.out.println("row:" + row);
            }
        }

    }//GEN-LAST:event_tb_hoadonMouseClicked

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        int rowGH = tb_giohang.getSelectedRow();
        int rowDDSP = tb_HdSp.getSelectedRow();

        if (rowGH <= -1) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng để xóa !");
            return;
        } else {
            int cf = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm '" + tb_giohang.getValueAt(rowGH, 1) + "' ra khỏi giỏ hàng không ?", "Xóa giỏ hàng !", JOptionPane.YES_NO_OPTION);
            if (cf == JOptionPane.NO_OPTION) {
                return;
            } else {
                String idHDCT = tb_giohang.getValueAt(rowGH, 0).toString();
                int sl_hdct = Integer.parseInt(tb_giohang.getValueAt(rowGH, 2).toString());
                String idCTSP = hoaDonChoRepository.getIdCTSPbyIdHDCT(idHDCT);
                int sl_ctsp = hoaDonChoRepository.getSoLuongByIdCTSP(idCTSP) + sl_hdct;
                hoaDonChoRepository.updteSoLuongChiTietSanPham(sl_ctsp, idCTSP);
                hoaDonChoRepository.xoaHoaDonCT(idHDCT);
                fillTableGioHang();
                fillTableSanPham();
                showDetailHDC();
                clearFormHoaDon1();
                JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm khỏi giỏ hàng !");
            }

        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        // TODO add your handling code here:
        int sl = 0;
        int rowGH = tb_giohang.getSelectedRow();

        if (rowGH <= -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn 1 dòng để sửa !");
            return;
        }
        Double giaBan = new Double(tb_giohang.getValueAt(rowGH, 7).toString());
        String idHDCT = tb_giohang.getValueAt(rowGH, 0).toString();
        String idCTSP = hoaDonChoRepository.getIdCTSPbyIdHDCT(idHDCT);
        int sl_ctsp = hoaDonChoRepository.getSoLuongByIdCTSP(idCTSP);
        int sl_gh = Integer.parseInt(tb_giohang.getValueAt(rowGH, 2).toString());

        String slNhap = JOptionPane.showInputDialog(this, "Số lượng sản phẩm trong cửa hàng là :"
                + sl_ctsp
                + "\n Nhập số lượng sản phẩm muốn mua: ");
        if (!StringUtils.isNumeric(slNhap)) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số !");
            return;
        }
        if (slNhap == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng !");
            return;
        }
        sl = Integer.parseInt(slNhap);
        if (sl <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương !");
            return;
        }
        if (sl > sl_ctsp) {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong cửa hàng không đủ !");
            return;
        }

        Double donGia = giaBan * sl;

        // Tạo đối tượng JOptionPane
        JOptionPane optionPane = new JOptionPane(
                "----------- THÔNG TIN SẢN PHẨM ---------\n"
                + "\nTên sản phẩm: " + tb_HdSp.getValueAt(rowGH, 1)
                + "\n"
                + "\nSố lượng:" + sl
                + "\n"
                + "\nĐơn giá: " + donGia,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION
        );

// Thiết lập kích thước cho JOptionPane
        optionPane.setPreferredSize(new Dimension(400, 200)); // Chọn kích thước theo nhu cầu của bạn

// Tạo hộp thoại JDialog
        JDialog dialog = optionPane.createDialog(this, "Cập nhật số lượng sản phẩm giỏ hàng !");

// Đặt vị trí của JDialog ở giữa màn hình
        dialog.setLocationRelativeTo(null);

// Hiển thị hộp thoại và xử lý kết quả khi người dùng nhấn Yes hoặc No
        dialog.setVisible(true);

// Lấy kết quả khi hộp thoại đóng lại
        int result = (int) optionPane.getValue();

// Xử lý kết quả
        if (result == JOptionPane.YES_OPTION) {
            hoaDonChoRepository.updateSoLuongChiTietHoaDonbyId(sl, donGia, idHDCT);
            hoaDonChoRepository.updteSoLuongChiTietSanPham(sl_ctsp - sl + sl_gh, idCTSP);
            fillTableGioHang();
            fillTableSanPham();
            showDetailHDC();
            JOptionPane.showMessageDialog(this, "Đã cập nhật số lượng !");
        }

    }//GEN-LAST:event_btn_updateActionPerformed
    public void clearFormGioHang() {
        ArrayList<HoaDonChiTiet> listCTHD = hoaDonChoRepository.getAllHoaDonTatCa();
        int rowHD = tb_hoadon.getSelectedRow();
        String maHD = tb_hoadon.getValueAt(rowHD, 0).toString();
        String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
        hoaDonChoRepository.xoaHoaDonChiTiet(idHD);

        for (HoaDonChiTiet hoaDonChiTiet : listCTHD) {
            String chiTietDep = hoaDonChiTiet.getIdChiTietDep();
            int slnew = hoaDonChiTiet.getSoLuong();
            hoaDonChoRepository.capNhatSoLuongChiTietSanPham(chiTietDep, slnew);
        }
        DefaultTableModel model = (DefaultTableModel) tb_giohang.getModel();
        model.setRowCount(0);
        // fillTableGioHang();
        // fillTableSanPham();
        showDetailHDC();

    }

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed

        ArrayList<HoaDonChiTiet> listCTHD = hoaDonChoRepository.getAllHoaDonTatCa();
        int rowHD = tb_hoadon.getSelectedRow();
        String maHD = tb_hoadon.getValueAt(rowHD, 0).toString();
        String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
        hoaDonChoRepository.xoaHoaDonChiTiet(idHD);

        for (HoaDonChiTiet hoaDonChiTiet : listCTHD) {
            String chiTietDep = hoaDonChiTiet.getIdChiTietDep();
            int slnew = hoaDonChiTiet.getSoLuong();
            hoaDonChoRepository.capNhatSoLuongChiTietSanPham(chiTietDep, slnew);
        }
        fillTableGioHang();
        fillTableSanPham();
        showDetailHDC();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        // TODO add your handling code here:
        int rowHDC = tb_hoadon.getSelectedRow();
        int rowDSSP = tb_HdSp.getSelectedRow();
        int sl = 0;
        if (rowHDC <= -1) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn hóa đơn để thêm sản phẩm !");
            return;
        }
        String slNhap = JOptionPane.showInputDialog(this, "Nhập số lượng sản phẩm !");
        if (slNhap == null) {
            JOptionPane.showMessageDialog(this, "Mời nhập số lượng sản phẩm !");
            return;
        }
        sl = Integer.parseInt(slNhap);
        if (sl <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên dương !");
            return;
        }
        if (sl > Integer.parseInt(tb_HdSp.getValueAt(rowDSSP, 2).toString())) {
            JOptionPane.showMessageDialog(this, "Số lượng trong cửa hàng không đủ !");
            return;
        }

        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setIdHoaDon(hoaDonChoRepository.getIdHoaDonByMa(tb_hoadon.getValueAt(rowHDC, 0).toString()));
        hdct.setIdChiTietDep(tb_HdSp.getValueAt(rowDSSP, 0).toString());

        Double dongia = Double.parseDouble(tb_HdSp.getValueAt(rowDSSP, 3).toString());
        Integer soLuong_Sp = new Integer(sl);
        Double thanhTien = dongia * soLuong_Sp;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedThanhTien = decimalFormat.format(thanhTien);

        hdct.setDonGia(thanhTien);
        hdct.setSoLuong(sl);

        ChiTietDep ctd = new ChiTietDep();

        if (CheckTrung() == true) {
            JOptionPane.showMessageDialog(this, "Sản phẩm này đã có trong hóa đơn rồi !");
            return;
        } else {

            Integer slsp_sau = Integer.parseInt(tb_HdSp.getValueAt(rowDSSP, 2).toString()) - sl;
            hoaDonChoRepository.updteSoLuongChiTietSanPham(slsp_sau, tb_HdSp.getValueAt(rowDSSP, 0).toString());
            // Tạo đối tượng JOptionPane
            JOptionPane optionPane = new JOptionPane(
                    "----------- THÔNG TIN SẢN PHẨM ---------\n"
                    + "\nTên sản phẩm: " + tb_HdSp.getValueAt(rowDSSP, 1)
                    + "\n"
                    + "\nSố lượng:" + sl
                    + "\n"
                    + "\nThành tiền: " + formattedThanhTien,
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.YES_NO_OPTION
            );

            optionPane.setPreferredSize(new Dimension(400, 200)); // Chọn kích thước theo nhu cầu của bạn
            JDialog dialog = optionPane.createDialog(this, "Thêm sản phẩm vào giỏ hàng !");
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            int result = (int) optionPane.getValue();

            if (result == JOptionPane.YES_OPTION) {
                hoaDonChoRepository.addHoaDonChiTiet(hdct);
                fillTableGioHang();
                fillTableSanPham();
                showDetailHDC();
            }
        }
    }//GEN-LAST:event_btn_themActionPerformed

    public void loadThanhToan() {
        txt_sdtKH.setText("");
        txt_tenKH.setText("");
        txt_thanhtien.setText("");
        txt_tienKhachTra.setText("");
        txt_tongTien.setText("");
        txt_maVoucher.setText("");
        txt_phanTramGiam.setText("");
        fillTableGioHang();
        fillTableSanPham();
        fillTabelHoaDonCho();
    }

    public void clearFormHoaDon() {
        txt_sdtKH.setText("");
        txt_tenKH.setText("");
        txt_thanhtien.setText("");
        txt_tienKhachTra.setText("");
        txt_tongTien.setText("");
        //  txt_tienThua.setText("");
    }

    public void clearFormHoaDon1() {
        jlb_maHoaDon.setText("");
        txt_sdtKH.setText("");
        txt_tenKH.setText("");
        txt_thanhtien.setText("");
        txt_tienKhachTra.setText("");
        txt_tongTien.setText("");
        txt_tienThua.setText("");
        txt_tienKhachTra.setText("");
        txt_maVoucher.setText("");
        txt_phanTramGiam.setText("");
    }

    private boolean isValidNumberInput(String input) {
        return input.matches("-?\\d+(\\.\\d+)?");
    }
    private void btn_thanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thanhToanActionPerformed

        try {
            String tienKhachTraText = txt_tienKhachTra.getText().replaceAll(",", "");
            String thanhTienText = txt_thanhtien.getText().replaceAll(",", "");

            if (!isValidNumberInput(tienKhachTraText) || !isValidNumberInput(thanhTienText)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền khách trả là số !");
                // return;
            }

            Double tienKhachTra = Double.valueOf(tienKhachTraText);
            Double thanhTien = Double.valueOf(thanhTienText);
            Double tienThua = tienKhachTra - thanhTien;
            // Định dạng số cho việc hiển thị
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String tienThuaFormatted = decimalFormat.format(tienThua);

            if (tienThua < 0) {
                JOptionPane.showMessageDialog(this, "Tiền khách trả không đủ để thanh toán !");
            } else {
                txt_tienThua.setText(tienThuaFormatted);
                int rowHD = tb_hoadon.getSelectedRow();
                String maHD = tb_hoadon.getValueAt(rowHD, 0).toString();
                String idHD = hoaDonChoRepository.getIdHoaDonByMa(maHD);
                hoaDonChoRepository.updateTrangThaiHoaDon(idHD);
                hoaDonChoRepository.ngayThanhToanHoaDon(idHD);
                clearFormGioHang();
                clearFormHoaDon1();
                fillTableGioHang();
//                DefaultTableModel model = (DefaultTableModel) tb_giohang.getModel();
//                model.setRowCount(0);
                JOptionPane.showMessageDialog(this, "Đã thanh toán thành công !");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ.");
        }

    }//GEN-LAST:event_btn_thanhToanActionPerformed

    private void btn_truActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_truActionPerformed
        // TODO add your handling code here:
        try {
            String tienKhachTraText = txt_tienKhachTra.getText().replaceAll(",", "");
            String thanhTienText = txt_thanhtien.getText().replaceAll(",", "");

            if (!isValidNumberInput(tienKhachTraText) || !isValidNumberInput(thanhTienText)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền khách trả là số !");
                // return;
            }

            Double tienKhachTra = Double.valueOf(tienKhachTraText);
            Double thanhTien = Double.valueOf(thanhTienText);
            Double tienThua = tienKhachTra - thanhTien;
            // Định dạng số cho việc hiển thị
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String tienThuaFormatted = decimalFormat.format(tienThua);
            txt_tienThua.setText(tienThuaFormatted);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ.");
        }
    }//GEN-LAST:event_btn_truActionPerformed

    private void jlb_voucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlb_voucherMouseClicked
        // TODO add your handling code here:
        try {
            new Form_KhuyenMaiBanHang(this, tb_hoadon).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showDetailHDC();


    }//GEN-LAST:event_jlb_voucherMouseClicked

    private void btn_apDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apDungActionPerformed
        // TODO add your handling code here:

        showDetailHDC();

        int row = tb_hoadon.getSelectedRow();
        String maKM = tb_hoadon.getValueAt(row, 3).toString();
        KhuyenMai khuyenMai = hoaDonChoRepository.getHinThucGiamibyMa(maKM);

        if (khuyenMai != null) {
            int hinhThucGiam = khuyenMai.getHinhThucGiam();
            if (hinhThucGiam != 0) {
                try {
                    String tongTienStr = txt_tongTien.getText().trim().replace(",", "");
                    String phanTramGiamStr = txt_phanTramGiam.getText().trim();

                    if (!tongTienStr.isEmpty() && !phanTramGiamStr.isEmpty()) {
                        Double tongtien = Double.parseDouble(tongTienStr);
                        Double phanTramGiam = Double.parseDouble(phanTramGiamStr);

                        Double thanhTien = tongtien - ((tongtien * phanTramGiam) / 100);

                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                        String formattedThanhTien = decimalFormat.format(thanhTien);
                        System.out.println("thanh tien: " + thanhTien);

                        txt_thanhtien.setText(formattedThanhTien);

                    } else {
                        System.out.println("Nhập giá trị cho tổng tiền và phần trăm giảm trước khi tính.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi chuyển đổi số: " + e.getMessage());
                }
                System.out.println("Giam Theo Phan Tram: " + hinhThucGiam);
            } else {
                try {
                    String tongTienStr = txt_tongTien.getText().trim().replace(",", "");
                    String phanTramGiamStr = txt_phanTramGiam.getText().trim();

                    if (!tongTienStr.isEmpty() && !phanTramGiamStr.isEmpty()) {
                        Double tongtien = Double.parseDouble(tongTienStr);
                        Double phanTramGiam = Double.parseDouble(phanTramGiamStr);

                        Double thanhTien = tongtien - phanTramGiam;

                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                        String formattedThanhTien = decimalFormat.format(thanhTien);
                        System.out.println("thanh tien: " + thanhTien);

                        txt_thanhtien.setText(formattedThanhTien);

                    } else {
                        System.out.println("Nhập giá trị cho tổng tiền và phần trăm giảm trước khi tính.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi chuyển đổi số: " + e.getMessage());
                }
                System.out.println("Giam Theo Tien: " + hinhThucGiam);
            }
        } else {
            // Xử lý khi không có đối tượng KhuyenMai được trả về
        }


    }//GEN-LAST:event_btn_apDungActionPerformed

    private void btn_huyKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyKHActionPerformed
        // TODO add your handling code here:
        int indexHD = tb_hoadon.getSelectedRow();
        if (indexHD < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn để xóa khách hàng !");
            return;
        }
        String maHD = tb_hoadon.getValueAt(indexHD, 0).toString();
        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn hủy chọn khách hàng '" + tb_hoadon.getValueAt(indexHD, 2) + "' không ?", "Message", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        hoaDonChoRepository.xoaKhachHangKhoiHoaDon(maHD);
        txt_tenKH.setText("Khách lẻ");
        txt_sdtKH.setText("");
        JOptionPane.showMessageDialog(null, "Hủy chọn thành công");
        fillTabelHoaDonCho();


    }//GEN-LAST:event_btn_huyKHActionPerformed

    private void btn_huyKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyKMActionPerformed
        // TODO add your handling code here:
        int indexHD = tb_hoadon.getSelectedRow();
        if (indexHD < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn để xóa Voucher !");
            return;
        }
        String maHD = tb_hoadon.getValueAt(indexHD, 0).toString();
        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn hủy chọn voucher '" + tb_hoadon.getValueAt(indexHD, 3) + "' không ?", "Message", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        hoaDonChoRepository.xoaKhuyenMaiKhoiHoaDon(maHD);
        txt_maVoucher.setText("0");
        txt_phanTramGiam.setText("");
        txt_thanhtien.setText("");

        showDetailHDC();

        String tongTienStr = txt_tongTien.getText().trim().replace(",", "");

        Double tongtien = Double.parseDouble(tongTienStr);

        Double thanhTien = tongtien;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedThanhTien = decimalFormat.format(thanhTien);
        System.out.println("thanh tien: " + thanhTien);

        txt_thanhtien.setText(formattedThanhTien);

        JOptionPane.showMessageDialog(null, "Hủy chọn thành công");
        fillTabelHoaDonCho();
    }//GEN-LAST:event_btn_huyKMActionPerformed

    private void btn_chonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chonKHActionPerformed
        // TODO add your handling code here:
        try {
            new Form_KhachHangBanHang(this, tb_hoadon).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_chonKHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_apDung;
    private javax.swing.JButton btn_chonKH;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_huyHoaDon;
    private javax.swing.JButton btn_huyKH;
    private javax.swing.JButton btn_huyKM;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_prev;
    private javax.swing.JButton btn_taoHoaDon;
    private javax.swing.JButton btn_thanhToan;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_timkiemsp;
    private javax.swing.JButton btn_tru;
    private javax.swing.JButton btn_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel_BanHang;
    private javax.swing.JPanel jPanel_GioHang;
    private javax.swing.JPanel jPanel_HoaDon;
    private javax.swing.JPanel jPanel_SanPham;
    private javax.swing.JPanel jPanel_TableHD;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlb_giamgia;
    private javax.swing.JLabel jlb_maHoaDon;
    private javax.swing.JLabel jlb_voucher;
    private javax.swing.JLabel lbl_page;
    private javax.swing.JTable tb_HdSp;
    private javax.swing.JTable tb_giohang;
    private javax.swing.JTable tb_hoadon;
    private javax.swing.JTextField txt_maVoucher;
    private javax.swing.JTextField txt_phanTramGiam;
    private javax.swing.JTextField txt_sdtKH;
    private javax.swing.JTextField txt_tenKH;
    private javax.swing.JTextField txt_thanhtien;
    private javax.swing.JTextField txt_tienKhachTra;
    private javax.swing.JTextField txt_tienThua;
    private javax.swing.JTextField txt_timkiemsp;
    private javax.swing.JTextField txt_tongTien;
    // End of variables declaration//GEN-END:variables
}
