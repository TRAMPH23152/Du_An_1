/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.ChiTietDep;
import DomainModels.GioHang;
import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.KhachHang;
import DomainModels.KhuyenMai;
import DomainModels.NguoiDung;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import Utilities.DBConnection;
import Utilities.JDBCHelper;
import ViewModel.HoaDonChiTietViewModel;
import java.time.LocalDate;

/**
 *
 * @author asus
 */
public class HoaDonChoRepository {

    ChiTietSanPhamRepository ctsp = new ChiTietSanPhamRepository();

    public long generateRandomId() {
        return 10000 + new Random().nextInt(90000);

    }

    public ArrayList<HoaDon> getAllHoaDonCho() {
        ArrayList<HoaDon> listHD = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT HOADON.Id, HOADON.Ma, HOADON.TongTien, HOADON.ThanhTien, HOADON.NgayTao, HOADON.TrangThai,\n"
                    + "    NGUOIDUNG.Id AS IDND, NGUOIDUNG.Ma AS MAND, NGUOIDUNG.Ten AS TENND,\n"
                    + "    KHACHHANG.Id AS IDKH, KHACHHANG.Ten AS TENKH, KHACHHANG.Sdt AS SDTKH,\n"
                    + "    KHUYENMAI.Id AS IDKM, KHUYENMAI.Ma AS MAKM, KHUYENMAI.PhanTramGiam AS PTGKM\n"
                    + "FROM HOADON\n"
                    + "LEFT JOIN NGUOIDUNG ON HOADON.IdND = NGUOIDUNG.Id\n"
                    + "LEFT JOIN KHACHHANG ON HOADON.IdKH = KHACHHANG.Id\n"
                    + "LEFT JOIN KHUYENMAI ON HOADON.IdKM = KHUYENMAI.Id\n"
                    + "WHERE HOADON.TrangThai = 0;";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getString("Id"));
                hd.setMa(rs.getString("Ma"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                hd.setTrangThai(rs.getInt("TrangThai"));
                hd.setIdKhachHang(rs.getString("TENKH"));
                hd.setIdKhuyenMai(rs.getString("MAKM"));
                Integer idND = rs.getInt("IdND");
                NguoiDung nguoiDung = getNguoiDungById(idND);

                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;

    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonTatCa() {
        ArrayList<HoaDonChiTiet> listHD = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM HOADONCHITIET";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hd = new HoaDonChiTiet();
                hd.setId(rs.getString("Id"));
                hd.setIdChiTietDep(rs.getString("IdCTD"));
                hd.setSoLuong(rs.getInt("SoLuong"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                hd.setTrangThai(rs.getInt("TrangThai"));

                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;

    }

    public NguoiDung getNguoiDungById(Integer id) {
        NguoiDung nguoiDung = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM NGUOIDUNG WHERE Id =?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                nguoiDung = new NguoiDung();
                nguoiDung.setIdNguoiDung(rs.getInt("Id"));
                nguoiDung.setTenNguoiDung(rs.getString("Ten"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nguoiDung;
    }

    public Integer addHoaDonCho() {
        Integer row = null;
        String sql = "INSERT INTO HOADON (Ma, NgayTao, TrangThai) VALUES(?,?,?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ptm = con.prepareStatement(sql);
            Long maHD = generateRandomId();
            ptm.setString(1, "HD" + maHD);
            ptm.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ptm.setInt(3, 0);
            row = ptm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public void xoaHoaDonCho(String ma) {
        String sql = "DELETE FROM HOADON WHERE Ma = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, ma);
            ptm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void xoaHoaDonChiTiet(String id) {
        String sql = "DELETE  FROM HOADONCHITIET WHERE IdHD =? AND  TrangThai =0";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, id);
            ptm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Integer addHoaDonChiTiet(HoaDonChiTiet hdct) {
        Integer row = null;
        String sql = "INSERT INTO HOADONCHITIET(IdHD,IdCTD,SoLuong,DonGia,NgayTao,TrangThai) VALUES (?,?,?,?,?,?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ptm = con.prepareStatement(sql);

            ptm.setString(1, hdct.getIdHoaDon());
            ptm.setString(2, hdct.getIdChiTietDep());
            ptm.setInt(3, hdct.getSoLuong());
            ptm.setDouble(4, hdct.getDonGia());
            ptm.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            ptm.setInt(6, 0);

            row = ptm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public String getIdHoaDonByMa(String maHD) {
        String idHD = null;

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT Id FROM HOADON WHERE Ma = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maHD);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                idHD = rs.getString("Id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idHD;
    }

    public String getIDKHbyMa(String maKH) {
        String idKH = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT Id from KHACHHANG where Ma = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idKH = rs.getString("Id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idKH;
    }

    public String getIdKMbyMa(String maKM) {
        String idKM = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT Id from KHUYENMAI WHERE Ma = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maKM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idKM = rs.getString("Id");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idKM;
    }

    public String getSDTKHbyIDHD(String idHD) {
        String sdt = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT KHACHHANG.Sdt FROM HOADON JOIN KHACHHANG ON HOADON.IdKH = KHACHHANG.Id WHERE HOADON.Id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sdt = rs.getString("Sdt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdt;
    }

    public String getPhanTramGiambyIdHD(String idHD) {
        String ptg = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT KHUYENMAI.PhanTramGiam FROM HOADON JOIN KHUYENMAI ON HOADON.IdKM = KHUYENMAI.Id WHERE HOADON.Id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ptg = rs.getString("PhanTramGiam");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ptg;
    }
            public String getGiaGiambyIdHD(String idHD) {
        String ptg = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT KHUYENMAI.GiaGiam FROM HOADON JOIN KHUYENMAI ON HOADON.IdKM = KHUYENMAI.Id WHERE HOADON.Id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ptg = rs.getString("GiaGiam");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ptg;
    }

    public String getIdHoaDonCTByMaHD(String idHD) {
        String idHDCT = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT Id FROM HOADONCHITIET Where IdHD = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idHDCT);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                idHDCT = rs.getString("Id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idHDCT;

    }

    public String getIdHoaDonByMaHD(String maHD) {
        String maHDC = null;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT Id FROM HOADON Where Ma = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, maHDC);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                maHDC = rs.getString("Ma");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maHDC;

    }

    public ArrayList getAllGioHang(String idHD) {
        ArrayList<GioHang> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT HOADONCHITIET.Id, HOADONCHITIET.IdHD, HOADONCHITIET.IdCTD,HOADONCHITIET.SoLuong,HOADONCHITIET.DonGia, HOADONCHITIET.NgayTao,HOADONCHITIET.NgaySua,HOADONCHITIET.TrangThai,CHITIETDEP.Id,SANPHAM.Ten AS TENSP , NSX.Ten AS TENNSX, SIZE.KichCo , DANHMUC.Ten AS TENDM, CHATLIEU.Ten AS TENCL , MAUSAC.MauSac, DE.Ten AS TENDE, CHITIETDEP.GiaBan\n"
                    + "\n"
                    + "FROM HOADONCHITIET \n"
                    + "					JOIN CHITIETDEP ON HOADONCHITIET.IdCTD = CHITIETDEP.Id\n"
                    + "					JOIN  SANPHAM ON CHITIETDEP.IdSanPham = SANPHAM.Id\n"
                    + "					JOIN NSX ON CHITIETDEP.IdNSX = NSX.Id\n"
                    + "					JOIN DANHMUC ON CHITIETDEP.IdDanhMuc = DANHMUC.Id\n"
                    + "					JOIN CHATLIEU ON CHITIETDEP.IdChatLieu = CHATLIEU.Id\n"
                    + "					JOIN MAUSAC ON CHITIETDEP.IdMauSac = MAUSAC.Id\n"
                    + "					JOIN DE ON CHITIETDEP.IdDe = DE.Id\n"
                    + "					JOIN SIZE ON CHITIETDEP.IdSize = SIZE.Id\n"
                    + "					WHERE IdHD = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idHD);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                GioHang gh = new GioHang();
                gh.setId(rs.getString("Id"));
                gh.setSoLuong(rs.getInt("SoLuong"));
                gh.setNgayTao(rs.getDate("NgayTao"));
                gh.setDonGia(rs.getDouble("DonGia"));
                gh.setTrangThai(rs.getInt("TrangThai"));
                gh.setSanPham(rs.getString("TENSP"));
                gh.setDanhMuc(rs.getString("TENDM"));
                gh.setChatLieu(rs.getString("TENCL"));
                gh.setSize(rs.getString("KichCo"));
                gh.setNsx(rs.getNString("TENNSX"));
                gh.setDe(rs.getString("TENDE"));
                gh.setMauSac(rs.getString("MauSac"));
                list.add(gh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Integer updteSoLuongChiTietSanPham(Integer slsp, String Id) {
        Integer row = null;
        String sql = "UPDATE CHITIETDEP SET SoLuong = ? WHERE Id =?";
        try {
            Connection n = DBConnection.getConnection();
            PreparedStatement ptm = n.prepareCall(sql);
            ptm.setInt(1, slsp);
            ptm.setString(2, Id);
            row = ptm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public Integer updateSoLuongChiTietHoaDonbyId(Integer sl, Double donGia, String idCTHD) {
        Integer row = null;
        String sql = "UPDATE HOADONCHITIET SET SoLuong =?, DonGia =? WHERE Id =?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sl);
            ps.setDouble(2, donGia);
            ps.setString(3, idCTHD);
            row = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public String getIdCTSPbyIdHDCT(String idHDCT) {
        String idCTD = null;

        String sql = "SELECT IdCTD FROM HOADONCHITIET WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHDCT);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idCTD = rs.getString("IdCTD");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idCTD;
    }

    public String getIdKMbyIdHoaDon(String idHD) {
        String idKM = null;
        String sql = "SELECT IdKM From HOADON WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idKM);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                idKM = rs.getString("IdKM");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idKM;
    }

    public Integer getSoLuongByIdCTSP(String idCTSP) {
        Integer soLuong = null;

        String sql = "SELECT SoLuong FROM CHITIETDEP WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idCTSP);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt("SoLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuong;
    }

    public Double getTongTienByIdHD(String idHD) {
        Double tongTien = null;
        String sql = "SELECT TongTien FROM HOADON WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tongTien = rs.getDouble("TongTien");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongTien;
    }

    public Integer checkTrungCTSPinGH(String idHD, String idCTSP) {
        Integer row = 0;
        String sql = "SELECT * FROM HOADONCHITIET AS HDCT WHERE IdHD= ? AND HDCT.IdCTD =?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ps.setString(2, idCTSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                row = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public void xoaHoaDonCT(String id) {
        String sql = "DELETE FROM HOADONCHITIET WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, id);
            ptm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void capNhatSoLuongChiTietSanPham(String idCTSP, int slXoa) {

        String sql = "UPDATE CHITIETDEP SET SoLuong = SoLuong+? WHERE Id= ?";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, slXoa);
            ps.setString(2, idCTSP);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void capNhatTongTienHoaDon(String idHD, Double tongTien) {
        String sql = "UPDATE HOADON SET TongTien = TongTien+? WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, tongTien);
            ps.setString(2, idHD);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void capNhatThanhTienHoaDon(String idHD, Double thanhTien) {
        String sql = "UPDATE HOADON SET ThanhTien = ThanhTien-? WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ps.setDouble(2, thanhTien);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKHinHoaDon(String idHD, String idKH) {
        String sql = "UPDATE HOADON set IdKH =? where Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idKH);
            ps.setString(2, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKhuyenMaibyIdHoaDon(String idHD, String idKM) {

        String sql = "UPDATE HOADON SET IdKM = ? WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idKM);
            ps.setString(2, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateThanhTienbyIdHoaDon(String idHd, Double thanhTien) {
        String sql = "UPDATE HOADON SET ThanhTien = ? where Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTrangThaiHoaDon(String idHD) {
        String sql = "UPDATE HOADON SET TrangThai =1 WHERE Id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
public void ngayThanhToanHoaDon(String idHD){
    String sql = "UPDATE HOADON SET NgayThanhToan = ? WHERE Id = ?";
    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
        ps.setString(2, idHD);
        ps.executeUpdate();
        ps.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void xoaKhachHangKhoiHoaDon(String maHD) {
        String sql = "UPDATE HOADON SET IdKH = NULL WHERE Ma = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoaKhuyenMaiKhoiHoaDon(String maHD) {
        String sql = "UPDATE HOADON SET IdKM = NULL WHERE Ma = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHD);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<KhuyenMai> getAllVoucher() {
        ArrayList<KhuyenMai> km = new ArrayList<>();
        String sql = "SELECT Ma, Ten, PhanTramGiam,GiaGiam, SoLuong, NgayBatDau, NgayKetThuc,HinhThucGiam, MoTa FROM KHUYENMAI WHERE TrangThai = 1";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                km.add(new KhuyenMai(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getInt(6), rs.getDate(7), rs.getDate(8), rs.getInt(9), rs.getInt(10), rs.getString(11)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return km;
    }

    public Double getGiaTienHDCTtoTongTienHDbyIdHD(String idHD) {
        Double row = null;
        String sql = "SELECT SUM(DonGia) FROM HOADONCHITIET WHERE IdHD = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                row = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public KhachHang getKhachHangById(String id) {
        KhachHang khach = null;
        String sql = "SELECT * FROM KHACHHANG WHERE Id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    khach = new KhachHang();
                    khach.setId(rs.getInt("Id"));
                    khach.setTen(rs.getString("Ten"));
                    khach.setSdt(rs.getString("Sdt"));
                    // Thêm các trường thông tin khác nếu cần
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khach;
    }

    public KhuyenMai getKhuyenMaibyMa(String makm) {
        KhuyenMai km = null;
        String sql = "SELECT * FROM KHUYENMAI WHERE Ma = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, makm);
            ResultSet rs = ps.executeQuery();
            try {
                if (rs.next()) {
                    km = new KhuyenMai();
                    km.setId(rs.getInt("Id"));
                    km.setMa(rs.getString("Ma"));
                    km.setHinhThucGiam(rs.getInt("HinhThucGiam"));
                    km.setGiaGiam(rs.getDouble("GiaGiam"));
                    km.setPhanTramGiam(rs.getDouble("PhanTramGiam"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return km;
    }
    public KhuyenMai getHinThucGiamibyMa(String makm) {
        KhuyenMai km = null;
        String sql = "SELECT HinhThucGiam  FROM KHUYENMAI WHERE Ma = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, makm);
            ResultSet rs = ps.executeQuery();
            try {
                if (rs.next()) {
                    km = new KhuyenMai();
                    km.setHinhThucGiam(rs.getInt("HinhThucGiam"));
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return km;
    }
}
