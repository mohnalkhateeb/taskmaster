package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.amplifyframework.datastore.generated.model.TaskToDo;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE id =:id")
    List<Task> getTaskById(Long id);

    @Insert
    Long addTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);
}
