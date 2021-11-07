package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        String getTask = getIntent().getStringExtra("task");
        TextView taskText = findViewById(R.id.textView7);
        taskText.setText(getTask);
        String getTaskInfo = getIntent().getStringExtra("taskInfo");
        TextView taskInfoText = findViewById(R.id.textView8);
        taskInfoText.setText(getTaskInfo);
    }
}