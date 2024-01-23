/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import DomainModels.ChatLieu;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public interface ChatLieuImpl {
    
    public ArrayList<ChatLieu> getAllChatLieuService();
    
    public boolean insert(ChatLieu cl);
    
    public boolean update(ChatLieu cl);
    
    public ChatLieu getIDByName(String chatLieu);
    
    public void updateTrangThai(String idChatLieu);
}
