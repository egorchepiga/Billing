package com.egorchepiga.billingv2.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egorchepiga.billingv2.R;
import com.egorchepiga.billingv2.adapter.items.Payment_item;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PaymentViewHolder> {

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {



        CardView cv;
        TextView id,time,sum;

        PaymentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv7);
            id = (TextView)itemView.findViewById(R.id.id_payment_card);
            time = (TextView)itemView.findViewById(R.id.time_payment_card);
            sum = (TextView)itemView.findViewById(R.id.sum_payment_card);


        }
    }

    public List<Payment_item> paymentItems;

    public PayAdapter(List<Payment_item> paymentItems){
        this.paymentItems = paymentItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_card, viewGroup, false);
        PaymentViewHolder pvh = new PaymentViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder repairViewHolder, int i) {
        repairViewHolder.id.setText(paymentItems.get(i).id);
        repairViewHolder.time.setText(paymentItems.get(i).time);
        repairViewHolder.sum.setText(paymentItems.get(i).sum);
    }

    @Override
    public int getItemCount() {
        return paymentItems.size();
    }
}




