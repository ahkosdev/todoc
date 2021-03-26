package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.Database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TodocDaoTest {

    private TodocDatabase mDatabase;
    private static long CAT_PROJECT_ID = 4;
    private static Project PROJECT_CAT = new Project(CAT_PROJECT_ID, "cat", 0x00cc3f3f);
    private static long DOG_PROJECT_ID = 5;
    private static Project PROJECT_DOG = new Project(DOG_PROJECT_ID, "dog", 0x00bc4444);
    private static Task ENTRETIEN = new Task(1,CAT_PROJECT_ID,"Netoyage des Vitres", 15);
    private static Task JARDINAGE = new Task(2,CAT_PROJECT_ID, "Arroser les Plantes",10);
    private static Task CUISINE = new Task(3, CAT_PROJECT_ID, "Vider le lave vaisselle", 8);
    private static Task ENTRETIEN_TOILETTE = new Task(4, CAT_PROJECT_ID,"Netoyer les toilettes",7);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();
    @Before
    public void initDb() throws Exception {
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocDatabase.class).allowMainThreadQueries().build();

    }

    @Test
    public void insertAndGetProject() throws InterruptedException {

        mDatabase.projectDao().creteProject(PROJECT_CAT);

       List<Project> projects = LiveDataTestUtil.getValue(mDatabase.projectDao().getProjects());
        assertTrue(projects.get(0).getName().equals(PROJECT_CAT.getName())&& projects.get(0).getId()== CAT_PROJECT_ID);

    }

    @Test
    public void taskListIsEmpty() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTaskWithSuccess() throws InterruptedException {

        mDatabase.projectDao().creteProject(PROJECT_CAT);
        mDatabase.taskDao().insertTask(ENTRETIEN);
        mDatabase.taskDao().insertTask(JARDINAGE);
        mDatabase.taskDao().insertTask(CUISINE);
        mDatabase.taskDao().insertTask(ENTRETIEN_TOILETTE);

        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.taskDao().getTasks());
        assertTrue(tasks.size()== 4);
    }

    //@Test
   // public void insertAndUpdateTaskWithSuccess() throws InterruptedException {

       // mDatabase.projectDao().creteProject(PROJECT_CAT);
       // mDatabase.taskDao().insertTask(ENTRETIEN);
        //Task taskUpdate = LiveDataTestUtil.getValue(mDatabase.taskDao().getTasks(CAT_PROJECT_ID)).get(0);
        //taskUpdate.setSelected(true);
       // mDatabase.taskDao().updateTask(taskUpdate);
        //List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.taskDao().getTasks(CAT_PROJECT_ID));
        //assertTrue(tasks.size()==1 && tasks.get(0).getSelected());


   // }

    @Test
    public void insertAndDeleteTaskWithSuccess() throws InterruptedException {

        mDatabase.projectDao().creteProject(PROJECT_CAT);
        mDatabase.taskDao().insertTask(ENTRETIEN);
        mDatabase.taskDao().insertTask(CUISINE);
        Task updateTask = LiveDataTestUtil.getValue(mDatabase.taskDao().getTasks()).get(1);
        mDatabase.taskDao().deleteTask(updateTask.getId());
        List<Task> tasks = LiveDataTestUtil.getValue(mDatabase.taskDao().getTasks());
        assertTrue(tasks.size()==1);

    }

    @After
    public void closeDb(){
        mDatabase.close();
    }
}
