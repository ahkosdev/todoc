package com.cleanup.todoc.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void creteProject(Project project);

    @Query("SELECT * FROM Project WHERE id = :projectId ")
    LiveData<Project> getProject(Long projectId);
}