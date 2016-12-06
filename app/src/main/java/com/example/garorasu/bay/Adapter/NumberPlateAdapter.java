package com.example.garorasu.bay.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garorasu.bay.Model.Vehicle;
import com.example.garorasu.bay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by garorasu on 6/12/16.
 */

public class NumberPlateAdapter extends RecyclerView.Adapter<NumberPlateAdapter.ViewHolder>{
    private List<Vehicle> mDataset;
    private OnItemClickListener mItemClickListener;

    public NumberPlateAdapter(List<Vehicle> myDataset){
        this.mDataset = myDataset;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_plate_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.numberPlate.setText(mDataset.get(position).getVid());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int uid);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView numberPlate;
        public ViewHolder(final View itemView) {
            super(itemView);
            numberPlate = (TextView) itemView.findViewById(R.id.text_number_plate);
        }

        public void onClick(View view) {
            System.out.println("Adapter item clicked : "+Integer.parseInt(mDataset.get(getAdapterPosition()).getUid()));
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view,Integer.parseInt(mDataset.get(getAdapterPosition()).getUid()));
            }
        }
    }
}
