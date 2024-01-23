/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DomainModels.MauSac;
import Interface.MauSacImpl;
import Repository.MauSacRepository;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class MauSacService implements MauSacImpl{
    
    MauSacRepository mauSacRepository = new MauSacRepository();

    @Override
    public ArrayList<MauSac> getAllMauSacService() {
        return mauSacRepository.getAllMauSac();
    }

    @Override
    public boolean insert(MauSac ms) {
        return mauSacRepository.insertMauSac(ms);
    }

    @Override
    public boolean update(MauSac ms) {
        return mauSacRepository.updateMauSac( ms);
    }

    @Override
    public MauSac getIDByName(String mauSac) {
        return mauSacRepository.getMauSacTen(mauSac);
    }

    @Override
    public void updateTrangThai(String idMauSac) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
