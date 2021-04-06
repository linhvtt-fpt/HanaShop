/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblCategory;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class TblCategoryDTO implements Serializable{
    String cateID, cateName;

    public TblCategoryDTO(String cateID, String cateName) {
        this.cateID = cateID;
        this.cateName = cateName;
    }

    public TblCategoryDTO() {
    }

    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
    
}
