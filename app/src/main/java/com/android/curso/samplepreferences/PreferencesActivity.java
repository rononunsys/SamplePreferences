package com.android.curso.samplepreferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PreferencesActivity extends AppCompatActivity {
    private String name;
    private String pass;
    private boolean session;
    private int country;
    private String[] countries = {"Alemania","Bulgaria","Dinamarca","España","Francia","Italia","Grecia","Lituania","Moldova","Noruega"};
    private TextView textName;
    private TextView textPass;
    private CheckBox checkSession;
    private TextView textCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        LinearLayout layoutName = (LinearLayout) findViewById(R.id.layoutName);
        LinearLayout layoutPass = (LinearLayout) findViewById(R.id.layoutPass);
        RelativeLayout layoutSession = (RelativeLayout) findViewById(R.id.layoutSession);
        LinearLayout layoutCountry = (LinearLayout) findViewById(R.id.layoutCountry);
        textName = (TextView) findViewById(R.id.name);
        textPass = (TextView) findViewById(R.id.pass);
        checkSession = (CheckBox) findViewById(R.id.session);
        textCountry = (TextView) findViewById(R.id.country);

        checkSession.setClickable(false);

        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreferencesActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewLayout = inflater.inflate(R.layout.custom_dialog,null);
                TextView text = (TextView) viewLayout.findViewById(R.id.textView);
                final EditText editText = (EditText) viewLayout.findViewById(R.id.editText);
                text.setText("Intrduce el nombre");
                builder.setView(viewLayout)
                        .setTitle("Nombre")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                name = editText.getText().toString();
                                textName.setText(name);
                            }
                        });
                builder.create().show();
            }
        });

        layoutPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreferencesActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View viewLayout = inflater.inflate(R.layout.custom_dialog,null);
                TextView text = (TextView) viewLayout.findViewById(R.id.textView);
                final EditText editText = (EditText) viewLayout.findViewById(R.id.editText);
                text.setText("Intrduce el password");
                builder.setView(viewLayout)
                        .setTitle("Password")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pass = editText.getText().toString();
                                textPass.setText(pass);
                            }
                        });
                builder.create().show();
            }
        });

        layoutSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session = !checkSession.isChecked();
                checkSession.setChecked(session);
            }
        });

        layoutCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreferencesActivity.this);
                builder.setTitle("Selección")
                        .setSingleChoiceItems(countries,-1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                country = position;
                                textCountry.setText(countries[position]);
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        name = preferences.getString("name","Introduce tu nombre");
        pass = preferences.getString("pass","Cambiar password");
        session = preferences.getBoolean("session",false);
        country = preferences.getInt("country",-1);

        textName.setText(name);
        textPass.setText(pass);
        checkSession.setChecked(session);
        if(country>-1){
            textCountry.setText(countries[country]);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",name);
        editor.putString("pass",pass);
        editor.putBoolean("session",session);
        editor.putInt("country",country);

        editor.commit();
    }
}
