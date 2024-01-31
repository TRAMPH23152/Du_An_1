/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DomainModels.NguoiDung;
import Repository.NguoiDungRepository;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class NguoiDungService {
    
    NguoiDungRepository nguoiDungRepository = new NguoiDungRepository();
    
    public ArrayList<NguoiDung> checkLogin(NguoiDung user) {
        return nguoiDungRepository.checkLogin(user);
    }

    public String getIDLoaiNguoiDung() {
        return nguoiDungRepository.getIDLoaiNguoiDung();
    }

    public String getIDByEmail(String email) {
        return nguoiDungRepository.getIDByEmail(email);
    }
}
