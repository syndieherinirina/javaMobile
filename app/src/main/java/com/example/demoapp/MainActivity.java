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

public class MainActivity extends AppCompatActivity {

    private EditText    editEmail, editPassword;
    private Button      btnConnexion, btnEffacer;
    private TextView    tvApercu;
    private ImageButton btnToggleMdp;
    private CheckBox    checkSouvenir;

    private int     tentatives = 0;
    private boolean mdpVisible = false;

    private static final int    MAX_TENTATIVES = 3;
    private static final String EMAIL_CORRECT  = "test@exemple.com";
    private static final String MDP_CORRECT    = "motdepasse123";
    private static final String PREFS_NOM      = "AuthPrefs";
    private static final String CLE_EMAIL      = "email_sauvegarde";
    private static final String CLE_COCHE      = "souvenir_coche";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        SharedPreferences prefs = getSharedPreferences(PREFS_NOM, MODE_PRIVATE);
        if (prefs.getBoolean(CLE_COCHE, false)) {
            String email = prefs.getString(CLE_EMAIL, "");
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

            if (email.equals(EMAIL_CORRECT) && mdp.equals(MDP_CORRECT)) {
                sauvegarderPreferences(email);
                Intent intent = new Intent(MainActivity.this, AccueilActivity.class);
                startActivity(intent);
                finish();
            } else {
                tentatives++;
                btnConnexion.setText("Connexion");
                if (tentatives >= MAX_TENTATIVES) {
                    btnConnexion.setEnabled(false);
                    btnConnexion.setBackgroundColor(Color.GRAY);
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

    private void mettreAJourCouleur() {
        if (!btnConnexion.isEnabled()) return;
        boolean rempli = !editEmail.getText().toString().trim().isEmpty()
                      && !editPassword.getText().toString().trim().isEmpty();
        btnConnexion.setBackgroundColor(rempli ? Color.parseColor("#4CAF50") : Color.GRAY);
    }

    private void sauvegarderPreferences(String email) {
        SharedPreferences.Editor ed = getSharedPreferences(PREFS_NOM, MODE_PRIVATE).edit();
        if (checkSouvenir.isChecked()) {
            ed.putString(CLE_EMAIL, email).putBoolean(CLE_COCHE, true);
        } else {
            ed.remove(CLE_EMAIL).putBoolean(CLE_COCHE, false);
        }
        ed.apply();
    }
}