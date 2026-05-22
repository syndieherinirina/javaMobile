package com.example.demoapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

final class ThemeUtils {
    private ThemeUtils() {}

    static void appliquerTheme(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(AppPrefs.PREFS_NOM, Activity.MODE_PRIVATE);
        boolean sombre = AppPrefs.THEME_SOMBRE.equals(
            prefs.getString(AppPrefs.CLE_THEME, AppPrefs.THEME_CLAIR)
        );

        int fond = sombre ? Color.parseColor("#1A1A1A") : Color.parseColor("#FBFAFF");
        int texte = sombre ? Color.parseColor("#FFFFFF") : Color.parseColor("#111111");
        int secondaire = sombre ? Color.parseColor("#D6D6D6") : Color.parseColor("#4B465C");

        View racine = activity.findViewById(android.R.id.content);
        appliquerAuxVues(racine, fond, texte, secondaire, sombre);
    }

    private static void appliquerAuxVues(View vue, int fond, int texte, int secondaire, boolean sombre) {
        if (vue == null) return;

        if (vue instanceof Button && !(vue instanceof CompoundButton)) {
            return;
        }
        if (vue instanceof ImageView) {
            return;
        }

        Object tag = vue.getTag();
        if ("card".equals(tag)) {
            vue.setBackgroundResource(sombre ? R.drawable.card_background_dark : R.drawable.card_background);
        } else if ("input".equals(tag)) {
            vue.setBackgroundResource(sombre ? R.drawable.edit_text_background_dark : R.drawable.edit_text_background);
        } else if (!(vue instanceof TextView)) {
            vue.setBackgroundColor(fond);
        }

        if (vue instanceof EditText) {
            EditText editText = (EditText) vue;
            editText.setTextColor(texte);
            editText.setHintTextColor(secondaire);
        } else if (vue instanceof TextView) {
            ((TextView) vue).setTextColor(texte);
        }

        if (vue instanceof ViewGroup) {
            ViewGroup groupe = (ViewGroup) vue;
            for (int i = 0; i < groupe.getChildCount(); i++) {
                appliquerAuxVues(groupe.getChildAt(i), fond, texte, secondaire, sombre);
            }
        }
    }
}
