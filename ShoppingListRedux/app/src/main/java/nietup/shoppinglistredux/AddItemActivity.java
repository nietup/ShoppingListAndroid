package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends SLRActivity {

    private EditText itemName;
    private EditText itemQuantity;
    private EditText itemPrice;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = (EditText) findViewById(R.id.item_name_input);
        itemQuantity = (EditText) findViewById(R.id.item_quantity_input);
        itemPrice = (EditText) findViewById(R.id.item_price_input);
        dbHelper = new DBHelper(getApplicationContext());
    }

    public void addItem(View v) {
        String name = itemName.getText().toString();
        int quantity = Integer.parseInt(itemQuantity.getText().toString());
        boolean bought = false;
        String category = "Other";
        String deadline = "2137-04-20";
        int price = Integer.parseInt(itemPrice.getText().toString());

        dbHelper.insertItem(name, quantity, bought, category, deadline, price);

        String msg =  name + " " + Integer.toString(quantity) + " inserted\nrecords in base: " + Integer.toString(dbHelper.numberOfRows());
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void backToList(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }
}
