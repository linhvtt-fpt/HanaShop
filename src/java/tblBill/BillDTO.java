/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tblBill;

import java.io.Serializable;
import java.util.Date;



/**
 *
 * @author Thuy Linh
 */
public class BillDTO implements Serializable{
    String billID,  username;
    float totalPrice;
    Date buyDateTime;

    public BillDTO() {
    }

    public BillDTO(String billID, String username, float totalPrice, Date buyDateTime) {
        this.billID = billID;
        this.username = username;
        this.totalPrice = totalPrice;
        this.buyDateTime = buyDateTime;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

   

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBuyDateTime() {
        return buyDateTime;
    }

    public void setBuyDateTime(Date buyDateTime) {
        this.buyDateTime = buyDateTime;
    }

 
}
