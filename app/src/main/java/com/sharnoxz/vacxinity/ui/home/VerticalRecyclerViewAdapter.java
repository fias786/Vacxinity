package com.sharnoxz.vacxinity.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sharnoxz.vacxinity.R;

import java.util.ArrayList;


public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<VerticalData> data;

    public VerticalRecyclerViewAdapter(Context context, ArrayList<VerticalData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_layout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.topicName.setText(data.get(position).getTitle());
        holder.horizontalRecyclerView.setAdapter(new HorizontalRecyclerViewAdapter(context,data.get(position).getHorizontalData()));
        holder.horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.horizontalRecyclerView.setHasFixedSize(true);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RecyclerView horizontalRecyclerView;
        TextView topicName,appDetails;
        ImageView appIcon;
        ImageButton topicExpandButton;
        RelativeLayout clickLayout,DetailsOfApp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
            topicName = itemView.findViewById(R.id.topicName);
            topicExpandButton = itemView.findViewById(R.id.topicExpandButton);
            clickLayout = itemView.findViewById(R.id.topicRelativeLayout);
        }
    }
}
