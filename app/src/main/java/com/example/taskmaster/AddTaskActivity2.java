package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddTaskActivity2 extends AppCompatActivity {
    public static final String TAG = "ADD TASK";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Intent uploadFile;
    private String imgName;
    private Uri imgData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);
        Button button3 = findViewById(R.id.button3);
        Button addFile = findViewById(R.id.button5);
        addFile.setOnClickListener(v -> {
            uploadFile = new Intent(Intent.ACTION_GET_CONTENT);
            uploadFile.setType("*/*");
            uploadFile = Intent.createChooser(uploadFile, "Choose a file");
            startActivityForResult(uploadFile, 1234);

        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast punchToast = Toast.makeText(getApplicationContext(),"submitted!", Toast.LENGTH_SHORT);
//                punchToast.show();
                if (imgData != null) {
                    try {
                        InputStream exampleInputStream = getContentResolver().openInputStream(imgData);
                        Amplify.Storage.uploadInputStream(
                                imgName,
                                exampleInputStream,
                                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
                        );
                    } catch (FileNotFoundException error) {
                        Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
                    }
                }
                EditText userInput = findViewById(R.id.editTextTextPersonName);
                EditText userInput2 = findViewById(R.id.editTextTextPersonName2);
                EditText userInput3 = findViewById(R.id.editTextTextPersonName3);
                String userInputText = userInput.getText().toString();
                String userInputText2 = userInput2.getText().toString();
                String userInputText3 = userInput3.getText().toString();

                Task task = Task.builder()
                        .title(userInputText)
                        .body(userInputText2)
                        .state(userInputText3)
                        .file(imgName)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(task),
                        response -> Log.i(TAG, "Added task with id: " + response.getData().getId()),
                        error -> Log.e(TAG, "Create failed", error)
                );
                Intent goToHomePage = new Intent(AddTaskActivity2.this, MainActivity.class);
                startActivity(goToHomePage);

//                Task newTask = new Task(userInputText, userInputText2, userInputText3 );
//                Long addedTaskID = AppDataBase.getInstance(getApplicationContext()).TaskDao().addTask(newTask);
//                // TextView userItem = AddTasks.this.findViewById(R.id.textView2);
//                //userItem.setText("Submitted");
//
//                System.out.println(
//                        "++++++++++++++++++++++++++++++++++++++++++++++++++" +
//                                " Student ID : " + addedTaskID
//                                +
//                                "++++++++++++++++++++++++++++++++++++++++++++++++++");
//                Intent sentToAddTasksIntent = new Intent(AddTaskActivity2.this, MainActivity.class);
//
//                AddTaskActivity2.this.startActivity(sentToAddTasksIntent);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = new File(data.getData().getPath());
        imgName = file.getName();
        imgData = data.getData();
    }
}