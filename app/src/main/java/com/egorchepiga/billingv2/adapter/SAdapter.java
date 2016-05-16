package com.egorchepiga.billingv2.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egorchepiga.billingv2.R;
import com.egorchepiga.billingv2.adapter.items.Service;

import java.util.List;

public class SAdapter extends RecyclerView.Adapter<SAdapter.ServiceViewHolder>  {

    public static SAdapter.ClickListener2 clickListener;

    public static class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            if (clickListener!=null){
                clickListener.itemClicked(v,getPosition());
        }
        }


        CardView cv;
        TextView name,cost,period;

        ServiceViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv3);
            name = (TextView)itemView.findViewById(R.id.id_payment_card);
            cost = (TextView)itemView.findViewById(R.id.costCard);
            period = (TextView)itemView.findViewById(R.id.periodCard);
            itemView.setOnClickListener(this);

        }
    }


    public List<Service> services;

    public SAdapter(List<Service> services){
        this.services = services;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_card, viewGroup, false);
        ServiceViewHolder pvh = new ServiceViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder serviceViewHolder, int i) {
        serviceViewHolder.name.setText(services.get(i).name);
        serviceViewHolder.cost.setText(services.get(i).cost);
        serviceViewHolder.period.setText(services.get(i).period);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void setClickListener2(ClickListener2 clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener2{
        public void itemClicked(View view,int position);
    }

}