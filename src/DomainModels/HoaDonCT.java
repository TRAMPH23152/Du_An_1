package DomainModels;

import java.util.Date;

public class HoaDonCT {

    private String maHD;
    
    private String maNV;
    
    private String tenKH;
    
    private String maVoucher;
    
    private String sdt;
    
    private Date ngayTao;
    
    private String tenSP;
    
    private Integer soLuong;
    
    private double donGia;
    
    private Integer trangThai;

    public HoaDonCT() {
    }

    public HoaDonCT(String maHD, String maNV, String tenKH, String maVoucher, String sdt, Date ngayTao, String tenSP, Integer soLuong, double donGia, Integer trangThai) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.tenKH = tenKH;
        this.maVoucher = maVoucher;
        this.sdt = sdt;
        this.ngayTao = ngayTao;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public HoaDonCT(String maHD, String maNV, String maVoucher, String sdt, Date ngayTao, String tenSP, Integer soLuong, double donGia, Integer trangThai) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maVoucher = maVoucher;
        this.sdt = sdt;
        this.ngayTao = ngayTao;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.trangThai = trangThai;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }



    
    
}
