package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.example.taskmaster.auth.AuthActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksRecyclerViewAdapter.OnTaskSelectedListener{

    private static final String TAG = "MainActivity";
    private List<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        configureAmplify();
        userSession();
        this.renderRecyclerViewFromDatabase();
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
        Button signoutButton = findViewById(R.id.signoutButton);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                userSession();
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())

                );

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recreate();
                    }
                }, 1500);

                Intent signOut = new Intent(MainActivity.this, AuthActivity.class);
                MainActivity.this.startActivity(signOut);
            }
        });

        Task taskA = new Task("Eating", "HUMMMM", "IN_PROGRESS");
        Task taskB = new Task("Grade labs", "By 10:00pm", "NEW");
        Task taskC = new Task("SLEEPING", "IN_DREAM", "ASSIGNED" );
        this.tasks = new LinkedList<>();
        this.tasks.add(taskA);
        this.tasks.add(taskB);
        this.tasks.add(taskC);
        // Re: https://developer.android.com/guide/topics/ui/layout/recyclerview
        // Render Task items to the screen with RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView23);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Define Adapter class that is able to communicate with RecyclerView
        recyclerView.setAdapter(new TasksRecyclerViewAdapter(this.tasks, (TasksRecyclerViewAdapter.OnTaskSelectedListener) this));
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task todo : response.getData()) {
                        Task taskOrg = new Task(todo.getTitle(),todo.getBody(),todo.getState());
                        Log.i("graph testing", todo.getTitle());
                        tasks.add(taskOrg);
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error));



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

        this.renderRecyclerViewFromDatabase();
    }
    @Override
    public void onTaskSelected(Task task) {
        Log.i(TAG, "RecyclerView TextView clicked on this Task: " + task);
        Intent taskDetailsIntent = new Intent(this, TaskDetailActivity.class);
        taskDetailsIntent.putExtra("task", task.getTitle());
        taskDetailsIntent.putExtra("taskInfo", task.getBody()+" "+task.getState());
        MainActivity.this.startActivity(taskDetailsIntent);
    }
    private void renderRecyclerViewFromDatabase() {
        // Build the database and instantiate the List that hold the tasks from database
        this.tasks = new LinkedList<>();

        // Get everything from database and put in list of tasks to be rendered by recycler view
        this.tasks.addAll(AppDataBase.getInstance(getApplicationContext()).TaskDao().getAll());

        // Re android doc: https://developer.android.com/guide/topics/ui/layout/recyclerview
        // Render Task items to the screen with RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView23);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Define Adapter class that is able to communicate with RecyclerView
        recyclerView.setAdapter(new TasksRecyclerViewAdapter(this.tasks, this));
    }
    private void configureAmplify() {
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin()); // stores records locally
            Amplify.addPlugin(new AWSApiPlugin()); // stores things in DynamoDB and allows us to perform GraphQL queries
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }
    }

    private void userSession() {
        Amplify.Auth.fetchUserAttributes(
                attributes -> Log.i(TAG, "User attributes = " + attributes.toString()),
                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
        );

        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i(TAG, result.toString());
                },
                error -> Log.e(TAG, error.toString())
        );

        Amplify.Auth.signInWithWebUI(
                this,
                result -> Log.i("AuthQuickStart", result.toString()),
                error -> Log.e("AuthQuickStart", error.toString())
        );
    }


}