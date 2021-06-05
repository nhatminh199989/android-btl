package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Spinner spinner;
    Button btn_cancel,btn_date,btn_add;
    EditText et_date,et_describe,et_title;
    private int mYear, mMonth, mDay;
    private SQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        db = new SQLiteHelper(this);
        //spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spiner_item,getResources().getStringArray(R.array.loai_chi_tieu));
        spinner.setAdapter(adapter);
        //btn cancel
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //btn date
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c=Calendar.getInstance();
                mYear=c.get(Calendar.YEAR);
                mMonth=c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                                et_date.setText(date.trim());
                            }
                        },mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        //btn add
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String category = spinner.getSelectedItem().toString();
                String describe = et_describe.getText().toString();
                String date = et_date.getText().toString().trim();
                Item i = new Item(title,date,describe,category);
                db.addItem(i);
                finish();
            }
        });

    }

    public void initView(){
        spinner = findViewById(R.id.category_spiner);
        btn_cancel = findViewById(R.id.btnCancel);
        btn_date = findViewById(R.id.btnChosseDay);
        et_date = findViewById(R.id.date_input);
        btn_add = findViewById(R.id.btnSaveTask);
        et_title = findViewById(R.id.title);
        et_describe = findViewById(R.id.describe);
    }
}