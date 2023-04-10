package com.example.roomdatabase.RoomDB;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Employee.class,exportSchema = false,version = 5)
public abstract  class EmployeeDB extends RoomDatabase {

    public abstract EmployeeDAO employeeDAO();
    public static final String DB_NAME="EmployeeDB";

    public static EmployeeDB INSTANCE;

    public static synchronized EmployeeDB getInstance(Context context)
    {
        if (INSTANCE==null)
        {
            INSTANCE= Room.databaseBuilder(context,EmployeeDB.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }



}
