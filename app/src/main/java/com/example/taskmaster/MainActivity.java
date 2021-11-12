package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.TaskToDo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksRecyclerViewAdapter.OnTaskSelectedListener{

    private static final String TAG = "MainActivity";
    public List<TaskToDo> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
        RecyclerView tasksListRecyclerView = findViewById(R.id.recyclerView23);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                tasksListRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });
        Amplify.API.query(
                ModelQuery.list(TaskToDo.class),
                response -> {
                    for (TaskToDo taskTodo : response.getData()) {
                       tasks.add(taskTodo);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );
        tasksListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksListRecyclerView.setAdapter(new TasksRecyclerViewAdapter(this.tasks, (TasksRecyclerViewAdapter.OnTaskSelectedListener) this));
//        this.renderRecyclerViewFromDatabase();
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

//        Task taskA = new Task("Eating", "HUMMMM", "IN_PROGRESS");
//        Task taskB = new Task("Grade labs", "By 10:00pm", "NEW");
//        Task taskC = new Task("SLEEPING", "IN_DREAM", "ASSIGNED" );
//        this.tasks = new LinkedList<>();
//        this.tasks.add(taskA);
//        this.tasks.add(taskB);
//        this.tasks.add(taskC);
        // Re: https://developer.android.com/guide/topics/ui/layout/recyclerview
        // Render Task items to the screen with RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView23);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Define Adapter class that is able to communicate with RecyclerView
        recyclerView.setAdapter(new TasksRecyclerViewAdapter(this.tasks, (TasksRecyclerViewAdapter.OnTaskSelectedListener) this));
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

//        this.renderRecyclerViewFromDatabase();
    }
    @Override
    public void onTaskSelected(TaskToDo task) {
        Log.i(TAG, "RecyclerView TextView clicked on this Task: " + task);
        Intent taskDetailsIntent = new Intent(this, TaskDetailActivity.class);
        taskDetailsIntent.putExtra("task", task.getTitle());
        taskDetailsIntent.putExtra("taskInfo", task.getBody()+" "+task.getStatus());
        MainActivity.this.startActivity(taskDetailsIntent);
    }
//    private void renderRecyclerViewFromDatabase() {
//        // Build the database and instantiate the List that hold the tasks from database
//        this.tasks = new LinkedList<>();
//
//        // Get everything from database and put in list of tasks to be rendered by recycler view
//        this.tasks.addAll(AppDataBase.getInstance(getApplicationContext()).TaskDao().getAll().);
//
//        // Re android doc: https://developer.android.com/guide/topics/ui/layout/recyclerview
//        // Render Task items to the screen with RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recyclerView23);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        // Define Adapter class that is able to communicate with RecyclerView
//        recyclerView.setAdapter(new TasksRecyclerViewAdapter(this.tasks, this));
//    }
}