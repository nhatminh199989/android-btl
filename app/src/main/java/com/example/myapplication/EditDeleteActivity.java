package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class EditDeleteActivity extends AppCompatActivity {
    Spinner spinner;
    Button btn_cancel,btn_date,btn_add,btn_del,btn_update;
    EditText et_date,et_describe,et_title;
    private int mYear, mMonth, mDay;
    private SQLiteHelper db;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);
        initView();
        db = new SQLiteHelper(this);
        //GET SELECTED ITEM
        id=0;
        final Intent intent=getIntent();
        String category = "";
        if(intent.getSerializableExtra("item")!=null) {
            Item t = (Item) intent.getSerializableExtra("item");
            id=t.getId();
            et_title.setText(t.getTitle());
            et_date.setText(t.getDate());
            et_describe.setText(t.getDesc());
            category = t.getCategory();
        }

        //spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spiner_item,getResources().getStringArray(R.array.loai_chi_tieu));
        spinner.setAdapter(adapter);
        switch (category){
            case "\uD83D\uDD0CĐiện,Nước":{
                spinner.setSelection(0);
                break;
            }
            case "\uD83C\uDF54Ăn uống":{
                spinner.setSelection(1);
                break;
            }
            case "\uD83C\uDFE0Tiền nhà":{
                spinner.setSelection(2);
                break;
            }
            case "\uD83D\uDEB2Phương tiện":{
                spinner.setSelection(3);
                break;
            }
            case "\uD83D\uDED2Mua sắm":{
                spinner.setSelection(4);
                break;
            }
            case "\uD83C\uDF7FGiải trí":{
                spinner.setSelection(4);
                break;
            }
            case "Khác ":{
                spinner.setSelection(5);
                break;
            }
        }

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
                DatePickerDialog datePickerDialog=new DatePickerDialog(EditDeleteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                                et_date.setText(date.trim());
                            }
                        },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        //Remove item
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteItem(id);
                finish();
            }
        });
        //Update item
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item(id,
                        et_title.getText().toString(),
                        et_date.getText().toString(),
                        et_describe.getText().toString(),
                        spinner.getSelectedItem().toString()
                        );
                db.updateItem(item);
                finish();
            }
        });
    }

    public void initView(){
        spinner = findViewById(R.id.category_spiner);
        btn_cancel = findViewById(R.id.btnCancel);
        btn_date = findViewById(R.id.btnChosseDay);
        btn_del = findViewById(R.id.btnDel);
        btn_update = findViewById(R.id.btnUpdate);
        et_date = findViewById(R.id.date_input);
        btn_add = findViewById(R.id.btnSaveTask);
        et_title = findViewById(R.id.title);
        et_describe = findViewById(R.id.describe);
    }
}