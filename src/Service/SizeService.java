/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DomainModels.Size;
import Interface.SizeImpl;
import Repository.SizeRepository;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class SizeService implements SizeImpl{
    
    SizeRepository sizeRepository = new SizeRepository();

    
    @Override
    public ArrayList<Size> getAllSizeSevice() {
        return sizeRepository.getLAllSize();
    }

    @Override
    public boolean insert(Size sz) {
        return sizeRepository.insertSize(sz);
    }

    @Override
    public boolean update(Size sz) {
        return sizeRepository.updateSize(sz);
    }

    @Override
    public Size getIDByName(String size) {
        return sizeRepository.getSizeTen(size);
    }

    @Override
    public void updateTrangThai(String idSize) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
