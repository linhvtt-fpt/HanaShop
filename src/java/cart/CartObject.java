/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tblFood.FoodDTO;

/**
 *
 * @author Thuy Linh
 */
public class CartObject implements Serializable{
    private Map<String, FoodDTO> items;
    public Map<String, FoodDTO> getItems() {
    return items;
}
    public void addItemsToCart(FoodDTO item){
        if(this.items==null){
            this.items = new HashMap<>();
        }
          item.setQuantity(1);
        if(this.items.containsKey(item.getFoodID())){
           int  quantity = this.items.get(item.getFoodID()).getQuantity() +1 ;
                  item.setQuantity(quantity);
        }
        this.items.put(item.getFoodID(), item);
        
    }
    public int total (){
        int total = 0;
        for (FoodDTO dto : this.items.values()) {
            total = (int) (total + (dto.getPrice()*dto.getQuantity()));
        }
        return total;
    }
    public void removeItems(String foodID){
        if(this.items==null){
            return;
        }
        if(this.items.containsKey(foodID)){
            this.items.remove(foodID);
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
    }
    public void updateCart (String foodID, int quantity){
        if(this.items==null){
            return;
        }
        if(this.items.containsKey(foodID)){
            this.items.get(foodID).setQuantity(quantity);
        }
    }
}
