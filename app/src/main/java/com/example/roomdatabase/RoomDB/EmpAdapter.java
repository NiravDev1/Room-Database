package com.example.roomdatabase.RoomDB;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.R;
import com.example.roomdatabase.Update_activity;

import java.util.ArrayList;
import java.util.List;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.EmpViewholder> {

    Context context;
    List<Employee> empList;

    EmpAdapter.EmpDU empDU;

    public void removeEmp(int postion) {
        empList.remove(postion);
        notifyDataSetChanged();
    }

    public EmpAdapter(Context context, List<Employee> empList, EmpDU empDU) {
        this.context = context;
        this.empList = empList;
        this.empDU = empDU;
    }

    public void addEmp(Employee employee) {
        empList.add(employee);
        notifyDataSetChanged();
    }
    public void cleardata()
    {
        empList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmpViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_raw_card, parent, false);
        return new EmpViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpViewholder holder, int position) {

        Employee employee = empList.get(position);
        holder.emp_name.setText("EMP Name:" + employee.getName());
        holder.emp_salary.setText("EMP Salary:" + employee.getSalary());
        holder.emp_gender.setText("EMP Gender:" + employee.getGender());
        holder.emp_dob.setText("EMP DOB:" + employee.getDob());

        holder.emp_DeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                empDU.delete(employee.getId(), position);

            }
        });
        holder.emp_Update_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empDU.update(employee);
            }
        });

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public void filterList(List<Employee> filterempList)
    {
            empList=filterempList;
            notifyDataSetChanged();
    }

    public class EmpViewholder extends RecyclerView.ViewHolder {

        AppCompatTextView emp_name, emp_salary, emp_dob, emp_gender;
        AppCompatButton emp_DeleteBTN, emp_Update_BTN;

        public EmpViewholder(@NonNull View itemView) {
            super(itemView);
            emp_name = itemView.findViewById(R.id.card_emp_name_id);
            emp_salary = itemView.findViewById(R.id.card_emp_salary_id);
            emp_dob = itemView.findViewById(R.id.card_emp_dob_id);
            emp_gender = itemView.findViewById(R.id.card_emp_gender_id);
            emp_DeleteBTN = itemView.findViewById(R.id.card_emp_delete_btn_id);
            emp_Update_BTN = itemView.findViewById(R.id.card_emp_update_btn_id);


        }
    }

    public interface EmpDU {

        void delete(int id, int posi);

        void update(Employee employee);

    }


}
