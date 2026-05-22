package com.example.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText    editEmail, editPassword;
    private Button      btnConnexion, btnEffacer;
    private TextView    tvApercu;
    private ImageButton btnToggleMdp;
    private CheckBox    checkSouvenir;

    private int     tentatives = 0;
    private boolean mdpVisible = false;

    private static final int    MAX_TENTATIVES = 3;
    private static final String MDP_CORRECT    = "motdepasse123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThemeUtils.appliquerTheme(this);

        editEmail     = findViewById(R.id.editEmail);
        editPassword  = findViewById(R.id.editPassword);
        btnConnexion  = findViewById(R.id.btnConnexion);
        btnEffacer    = findViewById(R.id.btnEffacer);
        tvApercu      = findViewById(R.id.tvApercu);
        btnToggleMdp  = findViewById(R.id.btnToggleMdp);
        checkSouvenir = findViewById(R.id.checkSouvenir);

        
        btnToggleMdp.setImageResource(R.drawable.ic_eye_closed);

        chargerPreferences();
        configurerTextWatchers();
        configurerBoutons();
        mettreAJourCouleur();
    }

    private void chargerPreferences() {
        SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
        if (prefs.getBoolean(AppPrefs.CLE_COCHE, false)) {
            String email = prefs.getString(
                AppPrefs.CLE_EMAIL_SOUVENIR,
                prefs.getString(AppPrefs.CLE_EMAIL_UTILISATEUR, AppPrefs.EMAIL_DEFAUT)
            );
            editEmail.setText(email);
            checkSouvenir.setChecked(true);
            editEmail.setSelection(email.length());
        }
    }

    private void configurerTextWatchers() {
        editEmail.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                tvApercu.setText("Apercu email : " + s.toString());
                mettreAJourCouleur();
            }
            public void afterTextChanged(Editable s) {}
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                mettreAJourCouleur();
            }
            public void afterTextChanged(Editable s) {}
        });
    }

    private void configurerBoutons() {
        btnConnexion.setOnClickListener(v -> tenterConnexion());

        btnEffacer.setOnClickListener(v -> {
            editEmail.setText("");
            editPassword.setText("");
            tvApercu.setText("Apercu email : ");
            editEmail.requestFocus();
            tentatives = 0;
            btnConnexion.setEnabled(true);
            btnConnexion.setText("Connexion");
            mettreAJourCouleur();
        });

        btnToggleMdp.setOnClickListener(v -> {
            mdpVisible = !mdpVisible;
            int type = mdpVisible
                ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            editPassword.setInputType(type);
            int iconRes = mdpVisible ? R.drawable.ic_eye_open : R.drawable.ic_eye_closed;
            btnToggleMdp.setImageResource(iconRes);
            editPassword.setSelection(editPassword.getText().length());
        });
    }

    private void tenterConnexion() {
        try {
            editEmail.setError(null);
            editPassword.setError(null);

            String email = editEmail.getText().toString().trim();
            String mdp   = editPassword.getText().toString().trim();
            boolean valide = true;

            if (email.isEmpty()) {
                editEmail.setError("Email requis");
                valide = false;
            } else if (!emailValide(email)) {
                editEmail.setError("Email invalide : doit contenir un @ et un point apres le @");
                valide = false;
            }

            if (mdp.isEmpty()) {
                editPassword.setError("Mot de passe requis");
                valide = false;
            }

            if (!valide) return;

            btnConnexion.setText("Connexion en cours...");

            SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
            String emailCorrect = prefs.getString(AppPrefs.CLE_EMAIL_UTILISATEUR, AppPrefs.EMAIL_DEFAUT);

            if (email.equals(emailCorrect) && mdp.equals(MDP_CORRECT)) {
                String heureConnexion = formaterDate("HH:mm:ss");
                String dateConnexion = formaterDate("dd/MM/yyyy");
                String dateHeureConnexion = dateConnexion + " " + heureConnexion;

                sauvegarderPreferences(email, dateHeureConnexion);
                Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("heure_connexion", heureConnexion);
                intent.putExtra("date_connexion", dateConnexion);
                startActivity(intent);
                finish();
            } else {
                tentatives++;
                btnConnexion.setText("Connexion");
                if (tentatives >= MAX_TENTATIVES) {
                    btnConnexion.setEnabled(false);
                    btnConnexion.setBackgroundResource(R.drawable.button_light);
                    btnConnexion.setTextColor(Color.WHITE);
                    Toast.makeText(MainActivity.this, "Trop de tentatives. Bouton desactive.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,
                        "Identifiants incorrects. " + (MAX_TENTATIVES - tentatives) + " tentative(s) restante(s).",
                        Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean emailValide(String email) {
        int pos = email.indexOf('@');
        return pos >= 0 && email.substring(pos + 1).contains(".");
    }

    private String formaterDate(String format) {
        return new SimpleDateFormat(format, Locale.FRANCE).format(new Date());
    }

    private void mettreAJourCouleur() {
        if (!btnConnexion.isEnabled()) return;
        boolean rempli = !editEmail.getText().toString().trim().isEmpty()
                      && !editPassword.getText().toString().trim().isEmpty();
        btnConnexion.setBackgroundResource(rempli ? R.drawable.button_primary : R.drawable.button_light);
        btnConnexion.setTextColor(Color.WHITE);
    }

    private void sauvegarderPreferences(String email, String dateHeureConnexion) {
        SharedPreferences prefs = getSharedPreferences(AppPrefs.PREFS_NOM, MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(AppPrefs.CLE_EMAIL_UTILISATEUR, email);
        ed.putString(AppPrefs.CLE_DERNIERE_CONNEXION, dateHeureConnexion);
        if (!prefs.contains(AppPrefs.CLE_PREMIERE_CONNEXION)) {
            ed.putString(AppPrefs.CLE_PREMIERE_CONNEXION, dateHeureConnexion);
        }
        if (checkSouvenir.isChecked()) {
            ed.putString(AppPrefs.CLE_EMAIL_SOUVENIR, email).putBoolean(AppPrefs.CLE_COCHE, true);
        } else {
            ed.remove(AppPrefs.CLE_EMAIL_SOUVENIR).putBoolean(AppPrefs.CLE_COCHE, false);
        }
        ed.apply();
    }
}
