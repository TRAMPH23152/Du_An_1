/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModel;

import java.math.BigDecimal;

/**
 *
 * @author UyTin
 */
public class ThongKeModel {
    
    private int donhang;
    private BigDecimal doanhThu;
    private int thang;
    private String ma;
    private String ten;
     private String mauSac;
     private String tenCl;
     private int kichCo;
     private int soLuong;
     
     
            

    public ThongKeModel() {
    }

    public ThongKeModel(int donhang, BigDecimal doanhThu, int thang, String ma, String ten, String mauSac, String tenCl, int kichCo, int soLuong) {
        this.donhang = donhang;
        this.doanhThu = doanhThu;
        this.thang = thang;
        this.ma = ma;
        this.ten = ten;
        this.mauSac = mauSac;
        this.tenCl = tenCl;
        this.kichCo = kichCo;
        this.soLuong = soLuong;
    }

    public int getDonhang() {
        return donhang;
    }

    public void setDonhang(int donhang) {
        this.donhang = donhang;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getTenCl() {
        return tenCl;
    }

    public void setTenCl(String tenCl) {
        this.tenCl = tenCl;
    }

    public int getKichCo() {
        return kichCo;
    }

    public void setKichCo(int kichCo) {
        this.kichCo = kichCo;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "ThongKeModel{" + "donhang=" + donhang + ", doanhThu=" + doanhThu + ", thang=" + thang + ", ma=" + ma + ", ten=" + ten + ", mauSac=" + mauSac + ", tenCl=" + tenCl + ", kichCo=" + kichCo + ", soLuong=" + soLuong + '}';
    }


   
   
    
    
}
