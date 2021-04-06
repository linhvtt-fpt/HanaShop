/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchHistory;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Thuy Linh
 */
public class HistoryDTO implements Serializable{
    String foodName;
    int quantity;
    Date buyDateTime;

    public HistoryDTO(String foodName, int quantity, Date buyDateTime) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.buyDateTime = buyDateTime;
    }

    public HistoryDTO() {
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getBuyDateTime() {
        return buyDateTime;
    }

    public void setBuyDateTime(Date buyDateTime) {
        this.buyDateTime = buyDateTime;
    }
    
}
