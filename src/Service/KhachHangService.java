/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DomainModels.KhachHang;

import Interface.KhachHangServiceIplm;
import Repository.KhachHangRepository;
import ViewModel.KhachHangModel;
import java.util.ArrayList;

/**
 *
 * @author UyTin
 */
public class KhachHangService implements KhachHangServiceIplm{
    
    KhachHangRepository khachHangRepository =  new KhachHangRepository();

    @Override
    public ArrayList<KhachHang> getList() {
        return khachHangRepository.getList();
    }

    @Override
    public String them(KhachHang kh) {
         if(khachHangRepository.addNew(kh)){
            return"Thêm thành công";
        }else{
            return "Thêm thất bại";
        }
        }

    @Override
    public String update(String Ma, KhachHang kh) {
           if(khachHangRepository.upDateKH(Ma, kh)){
            return"Sửa thành công";
        }else{
            return "Sửa thất bại";
        }
        }

    @Override
    public ArrayList<KhachHang> TimKiem(String Ten, String sdt) {
    return  khachHangRepository.TimKiem(Ten, sdt);
    }
    public static void main(String[] args) {
        KhachHangService khachHangService=new KhachHangService();
        System.out.println(khachHangService.TimKiem("Nguyễn Thị A", "0123456789").toString());
    }
    }

  

    

