package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class PreferencesActivity extends Activity {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getSharedPreferences("my_pref", 0);
        editor = settings.edit();
        updateActivityTheme();

        setContentView(R.layout.activity_preferences);

        setupDarkModeSwitch();

        dbHelper = new DBHelper(this);
    }

    private void updateActivityTheme() {
        if (settings.getBoolean("dark_mode", false)) {
            setTheme(R.style.DarkAppTheme);
        }
        else {
            setTheme(R.style.AppTheme);
        }
    }

    private void setupDarkModeSwitch() {
        Switch sw = (Switch) findViewById(R.id.dark_mode_switch);

        if (settings.getBoolean("dark_mode", false)) {
            sw.setChecked(true);
            sw.setTextColor(Color.WHITE);
        }
        else {
            sw.setChecked(false);
        }
    }

    public void setDarkMode(View v) {
        Boolean darkMode = settings.getBoolean("dark_mode", false);

        if (darkMode) {
            editor.putBoolean("dark_mode", false);
            editor.commit();
        }
        else {
            editor.putBoolean("dark_mode", true);
            editor.commit();
        }
    }

    public void openMainMenu(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void upgradeDatabase (View v) {
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), dbHelper.DATABASE_VERSION, dbHelper.DATABASE_VERSION+1);
    }
}
