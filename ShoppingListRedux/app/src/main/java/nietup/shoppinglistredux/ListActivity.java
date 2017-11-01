package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ListActivity extends Activity {

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("my_pref", 0);
        updateActivityTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    private void updateActivityTheme() {
        if (settings.getBoolean("dark_mode", false)) {
            setTheme(R.style.DarkAppTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
    }
}
