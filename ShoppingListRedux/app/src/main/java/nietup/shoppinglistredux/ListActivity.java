package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        ArrayList<String> items = dbHelper.getAllItems();

        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        itemsList.setAdapter(arrayAdapter);
//        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
//                // TODO Auto-generated method stub
//                int id_To_Search = arg2 + 1;
//
//                Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", id_To_Search);
//
//                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
//
//                intent.putExtras(dataBundle);
//                startActivity(intent);
//            }
//        });
    }

    public void insertTestData(View v) {
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
}
