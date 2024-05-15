package com.example.travelor.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.travelor.DishCollectActivity;
import com.example.travelor.LoginActivity;
import com.example.travelor.R;

public class MineFragment extends Fragment {


    private Button btnLogout;
    private ViewGroup vgCollect;
    private ViewGroup canteen_collect;
    private TextView vipView;
    private Button btnLogin;
    private boolean isLoggedIn = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        btnLogin = rootView.findViewById(R.id.login);
        btnLogout = rootView.findViewById(R.id.logout);
        vgCollect = rootView.findViewById(R.id.mine_collect);
        canteen_collect = rootView.findViewById(R.id.canteen_collect);
        initVipView(rootView);

        // 从 SharedPreferences 中恢复登录状态
        updateButtonVisibility();

        // 设置登录按钮的点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置已登录状态为 true
                isLoggedIn = true;
                // 将登录状态保存到 SharedPreferences 中
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_pref", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", isLoggedIn);
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // 设置登出按钮的点击事件
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置已登录状态为 false
                isLoggedIn = false;
                // 将登录状态保存到 SharedPreferences 中
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_pref", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", isLoggedIn);
                editor.apply();

                // 更新按钮可见性
                updateButtonVisibility();
                // 显示提示消息
                Toast.makeText(getActivity(), "已退出登录", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置收藏按钮的点击事件
        vgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果已登录，则跳转到收藏界面
                if (isLoggedIn) {
                    Intent intent = new Intent(getActivity(), DishCollectActivity.class);
                    startActivity(intent);
                } else {
                    // 如果未登录，则提示用户登录
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void initVipView(View rootView) {
        vipView = rootView.findViewById(R.id.vip_text_view);
        vipView.setOnClickListener(view -> {
            VipDialogFragment vipDialogFragment = new VipDialogFragment();
            vipDialogFragment.show(getParentFragmentManager(), "vip");
        });
    }

    // 根据登录状态设置按钮可见性
    private void updateButtonVisibility() {
        // 获取 SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_pref", getActivity().MODE_PRIVATE);
        // 从 SharedPreferences 中获取登录状态，默认为 false
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // 如果已登录，显示退出按钮和收藏按钮，隐藏登录按钮
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            vgCollect.setVisibility(View.VISIBLE);
            canteen_collect.setVisibility(View.VISIBLE);
        } else {
            // 如果未登录，显示登录按钮，隐藏退出按钮和收藏按钮
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            vgCollect.setVisibility(View.GONE);
            canteen_collect.setVisibility(View.GONE);
        }

    }

    public static class VipDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction.
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // Get the layout inflater.
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.dialog_vip_pay, null))
                    .setTitle("成为会员")
                    .setPositiveButton("支付", (dialog, id) -> {
                        // START THE GAME!
                    })
                    .setNegativeButton("取消", (dialog, id) -> {
                        // User cancels the dialog.
                    });
            // Create the AlertDialog object and return it.
            return builder.create();
        }
    }

}