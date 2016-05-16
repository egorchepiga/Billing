package com.egorchepiga.billingv2.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egorchepiga.billingv2.R;
import com.egorchepiga.billingv2.adapter.items.Repair_item;

import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.RepairViewHolder> {
    private static ClickListener clickListener;

    public interface ClickListener{
        public void itemClicked(View view,int position);

    }

    public void setClickListener3(ClickListener clickListener){
        this.clickListener = clickListener;

    }

    public static class RepairViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            if (clickListener!=null){
                clickListener.itemClicked(v,getPosition());
            }
        }

        CardView cv;
        TextView id,type,status,time,phone;
        TextView personAge;
        ImageView personPhoto;

        RepairViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv5);
            id = (TextView)itemView.findViewById(R.id.id_payment_card);
            type = (TextView)itemView.findViewById(R.id.type_repair_card);
            status = (TextView)itemView.findViewById(R.id.status_repair_card);
            time = (TextView)itemView.findViewById(R.id.time_repair_card);
            phone = (TextView)itemView.findViewById(R.id.phone_repair_card);
            itemView.setOnClickListener(this);


        }
    }

    public List<Repair_item> repairItems;

    public RAdapter(List<Repair_item> repairItems){
        this.repairItems = repairItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RepairViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repair_card, viewGroup, false);
        RepairViewHolder pvh = new RepairViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(RepairViewHolder repairViewHolder, int i) {
        repairViewHolder.id.setText(repairItems.get(i).id);
        repairViewHolder.status.setText(repairItems.get(i).status);
        repairViewHolder.type.setText(repairItems.get(i).type);
        repairViewHolder.time.setText(repairItems.get(i).time);
        repairViewHolder.phone.setText(repairItems.get(i).phone);
    }

    @Override
    public int getItemCount() {
        return repairItems.size();
    }
}




