/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author asus
 */
public class GioHang {
    private String id;
     private String idHoaDon;
    private String idChiTietDep;
    private String SanPham;
    private String nsx;
    private String size;
    private String danhMuc;
    private String chatLieu;
    private String de;
    private String mauSac;
    private String tenKhuyenMai;
    private Double giaNhap;
    private Double giaBan;
    private int soLuong;
    private Double donGia;
    private Date ngayTao;
    private Date ngaySua;
    private int trangThai;

    public GioHang() {
    }

    public GioHang(String id, String idHoaDon, String idChiTietDep, String SanPham, String nsx, String size, String danhMuc, String chatLieu, String de, String mauSac, String tenKhuyenMai, Double giaNhap, Double giaBan, int soLuong, Double donGia, Date ngayTao, Date ngaySua, int trangThai) {
        this.id = id;
        this.idHoaDon = idHoaDon;
        this.idChiTietDep = idChiTietDep;
        this.SanPham = SanPham;
        this.nsx = nsx;
        this.size = size;
        this.danhMuc = danhMuc;
        this.chatLieu = chatLieu;
        this.de = de;
        this.mauSac = mauSac;
        this.tenKhuyenMai = tenKhuyenMai;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getIdChiTietDep() {
        return idChiTietDep;
    }

    public void setIdChiTietDep(String idChiTietDep) {
        this.idChiTietDep = idChiTietDep;
    }

    public String getSanPham() {
        return SanPham;
    }

    public void setSanPham(String SanPham) {
        this.SanPham = SanPham;
    }

    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }





    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public Double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public Double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
