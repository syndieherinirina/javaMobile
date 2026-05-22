package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ParametresActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        ThemeUtils.appliquerTheme(this);

        Button btnThemeClair = findViewById(R.id.btnThemeClair);
        btnThemeClair.setOnClickListener(v -> changerTheme(AppPrefs.THEME_CLAIR));

        Button btnThemeSombre = findViewById(R.id.btnThemeSombre);
        btnThemeSombre.setOnClickListener(v -> changerTheme(AppPrefs.THEME_SOMBRE));

        Button btnRetour = findViewById(R.id.btnRetourParametres);
        btnRetour.setOnClickListener(v -> {
            startActivity(new Intent(ParametresActivity.this, AccueilActivity.class));
            finish();
        });
    }

    private void changerTheme(String theme) {
        getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE)
            .edit()
            .putString(AppPrefs.CLE_THEME, theme)
            .apply();
        ThemeUtils.appliquerTheme(this);
    }
}
