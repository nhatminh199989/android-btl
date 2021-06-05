package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FloatingActionButton btn_add;
    private SQLiteHelper db;
    private ItemAdapter adapter;
    private ListView  listView;
    TextView tongtien;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btn_add = view.findViewById(R.id.fabutton);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivity(intent);
            }
        });

        final Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int  mMonth=c.get(Calendar.MONTH)+1;
        int mDay=c.get(Calendar.DAY_OF_MONTH);
        String date = mDay+"/"+mMonth+"/"+mYear;

        db = new SQLiteHelper(getContext());
        List<Item> itemList = db.getByDate(date);

        adapter = new ItemAdapter(getContext(),R.layout.item_holder,itemList,getActivity());
        listView = view.findViewById(R.id.todo_list_view);
        listView.setAdapter(adapter);

        //tong tien
        tongtien = view.findViewById(R.id.tongtien);
        int sotien = 0;
        for(int i =0;i<itemList.size();i++){
            Item ii = itemList.get(i);
            sotien = Integer.parseInt(ii.getDesc())+sotien;
        }

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        tongtien.setText("Tổng tiền: "+currencyFormatter.format(sotien));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new SQLiteHelper(getContext());

        final Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int  mMonth=c.get(Calendar.MONTH)+1;
        int mDay=c.get(Calendar.DAY_OF_MONTH);
        String date = mDay+"/"+mMonth+"/"+mYear;

        List<Item> itemList = db.getByDate(date);
        adapter = new ItemAdapter(getContext(),R.layout.item_holder,itemList,getActivity());
        listView.setAdapter(adapter);
        int sotien = 0;
        for(int i =0;i<itemList.size();i++){
            Item ii = itemList.get(i);
            sotien = Integer.parseInt(ii.getDesc())+sotien;
        }

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        tongtien.setText("Tổng tiền: "+currencyFormatter.format(sotien));
    }
}