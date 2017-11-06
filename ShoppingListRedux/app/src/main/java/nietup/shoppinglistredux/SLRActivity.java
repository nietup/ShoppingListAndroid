package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * This is the base class for activities created in the project.
 * It handles theme setting etc.
 */
public class SLRActivity extends Activity {

    protected SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("my_pref", 0);
        updateActivityTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slr);
    }

    protected void updateActivityTheme() {
        if (settings.getBoolean("dark_mode", false)) {
            setTheme(R.style.DarkAppTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
    }
}
