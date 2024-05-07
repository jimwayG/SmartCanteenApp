package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.travelor.GaodeMapActivity;
import com.example.travelor.MonitoringActivity;
import com.example.travelor.R;
import com.example.travelor.bean.CanteenItem;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CanttenCardAdapter extends RecyclerView.Adapter<CanttenCardAdapter.CanttenCardViewHolder> {

    private final List<CanteenItem> canteenList;
    private final Context context;

    public CanttenCardAdapter(Context context, List<CanteenItem> canttenList) {
        this.context = context;
        this.canteenList = canttenList;
    }

    @NonNull
    @Override
    public CanttenCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.canteen_card, parent, false);
        return new CanttenCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanttenCardViewHolder holder, int position) {
        CanteenItem canteenItem = canteenList.get(position);
        setTexts(holder, position, canteenItem);
        setCanteenImage(holder, canteenItem);
        addListeners(holder, canteenItem);
    }

    private static void setTexts(@NonNull CanttenCardViewHolder holder, int position, CanteenItem canteenItem) {
        holder.canteenIndex.setText(String.valueOf(position + 1));
        holder.canteenName.setText(canteenItem.getCanteenName());
        holder.flowState.setText("流量状况: " + canteenItem.getFlowState());
    }

    private void addListeners(@NonNull CanttenCardViewHolder holder, CanteenItem canteenItem) {
        addListener2CanteenCard(holder, canteenItem);
        addListener2LocationIcon(holder, canteenItem);
    }

    private void addListener2LocationIcon(@NonNull CanttenCardViewHolder holder, CanteenItem canteenItem) {
        holder.locationIcon.setOnClickListener(locationIcon -> {
            Intent intent = new Intent(context, GaodeMapActivity.class);
            intent.putExtra("canteenItem", canteenItem);
            context.startActivity(intent);
        });
    }

    private void addListener2CanteenCard(@NonNull CanttenCardViewHolder holder, CanteenItem canteenItem) {
        holder.cardBody.setOnClickListener(canteenCard -> {
            Intent intent = new Intent(context, MonitoringActivity.class);
            intent.putExtra("canteenItem", canteenItem);
            context.startActivity(intent);
        });
    }

    private static void setCanteenImage(@NonNull CanttenCardViewHolder holder, CanteenItem canteenItem) {
        try {
            holder.canteenImage.setBackground(canteenItem.getCanteenImage());
        } catch (Resources.NotFoundException exception) {
            Log.e("CanttenCardAdapter#onBindViewHolder", "Image resource not found");
        }
    }

    @Override
    public int getItemCount() {
        return canteenList.size();
    }

    public static class CanttenCardViewHolder extends RecyclerView.ViewHolder {

        TextView canteenIndex;
        TextView canteenName;
        TextView flowState;
        ImageView canteenImage;
        View cardBody;
        View locationIcon;

        public CanttenCardViewHolder(@NonNull View itemView) {
            super(itemView);
            canteenIndex = itemView.findViewById(R.id.cantten_index);
            canteenName = itemView.findViewById(R.id.canteen_name);
            flowState = itemView.findViewById(R.id.flow_state);
            canteenImage = itemView.findViewById(R.id.cantten_image);
            cardBody = itemView.findViewById(R.id.canteen_card);
            locationIcon = itemView.findViewById(R.id.location_icon);
        }
    }
}
