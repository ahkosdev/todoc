package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;
    private LiveData<List<Project>> currentProject;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }
    public void init(){
        if (currentProject != null){
            return;
        }
        currentProject = projectDataSource.getProjects();
    }

    public LiveData<List<Project>> getProject(){
        return currentProject;
    }

    public LiveData<List<Task>> getTasks(){
        return taskDataSource.getTasks();
    }
    public void createdTask(Task task){
        executor.execute(()->{
            taskDataSource.createTask(task);
        });
    }
    public void deleteTask(long taskId){
        executor.execute(()->{
            taskDataSource.deleteTask(taskId);
        });
    }
    public void updateTask(Task task){
        executor.execute(()-> {
            taskDataSource.updateTask(task);
        });
    }
}
