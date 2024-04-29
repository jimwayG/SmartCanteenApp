package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.travelor.fragment.FrontPageFragment1;

import com.example.travelor.fragment.MineFragment;
import com.example.travelor.fragment.PlanFragment;
import com.example.travelor.fragment.MonitoringFragment;

public class PageJumpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_layout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FrontPageFragment1 fgm_front_page = new FrontPageFragment1();
        ft.replace(R.id.main_fragment, fgm_front_page);
        ft.commit();
    }

    public void page_jump(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        FrontPageFragment1 fgm_front_page = new FrontPageFragment1();
        PlanFragment fgm_plan_page = new PlanFragment();
        MineFragment fgm_mine_page = new MineFragment();
        MonitoringFragment fgm_spark_page = new MonitoringFragment();


        switch (view.getId()){
            case R.id.front_page:
                ft.replace(R.id.main_fragment, fgm_front_page);
                break;
            case R.id.plan:
                ft.replace(R.id.main_fragment, fgm_plan_page);
                break;
            case R.id.mine:
                ft.replace(R.id.main_fragment, fgm_mine_page);
                break;
            case R.id.spark:
                ft.replace(R.id.main_fragment, fgm_spark_page);
                break;
        }
        ft.commit();
    }

}