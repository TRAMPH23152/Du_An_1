
package Interface;

import DomainModels.HD;
import DomainModels.HoaDonCT;

import java.util.ArrayList;


public interface HDServiceImpl {
     public ArrayList<HD> getAll();
     public ArrayList<HoaDonCT> getAll_HDCT(Integer idHD);
}
