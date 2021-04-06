/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblBillDetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import thuylinhHelper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class tblBillDetailDAO implements Serializable {

    public boolean insertBillDetail(BillDetailDTO dto) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Insert Into tblBillDetails "
                        + "Values(?,?,?,?,?)";
                stm = cn.prepareStatement(sql);
                stm.setString(1, dto.getBillDetailID());
                stm.setString(2, dto.getBillID());
                stm.setString(3, dto.getFoodID());
                stm.setInt(4, dto.getQuantity());
                stm.setFloat(5, dto.getPrice());
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
