/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.Size;
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
public class SizeRepository {

    DBConnection dBConnection;

    public ArrayList<Size> getLAllSize() {
        ArrayList<Size> sizes = new ArrayList<>();
        String sql = "select * from SIZE";
        try (Connection con = dBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Size sz = new Size();
                sz.setIdSize(rs.getInt("Id"));
                sz.setMa(rs.getString("Ma"));
                sz.setKichCo(rs.getBigDecimal("KichCo"));
                sz.setTrangThai(rs.getInt("TrangThai"));
                sizes.add(sz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sizes;
    }

    public Size getSizeByID(String id) {

        String sql = "SELECT * FROM SIZE WHERE Id=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, id);
        try {
            while (rs.next()) {
                return new Size(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4));
            }
        } catch (SQLException ex) {

            Logger.getLogger(SizeRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Size getSizeTen(String ten) {

        String sql = "SELECT * FROM SIZE WHERE KichCo=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, ten);
        try {
            while (rs.next()) {
                return new Size(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4));
            }
        } catch (SQLException ex) {

            Logger.getLogger(SizeRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean insertSize(Size size) {
        try (Connection connection = dBConnection.getConnection()) {
            String sql = "insert into SIZE (Ma , KichCo) values (?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, generateUniqueCode());
                preparedStatement.setBigDecimal(2, size.getKichCo());

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SizeRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private String generateUniqueCode() {
        String randomCode = String.valueOf((int) (Math.random() * 10000));
        return "SZ" + String.format("%04d", Integer.parseInt(randomCode));
    }

    public boolean updateSize(Size size) {
        try {
            Connection connection = dBConnection.getConnection();
            String sql = "update SIZE set KichCo = ? , Ma = ? where Id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, size.getKichCo());
            ps.setString(2, size.getMa());
            ps.setInt(3, size.getIdSize());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SizeRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
