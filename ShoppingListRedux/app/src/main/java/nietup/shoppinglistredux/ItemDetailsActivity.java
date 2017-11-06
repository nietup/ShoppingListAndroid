package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailsActivity extends Activity {

    private DBHelper dbHelper;
    private TextView TW_itemName;
    private TextView TW_itemQuantity;
    private TextView TW_itemBougth;
    private EditText ET_itemName;
    private EditText ET_itemQuantity;
    private CheckBox CB_itemBought;

    private int id;
    private String itemName;
    private int itemQuantity;
    private boolean itemBought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TW_itemName = (TextView) findViewById(R.id.tw_item_name);
        TW_itemQuantity = (TextView) findViewById(R.id.tw_item_quantity);
        TW_itemBougth = (TextView) findViewById(R.id.tw_item_bought);
        ET_itemName = (EditText) findViewById(R.id.id_et_item_name);
        ET_itemQuantity = (EditText) findViewById(R.id.id_et_item_quantity);
        CB_itemBought = (CheckBox) findViewById(R.id.id_cb_bought);

        dbHelper = new DBHelper(this);
        Bundle dataBundle = getIntent().getExtras();

        if (dataBundle != null) {
            id = dataBundle.getInt("id");

            Cursor cursor = dbHelper.getData(id);
            cursor.moveToFirst();

            itemName = cursor.getString(cursor.getColumnIndex(dbHelper.TOBUY_COLUMN_NAME));
            itemQuantity = cursor.getInt(cursor.getColumnIndex(DBHelper.TOBUY_QUANTITY));
            itemBought = cursor.getInt(cursor.getColumnIndex(dbHelper.TOBUY_BOUGHT)) > 0;

            ET_itemName.setText(itemName);
            ET_itemQuantity.setText(String.valueOf(itemQuantity));
            CB_itemBought.setChecked(itemBought);
        }
    }

    public void deleteItem(View v) {
        dbHelper.deleteItem(id);
        startActivity(new Intent(this, ListActivity.class));
    }

    public void updateRecord(View v) {
        itemName = ET_itemName.getText().toString();
        itemQuantity = Integer.parseInt(ET_itemQuantity.getText().toString());
        itemBought = CB_itemBought.isChecked();

        String category = "Other";
        String deadline = "2137-04-20";
        int price = 0;

        dbHelper.updateItem(id, itemName, itemQuantity, itemBought, category, deadline, price);

        startActivity(new Intent(this, ListActivity.class));
    }
}
