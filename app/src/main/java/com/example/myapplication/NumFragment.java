package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumFragment extends Fragment {

    PieChart pieChart;
    Spinner spinner;
    SQLiteHelper db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumFragment newInstance(String param1, String param2) {
        NumFragment fragment = new NumFragment();
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
        View view = inflater.inflate(R.layout.fragment_num, container, false);

        pieChart = view.findViewById(R.id.pieChart);
        spinner = view.findViewById(R.id.spinner);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        SQLiteHelper db = new SQLiteHelper(getContext());
        try {
            final Calendar c=Calendar.getInstance();
            int mYear=c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH)+1;
            pieEntries = db.getPieChart(mMonth+"/"+mYear);
        }catch (Exception e){
            Toast.makeText(getActivity(),e+"",Toast.LENGTH_LONG).show();
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);


        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();

        //Calculate sum
        double sum = 0;
        for(int i = 0;i<pieEntries.size();i++){
            sum = sum + pieEntries.get(i).getValue();
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String center = "Tổng tiền: "+currencyFormatter.format(sum);
        pieChart.setCenterText(center);
        pieChart.setCenterTextSize(20f);

        //Handle spinner
        final Calendar c=Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH);
        spinner.setSelection(mMonth);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        SQLiteHelper db = new SQLiteHelper(getContext());
        try {
            final Calendar c=Calendar.getInstance();
            int mYear=c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH)+1;
            spinner.setSelection(mMonth-1);
            pieEntries = db.getPieChart(mMonth+"/"+mYear);
        }catch (Exception e){
            Toast.makeText(getActivity(),e+"",Toast.LENGTH_LONG).show();
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);


        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();

        //Calculate sum
        double sum = 0;
        for(int i = 0;i<pieEntries.size();i++){
            sum = sum + pieEntries.get(i).getValue();
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String center = "Tổng tiền: "+currencyFormatter.format(sum);
        pieChart.setCenterText(center);
        pieChart.setCenterTextSize(20f);

        //spinner
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
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                pieEntries = db.getPieChart(searchMonth);
                if(pieEntries.size() > 0){
                    PieDataSet dataSet = new PieDataSet(pieEntries,"");
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    dataSet.setValueTextColor(Color.BLACK);
                    dataSet.setValueTextSize(16f);
                    PieData pieData = new PieData(dataSet);
                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.animate();
                    pieChart.notifyDataSetChanged();
                    pieChart.invalidate();

                    //Calculate sum
                    double sum = 0;
                    for(int i = 0;i<pieEntries.size();i++){
                        sum = sum + pieEntries.get(i).getValue();
                    }
                    Locale locale = new Locale("vi", "VN");
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                    String center = "Tổng tiền: "+currencyFormatter.format(sum);
                    pieChart.setCenterText(center);
                    pieChart.setCenterTextSize(20f);
                }else{
                    Toast.makeText(getActivity(), "Tháng này chưa có trong cơ sở dữ liệu",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}