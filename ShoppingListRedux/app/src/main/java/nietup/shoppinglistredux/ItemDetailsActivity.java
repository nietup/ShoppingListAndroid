package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ItemDetailsActivity extends Activity {

    private DBHelper dbHelper;
    private TextView TW_itemName;
    private int id;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TW_itemName = (TextView) findViewById(R.id.tw_item_name);
        dbHelper = new DBHelper(this);
        Bundle dataBundle = getIntent().getExtras();

        if (dataBundle != null) {
            id = dataBundle.getInt("id");

            Cursor cursor = dbHelper.getData(id);
            cursor.moveToFirst();

            itemName = cursor.getString(cursor.getColumnIndex(dbHelper.TOBUY_COLUMN_NAME));

            TW_itemName.setText(itemName);
        }
    }

    public void deleteItem(View v) {
        dbHelper.deleteItem(id);
        startActivity(new Intent(this, ListActivity.class));
    }
}
