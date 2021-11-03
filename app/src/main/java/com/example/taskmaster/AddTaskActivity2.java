package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast punchToast = Toast.makeText(getApplicationContext(),"submitted!", Toast.LENGTH_SHORT);
//                punchToast.show();
                EditText userInput = findViewById(R.id.editTextTextPersonName);
                EditText userInput2 = findViewById(R.id.editTextTextPersonName2);
                EditText userInput3 = findViewById(R.id.editTextTextPersonName3);
                String userInputText = userInput.getText().toString();
                String userInputText2 = userInput2.getText().toString();
                String userInputText3 = userInput3.getText().toString();

                Task newTask = new Task(userInputText, userInputText2, userInputText3 );
                Long addedTaskID = AppDatabase.getInstance(getApplicationContext()).TaskDao().addTask(newTask);
                // TextView userItem = AddTasks.this.findViewById(R.id.textView2);
                //userItem.setText("Submitted");
                Intent sentToAddTasksIntent = new Intent(AddTaskActivity2.this, MainActivity.class);

                AddTaskActivity2.this.startActivity(sentToAddTasksIntent);

            }
        });

    }
}