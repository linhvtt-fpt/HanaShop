/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchHistory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thuylinhHelper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class HistoryDAO implements Serializable {

    List<HistoryDTO> resultList;

    public List<HistoryDTO> searchHistoryByName(String username, String searchValue) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select tblFoods.Name, tblBillDetails.Quantity, tblBills.BuyDateTime, tblBills.Username\n"
                        + "From tblFoods, tblBillDetails, tblBills, tblLogin\n"
                        + "Where tblFoods.FoodID= tblBillDetails.FoodID AND tblBillDetails.BillID=tblBills.BillID "
                        + "AND tblBills.Username = tblLogin.Username AND tblFoods.Name LIKE ? AND tblLogin.Username=? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setString(2, username);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String foodName = rs.getString ("Name");
                    int quantity = rs.getInt("Quantity");
                    Date buyDateTime = rs.getDate("BuyDateTime");
                    HistoryDTO dto = new HistoryDTO(foodName, quantity, buyDateTime);
                    if (this.resultList == null) {
                        this.resultList = new ArrayList<>();
                    }
                    this.resultList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return resultList;
    }
    public List<HistoryDTO> searchHistoryByDate(String date,String username) throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select tblFoods.Name, tblBillDetails.Quantity, tblBills.BuyDateTime, tblBills.Username\n"
                        + "From tblFoods, tblBillDetails, tblBills, tblLogin\n"
                        + "Where tblFoods.FoodID= tblBillDetails.FoodID AND tblBillDetails.BillID=tblBills.BillID "
                        + "AND tblBills.Username = tblLogin.Username AND tblBills.BuyDateTime LIKE ? AND tblLogin.Username=? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1,"%"+date+"%");
                stm.setString(2, username);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String foodName = rs.getString ("Name");
                    int quantity = rs.getInt("Quantity");
                    Date buyDateTime = rs.getDate("BuyDateTime");
                    HistoryDTO dto = new HistoryDTO(foodName, quantity, buyDateTime);
                    if (this.resultList == null) {
                        this.resultList = new ArrayList<>();
                    }
                    this.resultList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return resultList;
    }
}
