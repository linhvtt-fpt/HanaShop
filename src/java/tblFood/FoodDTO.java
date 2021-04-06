/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblFood;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Thuy Linh
 */
public class FoodDTO implements Serializable{
    String foodID, name, description, image,cateID;
    float price;
    Date createDate;
    boolean status;
    int quantity;

    public FoodDTO() {
    }

    public FoodDTO(String foodID, String name, float price, int quantity) {
        this.foodID = foodID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

   

    public FoodDTO(String foodID, String name, String description, String image, String cateID, float price, Date createDate, boolean status, int quantity) {
        this.foodID = foodID;
        this.name = name;
        this.description = description;
        this.image = image;
        this.cateID = cateID;
        this.price = price;
        this.createDate = createDate;
        this.status = status;
        this.quantity = quantity;
    }


    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

   

   

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

   

    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
