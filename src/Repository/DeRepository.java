/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.De;
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
public class DeRepository {

    DBConnection dBConnection;

    public ArrayList<De> getAllDe() {
        ArrayList<De> des = new ArrayList<>();
        String sql = "select * from DE";
        try (Connection con = dBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                De cl = new De();
                cl.setIdDe(rs.getInt("Id"));
                cl.setMa(rs.getString("Ma"));
                cl.setTen(rs.getString("Ten"));
                cl.setTrangThai(rs.getInt("TrangThai"));
                des.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return des;
    }

    public De getDeByID(String id) {

        String sql = "SELECT * FROM DE WHERE Id=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, id);
        try {
            while (rs.next()) {
                return new De(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex) {

            Logger.getLogger(DeRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public De getDeTen(String ten) {

        String sql = "SELECT * FROM DE WHERE Ten=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, ten);
        try {
            while (rs.next()) {
                return new De(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        } catch (SQLException ex) {

            Logger.getLogger(DeRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean insertDe(De de) {
        try (Connection connection = dBConnection.getConnection()) {
            String sql = "INSERT INTO DE (Ma , Ten) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, generateUniqueCode());
                preparedStatement.setString(2, de.getTen());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private String generateUniqueCode() {
        String randomCode = String.valueOf((int) (Math.random() * 10000));
        return "DE" + String.format("%04d", Integer.parseInt(randomCode));
    }
    
    public boolean updateDe(De de) {
        try {
            Connection connection = dBConnection.getConnection();

            String sql = "update DE set Ten = ? where Ma = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, de.getTen());
            ps.setObject(2, de.getMa());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DeRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
