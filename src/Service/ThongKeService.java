/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Repository.ThongKeRepository;
import ViewModel.ThongKeModel;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author UyTin
 */
public class ThongKeService {

    ThongKeRepository thongKeRepository = new ThongKeRepository();
    
    public ArrayList<ThongKeModel> getListDoanhThu(){
    return thongKeRepository.getListDoanhThu();
    }
    public ArrayList<ThongKeModel> getListThongKeSP(){
    return thongKeRepository.getListThongKeSP();
    }

    public int getTongDon(Date ngayBatDau, Date NgayKetThuc) {

        return thongKeRepository.getTongDon(ngayBatDau, NgayKetThuc);
    }

    public int getTongDonThanhCong(Date ngayBatDau, Date NgayKetThuc) {

        return thongKeRepository.getTongDonThanhCong(ngayBatDau, NgayKetThuc);
    }
    
     public int getTongDonBiHuy(Date ngayBatDau, Date NgayKetThuc) {

        return thongKeRepository.getTongDonBiHuy(ngayBatDau, NgayKetThuc);
    }
     
     public BigDecimal getDoanhThuNam(){
     return thongKeRepository.getDoanhThuNam();
     }

     
      public BigDecimal getDoanhThuNgay(Date ngayBatDau, Date NgayKetThuc){
         return thongKeRepository.getDoanhThuNgay(ngayBatDau, NgayKetThuc);
     }
      public BigDecimal getDoanhThuThang(int thang){
         return thongKeRepository.getDoanhThuThang(thang);
     }
      
      public  int getDonThangTC(int thang){
       return thongKeRepository.getDonThangTC(thang);
      }
      public  int getDonHuyThang(int thang){
       return thongKeRepository.getDonHuyThang(thang);
      }
      
    
}
