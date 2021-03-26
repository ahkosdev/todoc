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

import java.util.concurrent.Callable;

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
                ContentValues contentValues1 = new ContentValues();
                ContentValues contentValues2 = new ContentValues();
                ContentValues contentValues3 = new ContentValues();
                contentValues.put("id",4L);
                contentValues.put("name", "toDocTest");
                contentValues.put("color", 0x3f48cc);
                contentValues1.put("id", 1L);
                contentValues1.put("name","Projet Tartampion");
                contentValues1.put("color",0xFFEADAD1);
                contentValues2.put("id", 2L);
                contentValues2.put("name","Projet Lucidia");
                contentValues2.put("color", 0xFFB4CDBA);
                contentValues3.put("id",3L);
                contentValues3.put("name", "Projet Circus");
                contentValues3.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.IGNORE,contentValues);
                db.insert("Project",OnConflictStrategy.IGNORE,contentValues1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);
            }
        };
    }

}
