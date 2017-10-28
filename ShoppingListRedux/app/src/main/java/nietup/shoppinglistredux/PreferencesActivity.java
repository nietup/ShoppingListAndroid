package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class PreferencesActivity extends Activity {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        settings = getPreferences(0);
        editor = settings.edit();

        setDarkModeSwitchPosition();
    }

    private void setDarkModeSwitchPosition() {
        Switch sw = (Switch) findViewById(R.id.dark_mode_switch);

        if (settings.getBoolean("dark_mode", false)) {
            sw.setChecked(true);
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
}
