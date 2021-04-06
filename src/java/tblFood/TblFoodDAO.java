/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblFood;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thuylinhHelper.DBHelper;

/**
 *
 * @author Thuy Linh
 */
public class TblFoodDAO implements Serializable {

    private List<FoodDTO> resultSearch;
    int size = 0;

    public List<FoodDTO> searchFood(String searchValue, String CateID, float priceMin, float priceMax, boolean isAdmin, int offset) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql;
                if (isAdmin) {
                    sql = "Select FoodID, Name, Description, Price, image, createDate, CateID, Quantity, Status "
                            + "From tblFoods "
                            + "Where Name LIKE ? AND CateID LIKE ? AND Price BETWEEN ? AND ? "
                            + "ORDER BY createDate "
                            + "OFFSET ? ROWS "
                            + "FETCH NEXT 20 ROWS ONLY";
                } else {
                    sql = "Select FoodID, Name, Description, Price, image, createDate, CateID, Quantity, Status "
                            + "From tblFoods "
                            + "Where Name LIKE ? AND CateID LIKE ? AND Price BETWEEN ? AND ? AND Status = 1 AND Quantity >0 "
                            + "ORDER BY createDate "
                            + "OFFSET ? ROWS "
                            + "FETCH NEXT 20 ROWS ONLY";
                }

                stm = cn.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setString(2, "%" + CateID + "%");
                stm.setFloat(3, priceMin);
                stm.setFloat(4, priceMax);
                stm.setInt(5, offset);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String foodID = rs.getString("FoodID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String image = rs.getString("image");
                    String cateID = rs.getString("CateID");
                    float price = rs.getFloat("Price");
                    Date createDate = rs.getDate("createDate");
                    int quantity = rs.getInt("Quantity");
                    boolean status = rs.getBoolean("Status");
                    FoodDTO foodDetail = new FoodDTO(foodID, name, description, image, cateID, price, createDate, status, quantity);
                    if (this.resultSearch == null) {
                        this.resultSearch = new ArrayList<>();
                    }
                    this.resultSearch.add(foodDetail);
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
        return resultSearch;
    }

    public int getRecord(String searchValue, String CateID, float priceMin, float priceMax, boolean isAdmin) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql;
                if (isAdmin) {
                    sql = "Select Name "
                            + "From tblFoods "
                            + "Where Name LIKE ? AND CateID LIKE ? AND Price BETWEEN ? AND ? ";
                } else {
                    sql = "Select Name "
                            + "From tblFoods "
                            + "Where Name LIKE ? AND CateID LIKE ? AND Price BETWEEN ? AND ? AND Status = 1 AND Quantity >0 ";

                }

                stm = cn.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setString(2, "%" + CateID + "%");
                stm.setFloat(3, priceMin);
                stm.setFloat(4, priceMax);
                rs = stm.executeQuery();
                while (rs.next()) {
                    size++;
                }
                return size;
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
        return size;
    }

    public boolean deleteFood(String foodID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = " Update tblFoods "
                        + " SET Status = 0, Quantity = 0 "
                        + " Where FoodID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, foodID);
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

    public boolean insertNote(String foodID, String note) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = " Update tblFoods "
                        + " SET Note = ? "
                        + " Where FoodID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, note);
                stm.setString(2, foodID);
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

    public FoodDTO findByFoodID(String foodID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = "Select FoodID, Name, Description, Price, image, createDate, CateID, Quantity, Status"
                        + " From tblFoods"
                        + " Where FoodID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, foodID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String FoodID = rs.getString("FoodID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    String image = rs.getString("image");
                    String CateID = rs.getString("CateID");
                    float price = rs.getFloat("Price");
                    int quantity = rs.getInt("Quantity");
                    Date createDate = rs.getDate("createDate");
                    boolean status = rs.getBoolean("Status");
                    FoodDTO dto = new FoodDTO(FoodID, name, description, image, CateID, price, createDate, status, quantity);
                    return dto;
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

    public boolean updateFood(FoodDTO dto, String note)
            throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
             String sql;
            if (cn != null) {
                if(dto.getImage().equals("")){
                 sql = " Update tblFoods "
                        + " SET Name =? , Description = ?, Price = ?, CateID = ?, Quantity = ?, Status = ?, Note = ?"
                        + " Where FoodID = ? ";
                                 stm = cn.prepareStatement(sql);
                stm.setString(1, dto.getName());
                stm.setString(2, dto.getDescription());
                stm.setFloat(3, dto.getPrice());
                stm.setString(4, dto.getCateID());
                stm.setInt(5, dto.getQuantity());
                stm.setBoolean(6, dto.isStatus());
                stm.setString(7, note);
                stm.setString(8, dto.foodID);
                }
                else{
                 sql = " Update tblFoods "
                        + " SET Name =? , Description = ?, Price = ?, CateID = ?, Quantity = ?, Status = ?, Note = ?, image=? "
                        + " Where FoodID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setString(1, dto.getName());
                stm.setString(2, dto.getDescription());
                stm.setFloat(3, dto.getPrice());
                stm.setString(4, dto.getCateID());
                stm.setInt(5, dto.getQuantity());
                stm.setBoolean(6, dto.isStatus());
                stm.setString(7, note);
                stm.setString(8, dto.getImage());
                stm.setString(9, dto.foodID);
                }
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

    public boolean createFood(FoodDTO dto) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = " Insert Into tblFoods(FoodID, Name, Description, Price, image, createDate, CateID, Quantity, Status, Note) "
                        + "Values(?,?,?,?,?,?,?,?,?,?)";

                stm = cn.prepareStatement(sql);
                stm.setString(1, dto.foodID);
                stm.setString(2, dto.name);
                stm.setString(3, dto.description);
                stm.setFloat(4, dto.price);
                stm.setString(5, dto.image);
                Timestamp date = new Timestamp(dto.getCreateDate().getTime());
                stm.setTimestamp(6, date);
                stm.setString(7, dto.cateID);
                stm.setInt(8, dto.quantity);
                stm.setBoolean(9, dto.status);
                stm.setString(10, "");
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

    public boolean updateQuantity(int quantity, String foodID) throws SQLException, NamingException {
        Connection cn = null;
        PreparedStatement stm = null;
        try {
            cn = DBHelper.makeConnection();
            if (cn != null) {
                String sql = " Update tblFoods "
                        + " SET  Quantity = ? "
                        + " Where FoodID = ? ";
                stm = cn.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, foodID);
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
