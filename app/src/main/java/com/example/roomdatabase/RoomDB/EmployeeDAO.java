package com.example.roomdatabase.RoomDB;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDAO {


    @Insert
    void Insert_emp(Employee employee);

    @Update
    void update_emp(Employee employee);

    @Query("delete from EmployeeT where Id=:id")
    void delete_emp(int id);

    @Query("select * from EmployeeT")
    List<Employee> getallemp();
}
