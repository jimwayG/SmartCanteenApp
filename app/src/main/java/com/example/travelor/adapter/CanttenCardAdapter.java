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
import com.example.travelor.bean.Canteen;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CanttenCardAdapter extends RecyclerView.Adapter<CanttenCardAdapter.CanttenCardViewHolder> {

    private final List<Canteen> canteenList;
    private final Context context;

    public CanttenCardAdapter(Context context, List<Canteen> canttenList) {
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
        Canteen canteen = canteenList.get(position);
        setTexts(holder, position, canteen);
        setCanteenImage(holder, canteen);
        addListeners(holder, canteen);
    }

    private static void setTexts(@NonNull CanttenCardViewHolder holder, int position, Canteen canteen) {
        holder.canteenIndex.setText(String.valueOf(position + 1));
        holder.canteenName.setText(canteen.getName());
        holder.flowState.setText("流量状况: " + canteen.getState());
    }

    private void addListeners(@NonNull CanttenCardViewHolder holder, Canteen canteen) {
        addListener2CanteenCard(holder, canteen);
        addListener2LocationIcon(holder, canteen);
    }

    private void addListener2LocationIcon(@NonNull CanttenCardViewHolder holder, Canteen canteen) {
        holder.locationIcon.setOnClickListener(locationIcon -> {
            Intent intent = new Intent(context, GaodeMapActivity.class);
            intent.putExtra("canteenItem", canteen);
            context.startActivity(intent);
        });
    }

    private void addListener2CanteenCard(@NonNull CanttenCardViewHolder holder, Canteen canteen) {
        holder.cardBody.setOnClickListener(canteenCard -> {
            Intent intent = new Intent(context, MonitoringActivity.class);
            intent.putExtra("canteenItem", canteen);
            context.startActivity(intent);
        });
    }

    private static void setCanteenImage(@NonNull CanttenCardViewHolder holder, Canteen canteen) {
        if (canteen.getCanteenImage() != null) {
            try {
                holder.canteenImage.setBackground(canteen.getCanteenImage());
            } catch (Resources.NotFoundException exception) {
                Log.e("CanttenCardAdapter#onBindViewHolder", "Image resource not found");
            }
        } else if (canteen.getImageUrl() != null && !canteen.getImageUrl().isEmpty()) {
            Picasso.get().load(canteen.getImageUrl()).into(holder.canteenImage);
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
