package com.example.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AccueilActivity extends AppCompatActivity {
    private int compteurClics = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ThemeUtils.appliquerTheme(this);

        afficherEmail();
        afficherConnexion();

        Button btnCount = findViewById(R.id.btnCount);
        btnCount.setOnClickListener(v -> {
            compteurClics++;
            btnCount.setText("Compte : " + compteurClics);
        });

        Button btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(AccueilActivity.this, ProfilActivity.class));
        });

        Button btnParametres = findViewById(R.id.btnParametres);
        btnParametres.setOnClickListener(v -> {
            startActivity(new Intent(AccueilActivity.this, ParametresActivity.class));
        });

        Button btnDeconnexion = findViewById(R.id.btnDeconnexion);
        btnDeconnexion.setOnClickListener(v -> confirmerDeconnexion());
    }

    private void afficherEmail() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        if (email == null || email.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
            email = prefs.getString(AppPrefs.CLE_EMAIL_UTILISATEUR, "");
        }
        
        TextView tvEmail = findViewById(R.id.tvEmail);
        if (email != null && !email.isEmpty()) {
            tvEmail.setText("Email: " + email);
        } else {
            tvEmail.setText("Email: Non disponible");
        }
    }

    private void afficherConnexion() {
        Intent intent = getIntent();
        String heure = intent.getStringExtra("heure_connexion");
        String date = intent.getStringExtra("date_connexion");

        if (heure == null || date == null) {
            String derniereConnexion = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE)
                .getString(AppPrefs.CLE_DERNIERE_CONNEXION, "Non disponible");
            ((TextView) findViewById(R.id.tvConnexion)).setText("Derniere connexion : " + derniereConnexion);
        } else {
            ((TextView) findViewById(R.id.tvConnexion)).setText(
                "Connecte a " + heure + "\nDate : " + date
            );
        }
    }

    private void confirmerDeconnexion() {
        new AlertDialog.Builder(this)
            .setMessage("Voulez-vous vraiment vous deconnecter ?")
            .setPositiveButton("OUI", (dialog, which) -> {
                getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE)
                    .edit()
                    .remove("mot_de_passe")
                    .apply();
                Toast.makeText(this, "Mot de passe efface", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AccueilActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            })
            .setNegativeButton("NON", (dialog, which) -> dialog.dismiss())
            .show();
    }
}




                  
