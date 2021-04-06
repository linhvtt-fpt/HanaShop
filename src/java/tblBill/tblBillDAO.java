/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblBill;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import thuylinhHelper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class tblBillDAO implements Serializable {

    public String getLastOrderByUser( String username) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if(cn!=null){
            String sql = "Select BillID "
                    + "From tblBills "
                    + "Where BuyDateTime=(Select MAX(BuyDateTime) "
                    + "From tblBills Where Username = ?)";
            stm=cn.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
            if (rs.next()) {
                String billID = rs.getString("BillID");
                return billID;
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
        return null;
    }

    public boolean checkOut(BillDTO bill) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if(cn!=null){
            String sql = "Insert Into tblBills "
                    + "Values(?,?,?,?)";
            stm = cn.prepareStatement(sql);
            stm.setString(1, bill.getBillID());
            stm.setTimestamp(2,  new Timestamp(bill.getBuyDateTime().getTime()));
            stm.setFloat(3, bill.getTotalPrice());
            stm.setString(4, bill.getUsername());
            int result = stm.executeUpdate();
            if (result > 0) {
                return true;
            }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return false;
    }
}
