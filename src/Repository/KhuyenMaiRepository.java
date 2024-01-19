/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.KhuyenMai;
import Interface.KhuyenMaiRepositoryImpl;
import Utilities.DBConnection;
import java.util.ArrayList;
import java.sql.*;

public class KhuyenMaiRepository implements KhuyenMaiRepositoryImpl {

    DBConnection cdao = new DBConnection();

    @Override
    public ArrayList<KhuyenMai> getAllKM() {
        ArrayList<KhuyenMai> dskm = new ArrayList<>();
        String sql = "Select Ma, Ten, PhanTramGiam, SoLuong, NgayBatDau, NgayKetThuc, TrangThai, MoTa from KHUYENMAI";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dskm.add(new KhuyenMai(rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getString(8)));
            }
        } catch (Exception e) {
            System.out.println("Lỗi GetAll KhuyenMai!");
        }
        return dskm;
    }

    @Override
    public boolean add(KhuyenMai km) {
        String sql = "INSERT INTO KHUYENMAI (Ma, Ten, PhanTramGiam, SoLuong, NgayBatDau, NgayKetThuc, TrangThai, MoTa)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, km.getMa());
            ps.setObject(2, km.getTen());
            ps.setObject(3, km.getPhanTramGiam());
            ps.setObject(4, km.getSoLuong());
            ps.setObject(5, km.getNgayBatDau());
            ps.setObject(6, km.getNgayKetThuc());
            ps.setObject(7, km.getTrangThai());
            ps.setObject(8, km.getMoTa());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi ADD KhuyenMaiiii");
        }
        return false;
    }

    @Override
    public boolean update(String ma, KhuyenMai km) {
        String sql = "Update KHUYENMAI set Ten=? , PhanTramGiam = ?, SoLuong= ?, NgayBatDau =?,"
                + " NgayKetThuc=?, TrangThai = ?, MoTa = ? where Ma=?";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, km.getTen());
            ps.setObject(2, km.getPhanTramGiam());
            ps.setObject(3, km.getSoLuong());
            ps.setObject(4, km.getNgayBatDau());
            ps.setObject(5, km.getNgayKetThuc());
            ps.setObject(6, km.getTrangThai());
            ps.setObject(7, km.getMoTa());
            ps.setObject(8, ma);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi update KHUYEN MAIiii");
        }

        return false;
    }

    @Override
    public ArrayList<KhuyenMai> timKiem(String ma, String ten, float mucGiam) {
        ArrayList<KhuyenMai> listTimKiem = new ArrayList<>();
        String sql = "SELECT Ma, Ten, PhanTramGiam, SoLuong, NgayBatDau, NgayKetThuc, TrangThai, MoTa FROM KHUYENMAI WHERE Ma =? or Ten =? or PhanTramGiam = ?";
        try {
            Connection con = cdao.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ma);
            ps.setString(2, ten);
            ps.setFloat(3, mucGiam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listTimKiem.add(new KhuyenMai(rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getInt(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getInt(7),
                        rs.getString(8)));
            }
        } catch (Exception e) {
            System.out.println("Lỗi Tim Kiem KhuyenMai!");
        }
        return listTimKiem;
    }

}
