package Service;

import DomainModels.HD;
import DomainModels.HoaDonCT;
import Repository.HDReponsitory;
import java.util.ArrayList;
import Interface.HDServiceImpl;

public class HDService implements HDServiceImpl {

    HDReponsitory hd = new HDReponsitory();

    @Override
    public ArrayList<HD> getAll() {
        return hd.getAll();
    }

    @Override
    public ArrayList<HoaDonCT> getAll_HDCT(Integer idHD) {
        return hd.getAll_HDCT(idHD);
    }

}
