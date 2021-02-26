package com.cleanup.todoc.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class},version = 1,exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    public static volatile TodocDatabase INSTANCE;

    public abstract ProjectDao  projectDao();
    public abstract TaskDao taskDao();

    public static TodocDatabase getInstance(Context context){

        if (INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TodocDatabase.class,"TodocDatabase.db").addCallback(prepopulateDatabase()).build();
                }
            }
        }
        return INSTANCE;

    }

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 4L);
                contentValues.put("name", "ToDocTest");
                contentValues.put("color", 0x00cc3f3f);
                db.insert("Project", OnConflictStrategy.IGNORE,contentValues);
            }
        };
    }

}
