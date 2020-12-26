package com.example.checkbox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkbox.R;
import com.example.checkbox.checkbox.checkbox;

import java.util.ArrayList;

public class CheckboxRecyclerAdapter extends RecyclerView.Adapter<CheckboxRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CheckboxRecyclerAdapter";
    private ArrayList<checkbox> mCheckboxes= new ArrayList<>();
    private OnCheckListener mOnCheckListener;

    public CheckboxRecyclerAdapter(ArrayList<checkbox> mCheckboxes,OnCheckListener mOnCheckListener) {
        this.mCheckboxes = mCheckboxes;
        this.mOnCheckListener=mOnCheckListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkbox_list_item,parent,false);
        return new ViewHolder(view,mOnCheckListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.koncnicas.setText(mCheckboxes.get(position).getDatumKonca());
        holder.naslov.setText(mCheckboxes.get(position).getNaslov());
        holder.caszakj.setText((mCheckboxes.get(position).getCas()));
    }

    @Override
    public int getItemCount() {
        return mCheckboxes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView naslov, koncnicas,caszakj;
        private CheckBox check;
        OnCheckListener onCheckListener;

        public ViewHolder(@NonNull View itemView ,OnCheckListener onCheckListener) {
            super(itemView);
            naslov=itemView.findViewById(R.id.checktitle);
            koncnicas=itemView.findViewById(R.id.caskonca);
            check=itemView.findViewById(R.id.checkmark);
            caszakj=itemView.findViewById(R.id.caszaklj);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnCheckListener.onCheckClick(getAdapterPosition());
        }
    }
    public interface OnCheckListener{
        void onCheckClick(int position);
    }
}
