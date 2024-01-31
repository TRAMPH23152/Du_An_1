/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DomainModels.ChucVu;
import Utilities.JDBCHelper;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ADMIN
 */
public class ChucVuRepository {
    public ChucVu getDanhMucByID(String id) {

        String sql = "SELECT * FROM CHUCVU WHERE Id=?";
        ResultSet rs = JDBCHelper.excuteQuery(sql, id);
        try {
            while (rs.next()) {
                return new ChucVu(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {

            Logger.getLogger(ChucVuRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
