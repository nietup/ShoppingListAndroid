package nietup.sldbproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
    }

    public void insertItems(View v) {
        dbHelper.insertItem("ddd", "warzywo", "12-12-2017", 99);
        dbHelper.insertItem("dcds", "warzywo", "12-12-2017", 99);
        dbHelper.insertItem("ew", "warzywo", "12-12-2017", 99);
        dbHelper.insertItem("cai", "warzywo", "12-12-2017", 99);

        Toast.makeText(getApplicationContext(), "insert done", Toast.LENGTH_SHORT).show();
    }

    public void showItems(View v) {
        ArrayList<String> items = dbHelper.getAllItems();
        String msg = "";
        for (String s : items) {
            msg += s + " ";
        }

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
