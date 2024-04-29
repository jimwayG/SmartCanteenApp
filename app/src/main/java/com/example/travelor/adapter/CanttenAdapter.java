package com.example.travelor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.travelor.R;
import com.example.travelor.bean.CanttenItem;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CanttenAdapter extends RecyclerView.Adapter<CanttenAdapter.CanttenViewHolder> {

    private List<CanttenItem> canttenList;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public CanttenAdapter(Context context, List<CanttenItem> canttenList) {
        this.context = context;
        this.canttenList = canttenList;
    }

    @NonNull
    @Override
    public CanttenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_canteen, parent, false);
        return new CanttenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanttenViewHolder holder, int position) {
        CanttenItem canttenItem = canttenList.get(position);

        holder.canttenIndex.setText(String.valueOf(position + 1));
        holder.mainCantten.setText(canttenItem.getMainCantten());
        holder.flowState.setText(canttenItem.getFlowState());
        holder.canttenImage.setImageResource(canttenItem.getCanttenImage());
    }

    @Override
    public int getItemCount() {
        return canttenList.size();
    }

    public static class CanttenViewHolder extends RecyclerView.ViewHolder {
        TextView canttenIndex;
        TextView mainCantten;
        TextView flowState;
        ImageView locationIcon;
        ImageView canttenImage;

        public CanttenViewHolder(@NonNull View itemView) {
            super(itemView);
            canttenIndex = itemView.findViewById(R.id.cantten_index);
            mainCantten = itemView.findViewById(R.id.main_cantten);
            flowState = itemView.findViewById(R.id.flow_state);
            canttenImage = itemView.findViewById(R.id.cantten_image);
        }
    }
}
