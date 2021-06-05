package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private SQLiteHelper db;
    private ItemAdapter adapter;
    private ListView listView;
    private TextView tv_titlepage;
    Spinner spinner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        db = new SQLiteHelper(getContext());
        final Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int  mMonth=c.get(Calendar.MONTH)+1;
        String date = "/"+mMonth+"/"+mYear;

        List<Item> itemList = db.getByMonth(date);
        adapter = new ItemAdapter(getContext(),R.layout.item_holder,itemList,getActivity());
        listView = view.findViewById(R.id.todo_list_view);
        listView.setAdapter(adapter);
        tv_titlepage = view.findViewById(R.id.titlepage);

        //Setup spinner
        spinner = view.findViewById(R.id.spinner);
        spinner.setSelection(c.get(Calendar.MONTH));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new SQLiteHelper(getContext());
        final Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int  mMonth=c.get(Calendar.MONTH)+1;
        String date = "/"+mMonth+"/"+mYear;

        //Setup spinner
        spinner.setSelection(c.get(Calendar.MONTH));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Calendar c=Calendar.getInstance();
                int mYear=c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH)+1;
                String searchMonth = mMonth+"/"+mYear;
                if(position == 0) searchMonth= "1/"+mYear;
                if(position == 1) searchMonth= "2/"+mYear;
                if(position == 2) searchMonth= "3/"+mYear;
                if(position == 3) searchMonth= "4/"+mYear;
                if(position == 4) searchMonth= "5/"+mYear;
                if(position == 5) searchMonth= "6/"+mYear;
                if(position == 6) searchMonth= "7/"+mYear;
                if(position == 7) searchMonth= "8/"+mYear;
                if(position == 8) searchMonth= "9/"+mYear;
                if(position == 9) searchMonth= "10/"+mYear;
                if(position == 10) searchMonth= "11/"+mYear;
                if(position == 11) searchMonth= "12/"+mYear;
                List<Item> itemList = db.getByMonth(searchMonth);
                if(itemList.size() > 0){
                   adapter = new ItemAdapter(getContext(),R.layout.item_holder,itemList,getActivity());
                   listView.setAdapter(adapter);
                   listView.invalidate();
                   int tongtien = 0;
                   for(int i =0;i<itemList.size();i++){
                       Item ii = itemList.get(i);
                       tongtien = Integer.parseInt(ii.getDesc())+tongtien;
                   }
                   Locale locale = new Locale("vi", "VN");
                   NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                   tv_titlepage.setText("Danh sách chi tiêu trong tháng"+"\n"+"Tổng tiền trong tháng: "+currencyFormatter.format(tongtien));
                }else{
                    Toast.makeText(getActivity(), "Tháng này chưa có trong cơ sở dữ liệu",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<Item> itemList = db.getByMonth(date);
        adapter = new ItemAdapter(getContext(),R.layout.item_holder,itemList,getActivity());
        listView.setAdapter(adapter);

        int tongtien = 0;
        for(int i =0;i<itemList.size();i++){
            Item ii = itemList.get(i);
            tongtien = Integer.parseInt(ii.getDesc())+tongtien;
        }

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        tv_titlepage.setText("Danh sách chi tiêu trong tháng"+"\n"+"Tổng tiền trong tháng: "+currencyFormatter.format(tongtien));
    }
}