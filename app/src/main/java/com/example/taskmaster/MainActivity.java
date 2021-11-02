package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,AddTaskActivity2.class);
                startActivity(intent1);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,AllTaskActivity2.class);
                startActivity(intent2);
            }
        });


        Button task1 = findViewById(R.id.task1);
        task1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent sentToDetails = new Intent(MainActivity.this, TaskDetailActivity.class);
                sentToDetails.putExtra("task", "Task One");
                MainActivity.this.startActivity(sentToDetails);
            }
        });


        Button task2 = findViewById(R.id.task2);
        task2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent sentToDetails = new Intent(MainActivity.this, TaskDetailActivity.class);
                sentToDetails.putExtra("task", "Task Two");
                MainActivity.this.startActivity(sentToDetails);
            }
        });





        Button task3 = findViewById(R.id.task3);
        task3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent sentToDetails = new Intent(MainActivity.this, TaskDetailActivity.class);
                sentToDetails.putExtra("task", "Task Three");
                MainActivity.this.startActivity(sentToDetails);
            }


        });



        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent sentToSettings = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(sentToSettings);
            }
        });


    }
    @Override
    public void onStart(){
        super.onStart();

        TextView textView = findViewById(R.id.textView1);


        textView.setText("WELCOME");
        textView.setVisibility(View.VISIBLE);



    }


    @Override
    public void onResume(){
        super.onResume();

        TextView textView = findViewById(R.id.textView1);
        //TextView settingsupdate = findViewById(R.id.settingsupdated);

        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = p.getString("username", "default");

        textView.setText(username + "'s Task List");
        textView.setVisibility(View.VISIBLE);


    }
}