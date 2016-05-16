package com.egorchepiga.billingv2;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.POJO.Post;
import com.egorchepiga.billingv2.POJO.StockService;
import com.egorchepiga.billingv2.adapter.SAdapter;
import com.egorchepiga.billingv2.adapter.items.Service;
import com.egorchepiga.billingv2.fragment_dialog.AddDialog;
import com.egorchepiga.billingv2.util.IabHelper;
import com.egorchepiga.billingv2.util.IabResult;
import com.egorchepiga.billingv2.util.Inventory;
import com.egorchepiga.billingv2.util.Purchase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 14.03.2016.
 */
public class AddService extends Fragment implements SAdapter.ClickListener2{
    String name,id,serviceStack,period,cost,ontimeString,offtimeString,info_id,info_balance;
    public static String ITEM_SKU,positionStr;
    public static int positionInt;
    String[] colors,servicescoststr,servicesidstr,servicesperiodstr;
    Calendar contime;
    double balance;
    ProgressDialog pDialog;
    ListView list;
    IabHelper mHelper;

    List<String> servicesinfo = new ArrayList<>();
    List<String> servicesid = new ArrayList<>();
    List<String> servicescost = new ArrayList<>();
    List<String> servicesofftime = new ArrayList<>();
    List<String> servicesontime = new ArrayList <>();
    List<String> servicesperiod = new ArrayList<>();
    List<String> services = new ArrayList<>();
    View v;
    DialogFragment dialogFragment;
    private List<Service> servicess;
    private RecyclerView rv3;

    private String TAG = MainScreen.class.getSimpleName();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void itemClicked(View view, int position) {
        Log.d("asdasdasdasd","asdasdasdasd");
        positionStr = String.valueOf(position + 1);
        positionInt = position+1;

        contime = new GregorianCalendar();
        ontimeString = df.format(contime.getTime());

        contime.add(Calendar.DAY_OF_YEAR, Integer.parseInt(servicesperiodstr[position]));
        offtimeString = df.format(contime.getTime());

        if (Double.parseDouble(String.valueOf(balance)) < Double.parseDouble(servicescoststr[positionInt-1])) {

            ITEM_SKU = String.valueOf(positionStr);
            //ITEM_SKU = "android.test.purchased";

            StupidLogic();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("serviceCost",servicescoststr[positionInt-1]);
            bundle.putString("Balance",String.valueOf(balance));
            bundle.putString("positionStr",positionStr);
            bundle.putString("ontimeString",ontimeString);
            bundle.putString("offtimeString",offtimeString);
            bundle.putString("info_id",info_id);
            bundle.putString("info_username",((TextView)getActivity().findViewById(R.id.info_username_nav)).getText().toString());

            AddService addService = new AddService();
            dialogFragment.setTargetFragment(addService,1);
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), "dialogFragment");
    }}

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.add_service, null);
        dialogFragment = new AddDialog();
        super.onCreate(savedInstanceState);

        pDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading dictionary file...");
        pDialog.setCancelable(false);
        showProgressDialog();

        Bundle bundle = this.getArguments();
        info_id = bundle.getString("info_id");
        info_balance = bundle.getString("info_balance");
        balance= Double.parseDouble(info_balance);

        rv3=(RecyclerView) v.findViewById(R.id.rv3);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv3.setLayoutManager(llm);
        rv3.setHasFixedSize(true);

        ServicesInfo();

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjCkfEVL0eLVe4iLhst5tXHTij548GVCV/UXms+u5oulNp0ZubKyN5vD+R3wYVxH1wuYgHGQ+idhWp5juCM9u4DK/OGPnNLtICSQQh3c3pqbwoZ8xGGsMYLXMmuxrDYGdKRENdOtOcOcLfwVcbZrLOJRIsVMqpdFBAaInEq9V/dN5nqU9XnI04WfdZ+EPxioR+ohA7Rv952DhlBH85PUGVLG0VMDY5Bbkf1OtyTg29FAcvhMAZZS0C3evpok6rhRgj42fKu1MEbVHvZkYWt7orV2eAvADNImEV9fbDwaSIaQ6UA43IEmGayi4iyQpVzWWQWI2f5iqAptfTaBxHszLGwIDAQAB";

        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d("Billing", "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d("Billing", "In-app Billing is set up OK");
                                           }
                                       }
                                   });

        return v;
}

    private void ServicesInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<List<StockService>> call = service.getUsluga();
        call.enqueue(new Callback<List<StockService>>() {
            @Override
            public void onResponse(Call<List<StockService>> call, retrofit2.Response<List<StockService>> response) {
                            services.clear();
                if (response.isSuccessful()) {
                    servicesid.clear();
                    servicesontime.clear();
                    servicesperiod.clear();
                    servicesofftime.clear();
                    servicescost.clear();
                    servicesinfo.clear();

                    List<StockService> services = response.body();
                    servicess = new ArrayList<>();
                    for (int i = 0; i < services.size(); i++) {
                        StockService service = services.get(i);
                        Log.d(TAG, service.getName());
                        id = service.getId();
                        period = service.getPeriod();
                        cost = service.getCost();
                        name = service.getName();

                        serviceStack = "Услуга " + name + " период " + period + " стоимость " + cost;
                        servicesinfo.add(serviceStack);

                        servicesid.add(id);
                        servicesperiod.add(period);
                        servicescost.add(cost);
                        servicess.add(new Service(name,cost,period));
                    }

                    servicescoststr = new String[servicescost.size()];
                    servicescost.toArray(servicescoststr);

                    servicesperiodstr = new String[servicesperiod.size()];
                    servicesperiod.toArray(servicesperiodstr);

                    servicesidstr = new String[servicesid.size()];
                    servicesid.toArray(servicesidstr);

                    colors = new String[services.size()];
                    servicesinfo.toArray(colors);


                    initializeAdapter();

                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<List<StockService>> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
            }
        });

    }


    public void StupidLogic(){
        if (mHelper != null) mHelper.flagEndAsync();
        mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001, mPurchaseFinishedListener, "mytoken");
    }



    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
            }

        }
    };

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {


            if (result.isFailure()) {
                Log.d("Billing"," Such Failure! WOW!");
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        //Приходящий ответ!!!!!--------------------------------------------------------------------------------------------
                        //String URL = "http://cybergenesis.ru/egor/add_service?ontime=" + ontimeString + "&offtime=" + offtimeString + "&user_id=" + info_id + "&service=" + Integer.toString(position + 1) + "&balance=" + String.valueOf(balance);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://cybergenesis.ru/egor/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        APIService service = retrofit.create(APIService.class);
                        Call<Post> call = service.addService(ontimeString, offtimeString, info_id, positionStr, String.valueOf(balance));
                        call.enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {

                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {

                            }
                        });

                        Bundle bundle = new Bundle();
                        bundle.putString("info_username", ((TextView)getActivity().findViewById(R.id.info_username_nav)).getText().toString());
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        MainScreen mainactivity = new MainScreen();
                        ft.replace(R.id.frameLay, mainactivity);
                        mainactivity.setArguments(bundle);
                        ft.commit();

                    } else {
                        Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT ).show();
                        // handle error
                    }
                }
            };
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void initializeAdapter(){
        SAdapter adapter = new SAdapter(servicess);
        adapter.setClickListener2(this);
        rv3.setAdapter(adapter);
    }
}
