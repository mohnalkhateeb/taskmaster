package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView userItem = SettingsActivity.this.findViewById(R.id.textview7);

        Button submitUsername = findViewById(R.id.save);
        submitUsername.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText userInput = findViewById(R.id.username);
                String userInputText = userInput.getText().toString();
                userItem.setText(userInputText);
                SharedPreferences username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                SharedPreferences.Editor editor = username.edit();
                editor.putString("username", userInputText);
                editor.apply();
                Intent sentToMain = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsActivity.this.startActivity(sentToMain);


            }




        });
    }
}