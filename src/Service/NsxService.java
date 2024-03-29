/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DomainModels.NhaSanXuat;
import Interface.NsxImpl;
import Repository.NsxRepository;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class NsxService implements NsxImpl{
    
    NsxRepository nsxRepository = new NsxRepository();

    @Override
    public ArrayList<NhaSanXuat> getAllNsxService() {
        return nsxRepository.getAllNsx();
    }

    @Override
    public boolean insert(NhaSanXuat nsx) {
        return nsxRepository.addNhaSanXuat(nsx);
    }

    @Override
    public boolean update(NhaSanXuat nsx) {
        return nsxRepository.updateNsx( nsx);
    }

    @Override
    public NhaSanXuat getIDByName(String nsx) {
        return nsxRepository.getNSXTen(nsx);
    }

    @Override
    public void updateTrangThai(String idNSX) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
