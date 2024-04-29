package com.example.travelor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travelor.GaodeMapActivity;
import com.example.travelor.MonitoringActivity;
import com.example.travelor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitoringFragment extends Fragment {


    private TextView mDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monitoring, container, false);


        LinearLayout ll = rootView.findViewById(R.id.he2);
        View iv = rootView.findViewById(R.id.location);
        mDate = rootView.findViewById(R.id.date);

        mDate.setText(getCurrentTimeFormat());
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到目标Activity
                Intent intent = new Intent(getActivity(), MonitoringActivity.class);
                startActivity(intent);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 跳转到目标Activity
                Intent intent = new Intent(getActivity(), GaodeMapActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}