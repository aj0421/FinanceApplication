package com.example.ass1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class User extends AppCompatActivity
{
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private EditText edt_firstName;
    private EditText edt_lastName;
    private String firstName = "";
    private String lastName = "";

    private AppCompatButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginButton = (AppCompatButton) findViewById(R.id.btnFragment);
        edt_firstName = (EditText)findViewById(R.id.edit_first);
        edt_lastName = (EditText)findViewById(R.id.edit_last);

        setOnClickListeners();
    }


    private void setOnClickListeners() {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                firstName = edt_firstName.getText().toString();
                lastName = edt_lastName.getText().toString();

                editor.putString(getString(R.string.firstName_key), firstName);
                editor.commit();

                editor.putString(getString(R.string.lastName_key), lastName);
                editor.commit();
                Intent intent = new Intent(User.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
