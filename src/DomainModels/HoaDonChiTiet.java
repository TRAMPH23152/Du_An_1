/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModels;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class HoaDonChiTiet {
    
    private String id;
    
    private String idHoaDon;
    
    private String idChiTietDep;
    
    private Double donGia;
    
    private Integer soLuong;
    
    private Date ngayTao;
    
    private Date ngaySua;
    
    private Integer trangThai;

    public HoaDonChiTiet() {
    }

    
    public HoaDonChiTiet(String id, String idHoaDon, String idChiTietDep, Double donGia, Integer soLuong, Date ngayTao, Date ngaySua, Integer trangThai) {
        this.id = id;
        this.idHoaDon = idHoaDon;
        this.idChiTietDep = idChiTietDep;
        this.donGia = donGia;
        this.soLuong = soLuong;
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

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
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

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
