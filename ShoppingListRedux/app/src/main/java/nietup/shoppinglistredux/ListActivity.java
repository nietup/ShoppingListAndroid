package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListActivity extends Activity {

    public static final String FIREBASE_TAG = "handling firebase";
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
//        Cursor items;
//        if (settings.getBoolean("show_all", true)) {
//            items = dbHelper.getAllItems();
//        }
//        else {
//            items = dbHelper.getUnboughtItems();
//        }
//
//
//        TobuyCursorAdapter adapter = new TobuyCursorAdapter(getApplicationContext(), items);
//        itemsList.setAdapter(adapter);
//
//        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView arg0, View arg1, int pos, long arg3) {
//                Cursor c = (Cursor) itemsList.getItemAtPosition(pos);
//                int idToSearch = c.getInt(c.getColumnIndex(DBHelper.TOBUY_COLUMN_ID));
//
//                Bundle dataBundle = new Bundle();
//                dataBundle.putInt("id", idToSearch);
//
//                Intent intent = new Intent(getApplicationContext(), ItemDetailsActivity.class);
//
//                intent.putExtras(dataBundle);
//                startActivity(intent);
//            }
//        });

        final List<String> items = new ArrayList<>();
        items.add("Loading...");
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(itemsAdapter);

        // firebase part
        FirebaseFirestore.getInstance().collection("sampleData")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            items.clear();

                            for (DocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                String row = (String) data.get("name") + "     x " + data.get("quantity") + "     $" + data.get("price");
                                items.add(row);
                            }

                            ArrayAdapter<String> itemsAdapter2
                                    = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
                            itemsList.setAdapter(itemsAdapter2);
                        } else {
                            Log.d(FIREBASE_TAG, "Error getting documents: ", task.getException());
                        }
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
