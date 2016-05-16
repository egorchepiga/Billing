package com.egorchepiga.billingv2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.adapter.RAdapter;
import com.egorchepiga.billingv2.adapter.items.Repair_item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 21.03.2016.
 */
public class Repair extends Fragment implements RAdapter.ClickListener{

    ProgressDialog pDialog;
    String status,id,serviceStack,phone,user_id,telephone,type,time;
    String[] colors,id_repair;
    List<String> servicesinfo = new ArrayList<>();
    List<String> servicesid = new ArrayList<>();
    List<String> servicescost = new ArrayList<>();
    List<String> servicesofftime = new ArrayList<>();
    List<String> servicesontime = new ArrayList <>();
    List<String> servicesperiod = new ArrayList<>();
    List<String> services = new ArrayList<>();
    public List<Repair_item> repairItems;
    private RecyclerView rv_repair;
    View v;



    /*@Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        MainScreen mainScreen = new MainScreen();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mainScreen = new MainScreen();
            Bundle bundle = new Bundle();
            bundle.putString("info_username", ((TextView) getActivity().findViewById(R.id.info_username_nav)).getText().toString());
            ft.addToBackStack(null);
        mainScreen.setArguments(bundle);
        ft.replace(R.id.frameLay, mainScreen);
        Log.d("asdasdasd","asdasd");
        ft.commit();
    }*/

    @Override
    public void itemClicked(View view, int position) {


        showProgressDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<com.egorchepiga.billingv2.POJO.Repair> call = service.getRepair(id_repair[position]);
        call.enqueue(new Callback<com.egorchepiga.billingv2.POJO.Repair>() {
            @Override
            public void onResponse(Call<com.egorchepiga.billingv2.POJO.Repair> call, retrofit2.Response<com.egorchepiga.billingv2.POJO.Repair> response) {
                com.egorchepiga.billingv2.POJO.Repair repair = response.body();

                Bundle bundle = new Bundle();
                bundle.putString("phone", repair.getTelephone());
                bundle.putString("info_id", user_id);
                bundle.putString("description", repair.getDescription());
                bundle.putString("id", repair.getId());
                bundle.putString("type", repair.getType());
                bundle.putString("time", repair.getTime());

                hideProgressDialog();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                AddRepair addRepair = new AddRepair();
                ft.replace(R.id.frameLay, addRepair);
                addRepair.setArguments(bundle);
                ft.addToBackStack(null);
                ft.commit();

            }

            @Override
            public void onFailure(Call<com.egorchepiga.billingv2.POJO.Repair> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.repair_lay, null);
        pDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading dictionary file...");
        pDialog.setCancelable(false);
        showProgressDialog();

        Bundle bundle = this.getArguments();
        user_id = bundle.getString("info_id");
        phone = bundle.getString("phone");

        Button add = (Button) v.findViewById(R.id.button11);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("info_id", user_id);
                bundle.putString("phone", phone);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                AddRepair addRepair = new AddRepair();
                Repair repair = new Repair();
                ft.replace(R.id.frameLay, addRepair);
                addRepair.setArguments(bundle);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        rv_repair=(RecyclerView) v.findViewById(R.id.rv6);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv_repair.setLayoutManager(llm);
        rv_repair.setHasFixedSize(true);
        repairItems = new ArrayList<>();

        RepairsInfo();

        return v;
    }

    private void initializeAdapter(){
        RAdapter adapter = new RAdapter(repairItems);
        adapter.setClickListener3(this);
        rv_repair.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        hideProgressDialog();
    }


    private void RepairsInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<List<com.egorchepiga.billingv2.POJO.Repair>> call = service.getRepairs(user_id);
        call.enqueue(new Callback<List<com.egorchepiga.billingv2.POJO.Repair>>() {
            @Override
            public void onResponse(Call<List<com.egorchepiga.billingv2.POJO.Repair>> call, retrofit2.Response<List<com.egorchepiga.billingv2.POJO.Repair>> response) {
                services.clear();
                if (response.isSuccessful()) {
                    servicesid.clear();
                    servicesontime.clear();
                    servicesperiod.clear();
                    servicesofftime.clear();
                    servicescost.clear();
                    servicesinfo.clear();

                    List<com.egorchepiga.billingv2.POJO.Repair> services = response.body();
                    for (int i = 0; i < services.size(); i++) {
                        com.egorchepiga.billingv2.POJO.Repair service = services.get(i);
                        id = service.getId();
                        status = service.getStatus();
                        telephone = service.getTelephone();
                        type = service.getType();
                        time = service.getTime();


                        repairItems.add(new Repair_item(id,status,type,time,phone));
                        servicesid.add(id);
                        serviceStack = "Ремонт №" + id + " " +type+ " назначен на: " + time + " статуc ремонта: "+status +" Телефон: " + telephone;
                        servicesinfo.add(serviceStack);
                    }

                    id_repair = new String[servicesid.size()];
                    servicesid.toArray(id_repair);

                    colors = new String[services.size()];
                    servicesinfo.toArray(colors);

                    initializeAdapter();
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<List<com.egorchepiga.billingv2.POJO.Repair>> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT ).show();
                hideProgressDialog();

            }
        });

    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }
}
