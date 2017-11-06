package nietup.shoppinglistredux;

import android.util.Log;

/**
 * Created by nietup on 11/6/2017.
 */

public class DBRecord {
    private String name;
    private int quantity;
    private boolean bought;
    private String category;
    private String deadline;
    private int price;

    public DBRecord(String _name, int _quantity, boolean _bought, String _category, String _deadline, int _price) {
        name = _name;
        quantity = _quantity;
        bought = _bought;
        category = _category;
        deadline = _deadline;
        price = _price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        Log.i("sss", "to strung om record coalled");
        return name; }
}
