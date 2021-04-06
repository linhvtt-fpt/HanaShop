/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblBillDetail;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class BillDetailDTO implements Serializable{
    String billID, billDetailID, foodID;
    int quantity;
    float price;

    public BillDetailDTO() {
    }

    public BillDetailDTO(String billID, String billDetailID, String foodID, int quantity, float price) {
        this.billID = billID;
        this.billDetailID = billDetailID;
        this.foodID = foodID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(String billDetailID) {
        this.billDetailID = billDetailID;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
