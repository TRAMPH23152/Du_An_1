package Repository;

import DomainModels.HD;
import DomainModels.HoaDonCT;
import Utilities.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Interface.HDReponsitoryImpl;

public class HDReponsitory implements HDReponsitoryImpl {

    DBConnection cdao = new DBConnection();

    @Override
    public ArrayList<HD> getAll() {
        ArrayList<HD> dshd = new ArrayList<>();

        String sql = "select HOADON.Id, HOADON.Ma, NGUOIDUNG.Ma As MaNV , KHACHHANG.Ten As TenKH , NgayTao, TongTien, HOADON.TrangThai, MoTa\n"
                + "from HOADON join NGUOIDUNG On HOADON.IdND=NGUOIDUNG.Id join KHACHHANG On HOADON.IdKH= KHACHHANG.Id";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dshd.add(new HD(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getFloat(6),
                        rs.getInt(7),
                        rs.getString(8)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dshd;

    }

    @Override
    public ArrayList<HoaDonCT> getAll_HDCT(Integer idHD) {
        ArrayList<HoaDonCT> list = new ArrayList<>();
        String sql = "select HOADON.Ma, NGUOIDUNG.Ma As MaNV , KHACHHANG.Sdt As SdtKH, KHUYENMAI.Ma AS maKM, HOADON.NgayTao, SANPHAM.Ten As TenSP, HOADONCHITIET.SoLuong ,HOADONCHITIET.DonGia, HOADON.TrangThai\n"
                + "from HOADON join NGUOIDUNG On HOADON.IdND=NGUOIDUNG.Id join KHACHHANG On HOADON.IdKH= KHACHHANG.Id \n"
                + "join HOADONCHITIET On HOADON.Id = HOADONCHITIET.IdHD join CHITIETDEP On CHITIETDEP.Id=HOADONCHITIET.IdCTD\n"
                + "join SANPHAM On SANPHAM.Id = CHITIETDEP.IdSanPham\n"
                + "join KHUYENMAI ON HOADON.IdKM = KHUYENMAI.Id\n"
                + "where HOADONCHITIET.IdHD =?";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new HoaDonCT(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getDouble(8),
                        rs.getInt(9)));
            }
        } catch (Exception e) {
            System.out.println("Lỗi GetAll_HDCT");
        }
        return list;
    }

    @Override
    public ArrayList<HD> search(String maKH) {
        ArrayList<HD> listTimKiem = new ArrayList<>();
        String sql = "select HOADON.Id, HOADON.Ma, NGUOIDUNG.Ma As MaNV , KHACHHANG.Ma As maKH,KHACHHANG.Sdt As sdtKH , NgayTao, TongTien, HOADON.TrangThai, MoTa\n"
                + "from HOADON join NGUOIDUNG On HOADON.IdND=NGUOIDUNG.Id join KHACHHANG On HOADON.IdKH= KHACHHANG.Id\n";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listTimKiem.add(new HD(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getFloat(7),
                        rs.getInt(8),
                        rs.getString(9)));
            }
        } catch (Exception e) {
            System.out.println("Lỗi Tim Kiem hoa don!");
        }
        return listTimKiem;
    }

    @Override
    public ArrayList<HD> searchDate(String ngayTao, String ngayThanhToan) {
        ArrayList<HD> listSearch = new ArrayList<>();
        String sql = "select HOADON.Id, HOADON.Ma, NGUOIDUNG.Ma As MaNV , KHACHHANG.Ma As maKH,KHACHHANG.Sdt As sdtKH , NgayTao, TongTien, HOADON.TrangThai, MoTa\n"
                + "from HOADON join NGUOIDUNG On HOADON.IdND=NGUOIDUNG.Id join KHACHHANG On HOADON.IdKH= KHACHHANG.Id\n"
                + "WHERE CONVERT(date , NgayTao )  >= ? and CONVERT(date , NgayThanhToan )  <= ? ";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ngayTao);
            ps.setString(2, ngayThanhToan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSearch.add(new HD(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getFloat(7),
                        rs.getInt(8),
                        rs.getString(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi Search Date hoa don!");
        }
        return listSearch;

    }

}
