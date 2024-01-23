/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import DomainModels.NhaSanXuat;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public interface NsxImpl {
    
    public ArrayList<NhaSanXuat> getAllNsxService();
    
    public boolean insert(NhaSanXuat nsx);
    
    public boolean update(NhaSanXuat nsx);
    
    public NhaSanXuat getIDByName(String nsx);
    
    public void updateTrangThai(String idNSX);
}
