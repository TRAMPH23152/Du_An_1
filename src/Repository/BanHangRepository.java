/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.ChatLieu;
import DomainModels.ChiTietDep;
import DomainModels.DanhMuc;
import DomainModels.De;
import DomainModels.MauSac;
import DomainModels.NhaSanXuat;
import DomainModels.SanPham;
import DomainModels.Size;
import java.util.ArrayList;
import Utilities.DBConnection;
import java.sql.*;

/**
 *
 * @author asus
 */
public class BanHangRepository {

    private static final int PAGESIZE = 4;

    SanPhamRepository spr = new SanPhamRepository();
    DanhMucRepository dmr = new DanhMucRepository();
    SizeRepository sizer = new SizeRepository();
    MauSacRepository msr = new MauSacRepository();
    ChatLieuRepository clr = new ChatLieuRepository();
    DeRepository der = new DeRepository();
    NsxRepository nsxr = new NsxRepository();

    public ArrayList<ChiTietDep> getAllChiTietDepForPage(int page) {
        ArrayList<ChiTietDep> listChiTietDep = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            int offset = (page - 1) * PAGESIZE;

            String sql = "SELECT * FROM CHITIETDEP WHERE TrangThai = 1 ORDER BY SoLuong OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, offset);
            st.setInt(2, PAGESIZE);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SanPham sp = spr.getSanPhamByID(rs.getString(3));
                DanhMuc dm = dmr.getDanhMucByID(rs.getString(2));
                Size s = sizer.getSizeByID(rs.getString(4));
                MauSac ms = msr.getMauSacID(rs.getString(5));
                ChatLieu cl = clr.getChatLieuByID(rs.getString(6));
                NhaSanXuat nsx = nsxr.getNSXByID(rs.getString(7));
                De d = der.getDeByID(rs.getString(8));
                listChiTietDep.add(new ChiTietDep(rs.getInt(1), sp, dm, s, ms, cl, nsx, d, rs.getInt(9), rs.getBigDecimal(11), rs.getInt(14)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listChiTietDep;

    }

    public int tongSoItem() {
        int tongSoTrang = 0;
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM CHITIETDEP ;";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                tongSoTrang = rs.getInt(1);
            }
            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongSoTrang;
    }

    public ArrayList<ChiTietDep> search(String tenSP) {
        ArrayList<ChiTietDep> listctd = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM CHITIETDEP WHERE TrangThai = 1";

            PreparedStatement st = con.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                SanPham sp = spr.getSanPhamByID(rs.getString(3));
                DanhMuc dm = dmr.getDanhMucByID(rs.getString(2));
                Size s = sizer.getSizeByID(rs.getString(4));
                MauSac ms = msr.getMauSacID(rs.getString(5));
                ChatLieu cl = clr.getChatLieuByID(rs.getString(6));
                NhaSanXuat nsx = nsxr.getNSXByID(rs.getString(7));
                De d = der.getDeByID(rs.getString(8));
                listctd.add(new ChiTietDep(rs.getInt(1), sp, dm, s, ms, cl, nsx, d, rs.getInt(9), rs.getBigDecimal(11), rs.getInt(14)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listctd;
    }
}
