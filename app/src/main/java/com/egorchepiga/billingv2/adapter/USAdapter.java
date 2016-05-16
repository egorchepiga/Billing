package com.egorchepiga.billingv2.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egorchepiga.billingv2.R;
import com.egorchepiga.billingv2.adapter.items.UserService;

import java.util.List;

public class USAdapter extends RecyclerView.Adapter<USAdapter.PersonViewHolder> {
    private static ClickListener clickListener;

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView name,cost,ontime,offtime,period;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv2);
            name = (TextView)itemView.findViewById(R.id.id_payment_card);
            cost = (TextView)itemView.findViewById(R.id.costCard);
            ontime = (TextView)itemView.findViewById(R.id.ontimeCard);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (clickListener!=null){
                clickListener.itemClicked(v,getPosition());
            }
        }
    }

   public List<UserService> userServices;

   public USAdapter(List<UserService> userServices){
        this.userServices = userServices;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_service_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.name.setText(userServices.get(i).name);
        personViewHolder.cost.setText(userServices.get(i).cost);
        personViewHolder.ontime.setText(userServices.get(i).ontime + " по: "+ userServices.get(i).offtime);
       // personViewHolder.offtime.setText(userServices.get(i).offtime);
    }

    @Override
    public int getItemCount() {
        return userServices.size();
    }

    public interface ClickListener{
        public void itemClicked(View view,int position);

    }
}