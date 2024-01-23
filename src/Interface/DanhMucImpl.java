/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import DomainModels.DanhMuc;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public interface DanhMucImpl {
    public ArrayList<DanhMuc> getAllDanhMucRepository();
    
    public boolean insert(DanhMuc dm);
    
    public boolean update(DanhMuc dm);
    
    public DanhMuc getIDByName(String danhMuc);
    
    public void updateTrangThai(String idDanhMuc);
}
