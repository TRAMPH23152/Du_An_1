/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.DanhMuc;
import Utilities.DBConnection;
import Utilities.JDBCHelper;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DanhMucRepository {

    DBConnection dBConnection;

    public ArrayList<DanhMuc> getAllDanhMuc() {
        ArrayList<DanhMuc> danhMucs = new ArrayList<>();
        String sql = "select * from DANHMUC";

        try (Connection con = dBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setIdDanhMuc(rs.getInt("Id"));
                danhMuc.setMa(rs.getString("Ma"));
                danhMuc.setTen(rs.getString("Ten"));
                danhMuc.setTrangThai(rs.getInt("TrangThai"));
                danhMucs.add(danhMuc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhMucs;

    }

    public DanhMuc getDanhMucByID(String id) {

        String sql = "SELECT * FROM DANHMUC WHERE Id=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, id);
        try {
            while (rs.next()) {
                return new DanhMuc(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex) {

            Logger.getLogger(DanhMucRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public DanhMuc getDanhMucTen(String ten) {

        String sql = "SELECT * FROM DANHMUC WHERE Ten = ?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, ten);
        try {
            while (rs.next()) {
                return new DanhMuc(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex) {

            Logger.getLogger(DanhMucRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean insertDanhMuc(DanhMuc danhMuc) {
        try (Connection connection = dBConnection.getConnection()) {
            String sql = "insert into DANHMUC (Ma , Ten,TrangThai) values (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, generateUniqueCode());
                preparedStatement.setString(2, danhMuc.getTen());
                preparedStatement.setInt(3, danhMuc.getTrangThai());
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DanhMucRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private String generateUniqueCode() {
        String randomCode = String.valueOf((int) (Math.random() * 10000));
        return "DM" + String.format("%04d", Integer.parseInt(randomCode));
    }
    
    public boolean updateDanhMuc(DanhMuc danhMuc) {
        try {
            Connection connection = dBConnection.getConnection();

            String sql = "update DANHMUC set Ten = ? , Ma = ?, TrangThai = ? where Id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, danhMuc.getTen());
            ps.setObject(2, danhMuc.getMa());
            ps.setObject(3, danhMuc.getTrangThai());
            ps.setObject(4, danhMuc.getIdDanhMuc());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DanhMucRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
