package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.DetailsPageActivity;
import com.example.travelor.R;
import com.example.travelor.bean.Dish;

import java.util.List;

public class DishCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dish> mDishList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public DishCardAdapter(Context context, List<Dish> dishList) {
        this.mContext = context;
        this.mDishList = dishList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.attraction_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((MyViewHolder)holder, position);
    }

    private void bindViewHolder(MyViewHolder holder, int position) {
        Dish dish = mDishList.get(position);
        holder.mDishName.setText(dish.getName());
        holder.mDishPrice.setText(dish.getPrice());
        holder.mDishLocation.setText(dish.getLocation());

        // 动态设置图片
        holder.mDishImage.setImageResource(getResourceId(dish.getImages()));

        holder.mCardContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void refreshData(List<Dish> dishList) {
        this.mDishList = dishList;
        notifyDataSetChanged();
    }

    // 用于保存 list_item.xml 中的视图组件
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mDishImage;
        TextView mDishName;
        TextView mDishPrice;
        TextView mDishLocation;
        ViewGroup mCardContain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mDishImage = itemView.findViewById(R.id.attraction_image);
            this.mDishName = itemView.findViewById(R.id.attraction_name);
            this.mDishPrice = itemView.findViewById(R.id.rank );
            this.mDishLocation = itemView.findViewById(R.id.location);
            this.mCardContain = itemView.findViewById(R.id.card_contain);
        }
    }
}
