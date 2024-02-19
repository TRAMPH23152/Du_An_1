package Interface;

import DomainModels.HD;
import DomainModels.HoaDonCT;


import java.util.ArrayList;

public interface HDReponsitoryImpl {

    public ArrayList<HD> getAll();
    public ArrayList<HoaDonCT> getAll_HDCT(Integer idHD);
    public ArrayList<HD> search(String maKH );
     public ArrayList<HD> searchDate(String ngayBatDau, String ngayKetThuc);
}
