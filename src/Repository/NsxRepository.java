/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.NhaSanXuat;
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
public class NsxRepository {
    
    DBConnection dBConnection;
    
    public ArrayList<NhaSanXuat> getAllNsx(){
        ArrayList<NhaSanXuat> nhaSanXuats = new ArrayList<>();
        String sql = "select * from NSX";
        try (Connection con = dBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                NhaSanXuat nsx = new NhaSanXuat();
                nsx.setId(rs.getInt("Id"));
                nsx.setMa(rs.getString("Ma"));
                nsx.setTen(rs.getString("Ten"));
                nsx.setTrangThai(rs.getInt("TrangThai"));
                nhaSanXuats.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhaSanXuats;
    }
    
    public NhaSanXuat getNSXByID(String id) {

        String sql = "SELECT * FROM NSX WHERE Id=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, id);
        try {
            while (rs.next()) {
                return new NhaSanXuat(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4));
            }
        } catch (SQLException ex) {
            
           Logger.getLogger(NsxRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public NhaSanXuat getNSXTen(String ten) {

        String sql = "SELECT * FROM NSX WHERE Ten=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, ten);
        try {
            while (rs.next()) {
                return new NhaSanXuat(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4));
            }
        } catch (SQLException ex) {
            
           Logger.getLogger(NsxRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean addNhaSanXuat(NhaSanXuat nhaSanXuat) {
        try (Connection connection = dBConnection.getConnection()) {
            String sql = "INSERT INTO NSX (Ma, Ten) VALUES (?, ?)";
           try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, generateUniqueCode());
                preparedStatement.setString(2, nhaSanXuat.getTen());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
             Logger.getLogger(NsxRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private String generateUniqueCode() {
        String randomCode = String.valueOf((int) (Math.random() * 10000));
        return "NSX" + String.format("%04d", Integer.parseInt(randomCode));
    }

    public boolean updateNsx(NhaSanXuat nsx) {
        try {
            Connection connection = dBConnection.getConnection();
            String sql = "update NSX set Ten = ? , Ma = ? where Id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, nsx.getTen());
            ps.setObject(2, nsx.getMa());
            ps.setInt(3, nsx.getId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NsxRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
