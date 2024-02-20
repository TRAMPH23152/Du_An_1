/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.HoaDon;

import Utilities.DBConnection;
import ViewModel.ThongKeModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author UyTin
 */
public class ThongKeRepository {

    public int getTongDon(Date ngayBatDau, Date ngayKetThuc) {

        String sql = "select count(id) as tongDon from HOADON where  NgayTao between ? and ? ";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setObject(1, ngayBatDau);
            ps.setObject(2, ngayKetThuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDonhang(rs.getInt("tongDon"));
                return thongKeModel.getDonhang();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;

    }

    public int getTongDonThanhCong(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "select count(id) as ThanhCong from HOADON where  NgayTao between ? and ? and TrangThai= 1 ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setObject(1, ngayBatDau);
            ps.setObject(2, ngayKetThuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDonhang(rs.getInt("ThanhCong"));
                return thongKeModel.getDonhang();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int getTongDonBiHuy(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "select count(id) as BiHuy from HOADON where  NgayTao between ? and ? and TrangThai=0";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setObject(1, ngayBatDau);
            ps.setObject(2, ngayKetThuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDonhang(rs.getInt("BiHuy"));
                return thongKeModel.getDonhang();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public BigDecimal getDoanhThuNam() {
        String sql = "SELECT sum(thanhtien) AS DOANHTHUNAM FROM HOADON WHERE TrangThai = 1 ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDoanhThu(rs.getBigDecimal("DOANHTHUNAM"));

                return thongKeModel.getDoanhThu();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new BigDecimal(0);
    }

    public BigDecimal getDoanhThuThang(int thang) {

        String sql = "select sum(thanhtien) as doanhThuThang from HOADON where month(NgayTao)=? ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, thang);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDoanhThu(rs.getBigDecimal("doanhThuThang"));

                return thongKeModel.getDoanhThu();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new BigDecimal(0);
    }
//       

    public BigDecimal getDoanhThuNgay(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "select sum(ThanhTien) as doanhThuNgay from HOADON where TrangThai=1 and NgayTao between ? and ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, ngayBatDau);
            ps.setObject(2, ngayKetThuc);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDoanhThu(rs.getBigDecimal("doanhThuNgay"));

                return thongKeModel.getDoanhThu();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new BigDecimal(0);
    }
    public int getDonThangTC(int thang){
    String sql = "select count(id) as donThang  from HOADON where month(NgayTao)=? and TrangThai = 1";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, thang);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDonhang(rs.getInt("donThang"));

                return thongKeModel.getDonhang();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }
      public int getDonHuyThang(int thang){
    String sql = "select count(id) as BiHuyThang  from HOADON where month(NgayTao)=? and TrangThai = 0";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, thang);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel thongKeModel = new ThongKeModel();
                thongKeModel.setDonhang(rs.getInt("BiHuyThang"));

                return thongKeModel.getDonhang();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }
    
//          public ArrayList<HoaDon>findbyDate(Date ngayTao){
//          ArrayList<HoaDon> list = new ArrayList<>();
//          String sql ="select sum(TongTien) as doanhThuDate from HOADON where  CONVERT(date,NgayTao) = ? and TrangThai=1";
//          try {
//            Connection con = DBConnection.getConnection();
//            PreparedStatement ps = con.prepareStatement(sql);
//            ResultSet rs=ps.executeQuery();
//             while (rs.next()) {
//                HoaDon hd = new HoaDon();
//
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return list;
//          }
    public ArrayList<ThongKeModel> getListDoanhThu() {
        ArrayList<ThongKeModel> list = new ArrayList<>();

        String sql = "SELECT month(hd.NgayTao) Thang , count(id) as tongDon ,sum(hd.ThanhTien) Tong\n"
                + "FROM HOADON hd \n"
                + "WHERE year(hd.NgayTao)=2024\n"
                + "GROUP BY month(hd.NgayTao)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel tk = new ThongKeModel();

                tk.setThang(rs.getInt("Thang"));
                tk.setDonhang(rs.getInt("tongDon"));
                tk.setDoanhThu(rs.getBigDecimal("Tong"));

                list.add(tk);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<ThongKeModel> getListThongKeSP() {
        ArrayList<ThongKeModel> list = new ArrayList<>();

        String sql = "select sp.Ma as Ma, sp.Ten as Ten, ms.MauSac as MauSac, cl.Ten as TenCl, s.KichCo as kichco,"
                + " ctd.SoLuong as soLuong\n"
                + "	from CHITIETDEP as ctd inner join SANPHAM as sp on ctd.IdSanPham=sp.id\n"
                + "	inner join MAUSAC as ms on ctd.IdMauSac=ms.Id\n"
                + "	inner join CHATLIEU as cl on ctd.IdChatLieu=cl.id\n"
                + "	inner join SIZE as s on ctd.IdSize=s.id order by ctd.SoLuong asc";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeModel tk = new ThongKeModel();

                tk.setMa(rs.getString("Ma"));
                tk.setTen(rs.getString("Ten"));
                tk.setMauSac(rs.getString("MauSac"));
                tk.setTenCl(rs.getString("TenCl"));
                tk.setKichCo(rs.getInt("kichco"));
                tk.setSoLuong(rs.getInt("soLuong"));

                list.add(tk);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        ThongKeRepository thongKeRepository = new ThongKeRepository();
        System.out.println(thongKeRepository.getDoanhThuNam());
//        System.out.println(thongKeRepository.getDoanhThuNgay(Date.valueOf("2024-01-01"), Date.valueOf("2024-01-30")));
////            System.out.println(thongKeRepository.getDoanhThuNgay(Date.valueOf("2024-01-01")));
//        System.out.println(thongKeRepository.getDoanhThuThang(1));
//        System.out.println(thongKeRepository.getListThongKeSP().toString());
//        System.out.println(thongKeRepository.getDonThangTC(1));
    }
}
