/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DomainModels.DanhMuc;
import Interface.DanhMucImpl;
import Repository.DanhMucRepository;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class DanhMucService implements DanhMucImpl {

    DanhMucRepository danhMucRepository = new DanhMucRepository();

    @Override
    public ArrayList<DanhMuc> getAllDanhMucRepository() {
        return danhMucRepository.getAllDanhMuc();
    }

    @Override
    public boolean insert(DanhMuc dm) {
        return danhMucRepository.insertDanhMuc(dm);
    }

    @Override
    public boolean update(DanhMuc dm) {
        return danhMucRepository.updateDanhMuc(dm);
    }

    @Override
    public DanhMuc getIDByName(String danhMuc) {
        return danhMucRepository.getDanhMucTen(danhMuc);
    }

    @Override
    public void updateTrangThai(String idDanhMuc) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
