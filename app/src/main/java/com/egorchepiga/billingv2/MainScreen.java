package com.egorchepiga.billingv2;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.POJO.User;
import com.egorchepiga.billingv2.adapter.items.UserService;
import com.egorchepiga.billingv2.adapter.USAdapter;
import com.egorchepiga.billingv2.fragment_dialog.DateTimeDialog;
import com.egorchepiga.billingv2.fragment_dialog.DeleteDialog;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainScreen extends Fragment implements USAdapter.ClickListener {



    public static int positionInt;
    public static String Returned,Balance;
    Date ontimedate,offtimedate;
    String name,ontime,offtime,id,serviceStack,period,cost,phone;
    Button btn;
    public ListView sList;
    String[] colors,servicescoststr,servicesidstr,servicesperiodstr,ontimestr,offtimestr;
    List<String> servicesinfo = new ArrayList<>();
    List<String> servicesid = new ArrayList<>();
    List<String> servicescost = new ArrayList<>();
    List<String> servicesofftime = new ArrayList<>();
    List<String> servicesontime = new ArrayList<>();
    List<String> servicesperiod = new ArrayList<>();
    String clone_info_id,info_id,info_balance,info_username,info_password;
    private String TAG = MainScreen.class.getSimpleName();
    ProgressDialog pDialog ;
    TextView usernametxt,passwordtxt,balancetxt;
    SharedPreferences sPref;
    DialogFragment dialogFragment;
    static View v;
    private List<UserService> userServices;
    private RecyclerView rv;





    @Override
    public void onResume() {
        super.onResume();
        someEventListener.HomeSelection();

    }


    DateTimeDialog.onSomeEventListener someEventListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (DateTimeDialog.onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        someEventListener.DrawerEnabled(true);
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.mainscreen_lay, null);
        dialogFragment = new DeleteDialog();
        usernametxt = (TextView) v.findViewById(R.id.usernametxt);
        passwordtxt = (TextView) v.findViewById(R.id.passwordtxt);
        balancetxt = (TextView) v.findViewById(R.id.balancetxt);
        balancetxt.setTextColor(Color.BLACK);


        pDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading dictionary file...");
        pDialog.setCancelable(false);
        showProgressDialog();

        Bundle bundle = this.getArguments();
        info_username = bundle.getString("info_username");

        rv=(RecyclerView) v.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        GetUser();

        return v;
    }

    @Override
    public void itemClicked(View view, int position) {
        positionInt = position;
        Calendar contime = new GregorianCalendar();
        contime = Calendar.getInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            ontimedate = contime.getTime();
            offtimedate = format.parse(offtimestr[positionInt]);
        } catch (ParseException e) {
            Log.d("Parsing Eroor", "");
        }

        Long diff = ontimedate.getTime() - offtimedate.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(diff);
        Double returned = Long.valueOf(days).doubleValue() * (Double.parseDouble(servicescoststr[positionInt]) / Double.parseDouble(servicesperiodstr[positionInt]) * -1);
        Double balance = Double.parseDouble(info_balance) + returned;
        Balance = new DecimalFormat("#0.00").format(balance);
        Returned = new DecimalFormat("#0.00").format(returned);

        Bundle bundle = new Bundle();
        bundle.putString("Returned", Returned);
        bundle.putString("Balance", Balance);
        bundle.putString("service_id", servicesidstr[position]);
        bundle.putString("info_id", info_id);
        bundle.putString("info_username", info_username);

        MainScreen mainScreen = new MainScreen();
        dialogFragment.setTargetFragment(mainScreen, 1);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "dialogFragment");

    }


    private void initializeAdapter(){
        USAdapter adapter = new USAdapter(userServices);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);
    }



    public void GetUserServices(){

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit2.create(APIService.class);

        Call<List<com.egorchepiga.billingv2.POJO.UserService>> call = service.getAllServices(info_username);

        call.enqueue(new Callback<List<com.egorchepiga.billingv2.POJO.UserService>>() {
            @Override
            public void onResponse(Call<List<com.egorchepiga.billingv2.POJO.UserService>> call, retrofit2.Response<List<com.egorchepiga.billingv2.POJO.UserService>> response2) {
                if (response2.isSuccessful()) {
                    servicesid.clear();
                            servicesontime.clear();
                            servicesperiod.clear();
                            servicesofftime.clear();
                            servicescost.clear();
                    servicesinfo.clear();

                    List<com.egorchepiga.billingv2.POJO.UserService> services = response2.body();
                    userServices = new ArrayList<>();
                    for (int i = 0; i < services.size(); i++) {
                        com.egorchepiga.billingv2.POJO.UserService service = services.get(i);
                        Log.d(TAG, service.getName());
                        id = service.getId();
                        period = service.getPeriod();
                        cost = service.getCost();
                        name = service.getName();
                        ontime = service.getOntime();
                        offtime = service.getOfftime();

                        serviceStack = "Услуга " + name + " действует c: " + ontime + " до: " + offtime;
                        servicesinfo.add(serviceStack);

                        servicesid.add(id);
                        servicesontime.add(ontime);
                        servicesofftime.add(offtime);
                        servicesperiod.add(period);
                        servicescost.add(cost);

                        userServices.add(new UserService(name,cost,ontime,offtime));
                    }
                    initializeAdapter();
                    ontimestr = new String[servicesontime.size()];
                    servicesontime.toArray(ontimestr);

                    offtimestr = new String[servicesofftime.size()];
                    servicesofftime.toArray(offtimestr);

                    servicescoststr = new String[servicescost.size()];
                    servicescost.toArray(servicescoststr);

                    servicesperiodstr = new String[servicesperiod.size()];
                    servicesperiod.toArray(servicesperiodstr);

                    servicesidstr = new String[servicesid.size()];
                    servicesid.toArray(servicesidstr);

                    colors = new String[services.size()];
                    servicesinfo.toArray(colors);

                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<List<com.egorchepiga.billingv2.POJO.UserService>> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT ).show();
                hideProgressDialog();
            }
        });
    }

    public void GetUser(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<User> call = service.getUserInfo(info_username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                phone = response.body().getTelephone();
                info_username = response.body().getUsername();
                info_id = response.body().getId();
                info_balance = response.body().getBalance();
                info_password = response.body().getPassword();
                usernametxt.setText(info_username);
                passwordtxt.setText(info_password);
                balancetxt.setText(info_balance);

                ((TextView)getActivity().findViewById(R.id.info_id_nav)).setText(info_id);
                ((TextView)getActivity().findViewById(R.id.info_balance_nav)).setText(info_balance);
                ((TextView)getActivity().findViewById(R.id.phone_nav)).setText(phone);
                ((TextView)getActivity().findViewById(R.id.password_nav)).setText(info_password);
                GetUserServices();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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
