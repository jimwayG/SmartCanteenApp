package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.DetailsPageActivity;
import com.example.travelor.R;
import com.example.travelor.bean.Dish;

import com.example.travelor.datebase.DishCltDbOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DishCltAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Dish> mDishList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Boolean checkboxVisible = false;
    private Boolean mChooseAll = false;
    private DishCltDbOpenHelper mDishCltDbOpenHelper;
    private boolean mCancelAll = false;
    private boolean[] checkedPositions; // 跟踪复选框的选中状态

    public DishCltAdapter(Context context, List<Dish> mDish) {
        this.mDishList = mDish;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDishCltDbOpenHelper = new DishCltDbOpenHelper(mContext);
        checkedPositions = new boolean[mDish.size()];
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.attraction_collect_card, parent, false);
        DishCltAdapter.MyViewHolder myViewHolder = new DishCltAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((DishCltAdapter.MyViewHolder) holder, position);
    }

    private void bindViewHolder(DishCltAdapter.MyViewHolder holder, int position) {
        Dish dish = mDishList.get(position);
        holder.mAttrName.setText(dish.getName());
        holder.mAttrLocation.setText(dish.getLocation());
//        holder.mAttrImage.setImageResource(R.drawable.west_lake1); // todo

        // 动态设置图片
        holder.mAttrImage.setImageResource(getResourceId(dish.getImages()));
        String introduce = dish.getIntroduce();
        // 卡片简介不能太长
        holder.mAttrIntroduce.setText("简介：" + introduce.substring(0, Math.min(introduce.length(), 15)));

        if (checkboxVisible) {
            holder.mChoice.setVisibility(View.VISIBLE);
        } else {
            holder.mChoice.setVisibility(View.INVISIBLE);
            checkedPositions[position] = false;
        }

        holder.mChoice.setChecked(checkedPositions[position]);
        holder.mChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedPositions[position] = isChecked; // 更新选中状态
            }
        });

        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsPageActivity.class);
                intent.putExtra("dish", dish);
                mContext.startActivity(intent);
            }
        });
    }

    public int getResourceId(String fileNames) {
        String[] imageNames = fileNames.split("#");
        String resourceName = imageNames[0];
        String resourceType = "drawable";
        String packageName = mContext.getPackageName();
        return mContext.getResources().getIdentifier(resourceName, resourceType, packageName);
    }

    @Override
    public int getItemCount() {
        return mDishList.size();
    }

    public void refreshData(List<Dish>dishList) {
        this.mDishList = dishList;
        for (int i = 0; i < mDishList.size(); i++)
            checkedPositions[i] = false;
        notifyDataSetChanged();
    }

    public void setCheckBoxStatus(Boolean managed) {
        this.checkboxVisible = managed;
        notifyDataSetChanged();
    }

    public void chooseAll() {
        for (int i = 0; i < mDishList.size(); i++)
            checkedPositions[i] = true;
        notifyDataSetChanged();
    }

    public void deleteChosen() {
        List<Dish> newDishList = new ArrayList<>();
        for (int i = 0; i < mDishList.size(); i++) {
            if (checkedPositions[i])
                mDishCltDbOpenHelper.deleteFromDbByName(mDishList.get(i).getName());
            else newDishList.add(mDishList.get(i));
        }
        refreshData(newDishList);
    }

    public void cancelAll() {
        for (int i = 0; i < mDishList.size(); i++)
            checkedPositions[i] = false;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mAttrImage;
        TextView mAttrName;
        TextView mAttrLocation;
        TextView mAttrIntroduce;
        TextView mDetails;
        CheckBox mChoice;
        ViewGroup mCardContain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mAttrImage = itemView.findViewById(R.id.image);
            this.mAttrName = itemView.findViewById(R.id.name);
            this.mAttrLocation = itemView.findViewById(R.id.location);
            this.mAttrIntroduce = itemView.findViewById(R.id.introduce);
            this.mCardContain = itemView.findViewById(R.id.card_contain);
            this.mDetails = itemView.findViewById(R.id.details);
            this.mChoice = itemView.findViewById(R.id.cb_choice);
        }
    }
}