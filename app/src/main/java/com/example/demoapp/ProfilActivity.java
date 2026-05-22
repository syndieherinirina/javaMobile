package com.example.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {
    private TextView tvEmailProfil;
    private TextView tvPremiereConnexion;
    private TextView tvDerniereConnexion;
    private TextView tvDerniereVisiteProfil;
    private TextView tvNombreVisitesProfil;
    private EditText editNouvelEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ThemeUtils.appliquerTheme(this);

        tvEmailProfil = findViewById(R.id.tvEmailProfil);
        tvPremiereConnexion = findViewById(R.id.tvPremiereConnexion);
        tvDerniereConnexion = findViewById(R.id.tvDerniereConnexion);
        tvDerniereVisiteProfil = findViewById(R.id.tvDerniereVisiteProfil);
        tvNombreVisitesProfil = findViewById(R.id.tvNombreVisitesProfil);
        editNouvelEmail = findViewById(R.id.editNouvelEmail);

        enregistrerVisiteProfil();
        afficherInformations();

        Button btnChangerEmail = findViewById(R.id.btnChangerEmail);
        btnChangerEmail.setOnClickListener(v -> changerEmail());

        Button btnRetour = findViewById(R.id.btnRetourProfil);
        btnRetour.setOnClickListener(v -> {
            startActivity(new Intent(ProfilActivity.this, AccueilActivity.class));
            finish();
        });
    }

    private void enregistrerVisiteProfil() {
        SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
        int visites = prefs.getInt(AppPrefs.CLE_NOMBRE_VISITES_PROFIL, 0) + 1;
        String maintenant = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE).format(new Date());

        prefs.edit()
            .putString(AppPrefs.CLE_DERNIERE_VISITE_PROFIL, maintenant)
            .putInt(AppPrefs.CLE_NOMBRE_VISITES_PROFIL, visites)
            .apply();
    }

    private void afficherInformations() {
        SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
        String email = prefs.getString(AppPrefs.CLE_EMAIL_UTILISATEUR, AppPrefs.EMAIL_DEFAUT);

        tvEmailProfil.setText("Email : " + email);
        tvPremiereConnexion.setText("Date de premiere connexion : "
            + prefs.getString(AppPrefs.CLE_PREMIERE_CONNEXION, "Non disponible"));
        tvDerniereConnexion.setText("Derniere connexion : "
            + prefs.getString(AppPrefs.CLE_DERNIERE_CONNEXION, "Non disponible"));
        tvDerniereVisiteProfil.setText("Derniere visite du profil : "
            + prefs.getString(AppPrefs.CLE_DERNIERE_VISITE_PROFIL, "Non disponible"));
        tvNombreVisitesProfil.setText("Nombre de visites du profil : "
            + prefs.getInt(AppPrefs.CLE_NOMBRE_VISITES_PROFIL, 0));
        editNouvelEmail.setText(email);
        editNouvelEmail.setSelection(email.length());
    }

    private void changerEmail() {
        String nouvelEmail = editNouvelEmail.getText().toString().trim();
        if (nouvelEmail.isEmpty()) {
            editNouvelEmail.setError("Email requis");
            return;
        }
        if (!nouvelEmail.contains("@") || !nouvelEmail.contains(".")) {
            editNouvelEmail.setError("L'email doit contenir @ et .");
            return;
        }

        SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit()
            .putString(AppPrefs.CLE_EMAIL_UTILISATEUR, nouvelEmail);

        if (prefs.getBoolean(AppPrefs.CLE_COCHE, false)) {
            editor.putString(AppPrefs.CLE_EMAIL_SOUVENIR, nouvelEmail);
        }

        editor.apply();
        tvEmailProfil.setText("Email : " + nouvelEmail);
        Toast.makeText(this, "Email mis a jour", Toast.LENGTH_SHORT).show();
    }
}
