package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class TaskDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        String getTask = intent.getStringExtra("task");
        TextView taskText = findViewById(R.id.textView7);
        taskText.setText(getTask);
        String getTaskInfo = intent.getStringExtra("taskInfo");
        TextView taskInfoText = findViewById(R.id.textView8);
        taskInfoText.setText(getTaskInfo);
        ImageView storeImg = findViewById(R.id.taskImage);
//        Intent intent = getIntent();

        if (intent.getExtras().getString("taskImage") != null) {
            Amplify.Storage.downloadFile(
                    intent.getExtras().getString("taskImage"),
                    new File(getApplicationContext().getFilesDir() + "/" + intent.getExtras().getString("taskImage") + ".jpg"),
                    result -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(result.getFile().getPath());
                        storeImg.setImageBitmap(bitmap);
                        Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                    },
                    error -> Log.e("MyAmplifyApp", "Download Failure", error)
            );
        }

        Button goHomeButtonDetail = findViewById(R.id.button6);
        goHomeButtonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent goHomeTasks = new Intent(TaskDetailActivity.this, MainActivity.class);
                startActivity(goHomeTasks);
            }
        });
    }
}