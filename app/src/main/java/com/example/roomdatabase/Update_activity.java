package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.roomdatabase.RoomDB.Employee;
import com.example.roomdatabase.RoomDB.EmployeeDAO;
import com.example.roomdatabase.RoomDB.EmployeeDB;
import com.example.roomdatabase.databinding.ActivityUpdateBinding;

import java.util.Calendar;

public class Update_activity extends AppCompatActivity {
    ActivityUpdateBinding ub;
    Employee employee;
    private EmployeeDB employeeDB;
    private EmployeeDAO employeeDAO;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ub = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(ub.getRoot());
        setTitle("Update Employee");

        employeeDB = EmployeeDB.getInstance(this);
        employeeDAO = employeeDB.employeeDAO();
        employee = (Employee) getIntent().getSerializableExtra("model");

        ub.empNameUId.setText(employee.getName());
        ub.empSalaryUId.setText(employee.getSalary());
        ub.empDobUId.setText(employee.getDob());
        ub.empUpBTNId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gender = null;
                int gid = ub.empRadioGroupId.getCheckedRadioButtonId();
                if (gid != -1) {
                    radioButton = findViewById(gid);
                    Gender = radioButton.getText().toString();
                }
                String Name=ub.empNameUId.getText().toString();
                String Salary=ub.empSalaryUId.getText().toString();
                if (Name.isEmpty())
                {
                    Toast.makeText(Update_activity.this, "fill the Name", Toast.LENGTH_SHORT).show();
                }
                else  if (Salary.isEmpty())
                {
                    Toast.makeText(Update_activity.this, "Fill the Salary", Toast.LENGTH_SHORT).show();
                }
                else if (ub.empRadioGroupId.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Update_activity.this, "select gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Employee employeeup = new Employee(employee.getId(), ub.empNameUId.getText().toString(), ub.empSalaryUId.getText().toString(), ub.empDobUId.getText().toString(), Gender);
                    employeeDAO.update_emp(employeeup);
                    finish();
                }

            }
        });


    }

    public void datepick(View view) {
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Update_activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ub.empDobUId.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}