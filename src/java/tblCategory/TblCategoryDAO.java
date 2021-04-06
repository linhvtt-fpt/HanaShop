/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblCategory;

import java.io.Serializable;
import java.sql.Connection;
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
public class TblCategoryDAO implements Serializable{
    private List<TblCategoryDTO> resultCate;
    public List<TblCategoryDTO> getCate() throws SQLException, NamingException{
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn= DBHelper.makeConnection();
            if(cn!=null){
                String sql = "Select CateID , Name "
                        + " From tblCategory ";
                stm = cn.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String cateID = rs.getString("CateID");
                    String nameCate = rs.getString("Name");
                    TblCategoryDTO cate = new TblCategoryDTO(cateID, nameCate);
                    if(this.resultCate==null){
                        this.resultCate= new ArrayList<>();
                    }
                    resultCate.add(cate);
                }
            }
        } 
        finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(cn!=null){
                cn.close();
            }
        }
        return resultCate;
    }
}
