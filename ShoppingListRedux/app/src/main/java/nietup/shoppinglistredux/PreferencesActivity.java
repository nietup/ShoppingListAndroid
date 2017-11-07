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
        setupShowAllSwitch();

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
        }
        else {
            sw.setChecked(false);
        }
    }

    private void setupShowAllSwitch() {
        Switch sw = (Switch) findViewById(R.id.ap_s_show_all);

        if (settings.getBoolean("show_all", true)) {
            sw.setChecked(false);
        }
        else {
            sw.setChecked(true);
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

    public void setShowAll(View v) {
        Boolean showAll = settings.getBoolean("show_all", true);

        if (showAll) {
            editor.putBoolean("show_all", false);
            editor.commit();
        }
        else {
            editor.putBoolean("show_all", true);
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
