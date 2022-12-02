package com.example.da1_group6.ui_staff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_qlvmb_staff;
import com.example.da1_group6.dao.DAO_VeMB;
import com.example.da1_group6.model.VeMB;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Fragment_QLVMB_staff extends Fragment {
    TextView tv_date;
    RecyclerView recyclerView;
    DAO_VeMB dao;
    ArrayList<VeMB> list;
    Adapter_Recycler_qlvmb_staff adapter;
    String user;
    TextView tv_no_result;
    ImageView img;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlvmb_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_qlvmb_staff);
        searchView = view.findViewById(R.id.search_qlvmb_staff);

        tv_no_result = view.findViewById(R.id.tv_no_result_dxn_staff);
        img = view.findViewById(R.id.img_sad_dxn_staff);

        SharedPreferences preferences = getActivity().getSharedPreferences("TB", Context.MODE_PRIVATE);
        user = preferences.getString("User", "");

        reload();

        if(list.isEmpty()) {
            tv_no_result.setText("Hmm...Có vẻ như không có gì ở đây ");
            img.setImageResource(R.drawable.img_sad);
        } else {
            tv_no_result.setText("");
            img.setImageDrawable(null);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findItem(newText);
                return false;
            }
        });
    }

    private void findItem(String newText) {
        ArrayList<VeMB> listVMB = new ArrayList<>();
        for (VeMB vmb : list) {
            if(vmb.getMacb().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getTenkh().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getDiemdi().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getDiemden().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getTimebay().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            }
        }

        if(listVMB.isEmpty()) {
            listVMB.clear();
            adapter.setListSearch(listVMB);
            tv_no_result.setText("Hmm...Không có dữ liệu phù hợp ");
            img.setImageResource(R.drawable.img_sad);
        } else {
            adapter.setListSearch(listVMB);
            tv_no_result.setText("");
            img.setImageDrawable(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    void reload() {
        dao = new DAO_VeMB(getContext());
        list = dao.getVMBtheoNV(user, 1);
        adapter = new Adapter_Recycler_qlvmb_staff(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}