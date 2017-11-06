package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends Activity {

    private SharedPreferences settings;
    private DBHelper dbHelper;
    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("my_pref", 0);
        updateActivityTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbHelper = new DBHelper(getApplicationContext());
        itemsList = (ListView) findViewById(R.id.items_list);
        showItems();
    }

    private void updateActivityTheme() {
        if (settings.getBoolean("dark_mode", false)) {
            setTheme(R.style.DarkAppTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
    }

    private void showItems() {
        Cursor items = dbHelper.getAllItems();

        TobuyCursorAdapter adapter = new TobuyCursorAdapter(getApplicationContext(), items);
        itemsList.setAdapter(adapter);

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int pos, long arg3) {
                Cursor c = (Cursor) itemsList.getItemAtPosition(pos);
                int idToSearch = c.getInt(c.getColumnIndex(DBHelper.TOBUY_COLUMN_ID));

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", idToSearch);

                Intent intent = new Intent(getApplicationContext(), ItemDetailsActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    public void insertTestData() {
        dbHelper.insertItem("ddd", 12, false, "warzywo", "12-12-2017", 99);
        dbHelper.insertItem("dcds", 4, false,"warzywo", "12-12-2017", 99);
        dbHelper.insertItem("ew", 90, false,"warzywo", "12-12-2017", 99);
        dbHelper.insertItem("cai", 3, true, "warzywo", "12-12-2017", 99);

        Toast.makeText(getApplicationContext(), "insert done", Toast.LENGTH_SHORT).show();
    }

    public void dropData(View v) {
        dbHelper.deleteAllItems();
    }

    public void startAddItemActivity(View view) {
        startActivity(new Intent(this, AddItemActivity.class));
    }

    public void itemClicked(View v) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }

    public void openMainMenu(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
