package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskToDo;

import java.util.ArrayList;
import java.util.List;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.TaskViewHolder> {

    List<TaskToDo> allTasks = new ArrayList<TaskToDo>();
    private OnTaskSelectedListener listener;
    // create a public constructor to be used in the activity (to set the data to the adapter)


    public TasksRecyclerViewAdapter(List<TaskToDo> allTasks, OnTaskSelectedListener listener) {
        this.allTasks = allTasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TasksRecyclerViewAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reference the fragment that will be used
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tasks, parent, false);
        final TaskViewHolder holder = new TaskViewHolder(view);
        // Do this to attach onClickListener to each fragment in RecyclerView
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTaskSelected(holder.mTask);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksRecyclerViewAdapter.TaskViewHolder holder, int position) {
        TaskToDo taskAtPosition = this.allTasks.get(position);
        // Set the reference of which Task
        holder.mTask = taskAtPosition;
        holder.mTitleView.setText(taskAtPosition.getTitle());
        holder.mBodyView.setText(taskAtPosition.getDescription());
        holder.mStateView.setText(taskAtPosition.getStatus());

    }


    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
        public final TextView mTitleView;
        public final TextView mBodyView;
        public final TextView mStateView;
        public TaskToDo mTask;

        public TaskViewHolder(View view) {
            super(view);
            // Set the text/values of all the views in the fragment
            this.mTitleView = itemView.findViewById(R.id.title);
            this.mBodyView = itemView.findViewById(R.id.body);
            this.mStateView = itemView.findViewById(R.id.state);
        }

    }


    public interface OnTaskSelectedListener {
        void onTaskSelected(TaskToDo task);
    }
}
