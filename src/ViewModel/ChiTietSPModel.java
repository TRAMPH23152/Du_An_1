/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewModel;

import DomainModels.ChatLieu;
import DomainModels.DanhMuc;
import DomainModels.De;
import DomainModels.MauSac;
import DomainModels.NhaSanXuat;
import DomainModels.SanPham;
import DomainModels.Size;
import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class ChiTietSPModel {

    private Integer id;

    private SanPham tenSanPham;

    private DanhMuc tenDanhMuc;

    private Size tenSize;

    private MauSac tenMauSac;

    private ChatLieu tenChatLieu;

    private NhaSanXuat tenNhaSX;

    private De tenDe;

    private Integer soLuong;

    private BigDecimal giaNhap;

    private BigDecimal giaBan;

    private String moTa;

    private String imageUrl;

    private Integer trangThai;

    public ChiTietSPModel() {
    }

    public ChiTietSPModel(Integer id, SanPham tenSanPham, DanhMuc tenDanhMuc, Size tenSize, MauSac tenMauSac, ChatLieu tenChatLieu, NhaSanXuat tenNhaSX, De tenDe, Integer soLuong, BigDecimal giaBan, String moTa, Integer trangThai) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.tenDanhMuc = tenDanhMuc;
        this.tenSize = tenSize;
        this.tenMauSac = tenMauSac;
        this.tenChatLieu = tenChatLieu;
        this.tenNhaSX = tenNhaSX;
        this.tenDe = tenDe;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public ChiTietSPModel(Integer id, SanPham tenSanPham, DanhMuc tenDanhMuc, Size tenSize, MauSac tenMauSac, ChatLieu tenChatLieu, NhaSanXuat tenNhaSX, De tenDe, Integer soLuong, BigDecimal giaNhap, BigDecimal giaBan, String moTa, String imageUrl, Integer trangThai) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.tenDanhMuc = tenDanhMuc;
        this.tenSize = tenSize;
        this.tenMauSac = tenMauSac;
        this.tenChatLieu = tenChatLieu;
        this.tenNhaSX = tenNhaSX;
        this.tenDe = tenDe;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.moTa = moTa;
        this.imageUrl = imageUrl;
        this.trangThai = trangThai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DanhMuc getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(DanhMuc tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public SanPham getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(SanPham tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Size getTenSize() {
        return tenSize;
    }

    public void setTenSize(Size tenSize) {
        this.tenSize = tenSize;
    }

    public MauSac getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(MauSac tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public ChatLieu getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(ChatLieu tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public NhaSanXuat getTenNhaSX() {
        return tenNhaSX;
    }

    public void setTenNhaSX(NhaSanXuat tenNhaSX) {
        this.tenNhaSX = tenNhaSX;
    }

    public De getTenDe() {
        return tenDe;
    }

    public void setTenDe(De tenDe) {
        this.tenDe = tenDe;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        if (giaBan != null && giaBan.compareTo(BigDecimal.ZERO) >= 0) {
            this.giaBan = giaBan;
        } else {
            System.out.println("Gia ban khong duoc la so am.");
            throw new IllegalArgumentException("Gia ban khong duoc la so am.");
        }
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
