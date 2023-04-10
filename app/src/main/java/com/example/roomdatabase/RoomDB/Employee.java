package com.example.roomdatabase.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "EmployeeT")
public class Employee implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String Name;
    private String Salary;
    private String Dob;
    private String Gender;


    public Employee(int id, String name, String salary,  String dob, String gender) {
        Id = id;
        Name = name;
        Salary = salary;
        Dob = dob;
        Gender = gender;
    }


    public Employee() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }


    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Salary='" + Salary + '\'' +
                ", Dob='" + Dob + '\'' +
                ", Gender='" + Gender + '\'' +
                '}';
    }
}
