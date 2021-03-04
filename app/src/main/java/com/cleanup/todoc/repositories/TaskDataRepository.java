package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.Database.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    public LiveData<List<Task>> getTasks(long projectId){
        return taskDao.getTasks(projectId);
    }
    public void createTask(Task task){
         taskDao.insertTask( task);
    }
    public void deleteTask(long taskId){
        taskDao.deleteTask(taskId);
    }
    public void updateTask(Task task){
        taskDao.updateTask(task);
    }
}
