package com.example.roomdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.roomdatabase.RoomDB.EmpAdapter;
import com.example.roomdatabase.RoomDB.Employee;
import com.example.roomdatabase.RoomDB.EmployeeDAO;
import com.example.roomdatabase.RoomDB.EmployeeDB;
import com.example.roomdatabase.RoomDB.EmployeeDB_Impl;
import com.example.roomdatabase.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EmpAdapter.EmpDU {

    ActivityMainBinding b;
    Dialog dialog;

    AppCompatEditText nameEmp, salaryEmp;
    AppCompatTextView dOBEMp, title_text;
    RadioGroup radioGroup;
    AppCompatButton addEMp;
    RadioButton genderEmp;
    private EmployeeDB employeeDB;
    private EmployeeDAO employeeDAO;
    ArrayList<Employee> emplist;
    EmpAdapter empAdapter;
    Employee employee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        employeeDB = EmployeeDB.getInstance(this);
        employeeDAO = employeeDB.employeeDAO();
        emplist = new ArrayList<>();
        emplist = (ArrayList<Employee>) employeeDAO.getallemp();


        // TODO: 08-04-2023  recylerview work------------------------------------------------------------
        b.empRecyclerviewId.setLayoutManager(new LinearLayoutManager(this));
        empAdapter = new EmpAdapter(this, emplist, this);
        b.empRecyclerviewId.setAdapter(empAdapter);
        empAdapter.cleardata();

        List<Employee> employeeList = employeeDAO.getallemp();
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            emplist.add(employee);
        }

        // TODO: 09-04-2023  Searchview -----------------------------------------------------------------
        b.serarchViewId.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


        // TODO: 08-04-2023 add dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nameEmp = dialog.findViewById(R.id.emp_name_d_id);
        salaryEmp = dialog.findViewById(R.id.emp_salary_d_id);
        dOBEMp = dialog.findViewById(R.id.emp_dob_id);
        radioGroup = dialog.findViewById(R.id.emp_radio_group_id);
        title_text = dialog.findViewById(R.id.add_text_d_id);
        addEMp = dialog.findViewById(R.id.emp_add_BTN_id);


        b.toAddLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addEMp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // TODO: 08-04-2023 gender work
                        String GenderEMp = null;
                        int gid = radioGroup.getCheckedRadioButtonId();
                        if (gid != -1) {
                            genderEmp = dialog.findViewById(gid);
                            GenderEMp = genderEmp.getText().toString();
                        }

                        String NameEMP = nameEmp.getText().toString();
                        String SalaryEMP = salaryEmp.getText().toString();
                        String DOBEMP = dOBEMp.getText().toString();

                        if (NameEMP.isEmpty()) {
                            Toast.makeText(MainActivity.this, "fill name", Toast.LENGTH_SHORT).show();
                        } else if (SalaryEMP.isEmpty()) {
                            Toast.makeText(MainActivity.this, "fill salary", Toast.LENGTH_SHORT).show();
                        } else if (radioGroup.getCheckedRadioButtonId() == -1) {

                            Toast.makeText(MainActivity.this, "select Gender", Toast.LENGTH_SHORT).show();

                        } else if (DOBEMP.equals("DOB")) {
                            Toast.makeText(MainActivity.this, "select  dob", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                            employee = new Employee(0, NameEMP, SalaryEMP, DOBEMP, GenderEMp);
                            System.out.println(employee + "qqqq");
                            employeeDAO.Insert_emp(employee);
//                            empAdapter.addEmp(employee);
                            emplist.add(employee);
                            empAdapter.notifyDataSetChanged();

                            Toast.makeText(MainActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                            nameEmp.setText(null);
                            salaryEmp.setText(null);
                            dOBEMp.setText("DOB");
                            radioGroup.clearCheck();
                            dialog.dismiss();

                        }


                    }
                });
                dialog.show();
            }
        });


    }

    private void filter(String newText) {

        List<Employee> filteremlist = new ArrayList<>();
        for (Employee employee1 : emplist) {
            if (employee1.getName().toLowerCase().contains(newText.toLowerCase()) || employee1.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteremlist.add(employee1);
            }
        }
        empAdapter.filterList(filteremlist);


    }

    public void datpick(View view) {
        final Calendar c = Calendar.getInstance();

        // on below line we are getting
        // our day, month and year.
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dOBEMp.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

//    private void fetchdata() {
//        empAdapter.cleardata();
//        List<Employee> employeeList = employeeDAO.getallemp();
//        for (int i = 0; i < employeeList.size(); i++) {
//            Employee employee = employeeList.get(i);
//            employeeList.add(employee);
//            empAdapter.notifyDataSetChanged();
//        }
//        empAdapter.notifyDataSetChanged();
//
//    }


    @Override
    public void delete(int id, int posi) {

        employeeDAO.delete_emp(id);
        empAdapter.removeEmp(posi);
        empAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(Employee employee) {
        Intent intent = new Intent(this, Update_activity.class);
        intent.putExtra("model", employee);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        fetchdata();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.sort_m_id) {
            Toast.makeText(this, "sort", Toast.LENGTH_SHORT).show();
            sort();
            empAdapter.notifyDataSetChanged();

        }
        return super.onOptionsItemSelected(item);
    }

    private void sort() {

        Collections.sort(emplist, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {

                return o1.getName().compareToIgnoreCase(o2.getName());

            }
        });
    }
}