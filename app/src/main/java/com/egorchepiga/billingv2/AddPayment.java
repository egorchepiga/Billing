package com.egorchepiga.billingv2;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.POJO.Payment;
import com.egorchepiga.billingv2.POJO.Post;
import com.egorchepiga.billingv2.adapter.PayAdapter;
import com.egorchepiga.billingv2.adapter.items.Payment_item;
import com.egorchepiga.billingv2.util.IabHelper;
import com.egorchepiga.billingv2.util.IabResult;
import com.egorchepiga.billingv2.util.Inventory;
import com.egorchepiga.billingv2.util.Purchase;

import java.text.DateFormat;
import java.text.DecimalFormat;
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
public class AddPayment extends Fragment{

    Button rur50,rur100,rur200,rur500;
    public static String ITEM_SKU,money;
    IabHelper mHelper;
    private ProgressDialog pDialog;
    String user_id,username,balance,sum,ontimeString;
    EditText sumedit;
    TextView usernameedit;
    Calendar contime;
    ListView list2;
    String[] colors,servicescoststr,servicesidstr,servicesperiodstr,ontimestr,offtimestr;
    List<String> servicesinfo = new ArrayList<>();
    List<String> servicesid = new ArrayList<>();
    List<String> servicescost = new ArrayList<>();
    List<String> servicesofftime = new ArrayList<>();
    List<String> servicesontime = new ArrayList <>();
    List<String> servicesperiod = new ArrayList<>();
    List<String> services = new ArrayList<>();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    RecyclerView rv;
    private List<Payment_item> paymentsInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View v = localInflater.inflate(R.layout.payment_lay, null);
        //list2 = (ListView) v.findViewById(R.id.list2);

        rv=(RecyclerView) v.findViewById(R.id.rv7);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        rur50 = (Button) v.findViewById(R.id.rur50);
        rur50.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                money = "50";
                //ITEM_SKU = "android.test.purchased";
                ITEM_SKU ="50rur";
                if (mHelper != null) mHelper.flagEndAsync();
                mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        rur100 = (Button) v.findViewById(R.id.rur100);
        rur100.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                money = "100";
                //ITEM_SKU = "android.test.purchased";
                ITEM_SKU ="100rur";
                if (mHelper != null) mHelper.flagEndAsync();
                mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        rur200 = (Button) v.findViewById(R.id.rur200);
        rur200.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                money = "200";
                //ITEM_SKU = "android.test.purchased";
                ITEM_SKU ="200rur";
                if (mHelper != null) mHelper.flagEndAsync();
                mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        rur500 = (Button) v.findViewById(R.id.rur500);
        rur500.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // money = "500";
                ITEM_SKU = "android.test.purchased";
                //ITEM_SKU ="500rur";
                if (mHelper != null) mHelper.flagEndAsync();
                mHelper.launchPurchaseFlow(getActivity(), ITEM_SKU, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });



        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        Bundle bundle = this.getArguments();
        user_id = bundle.getString("info_id");
        username = bundle.getString("info_username");
        balance = bundle.getString("info_balance");

        contime = new GregorianCalendar();
        ontimeString = formatter.format(contime.getTime());

        PaymentsInfo();

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjCkfEVL0eLVe4iLhst5tXHTij548GVCV/UXms+u5oulNp0ZubKyN5vD+R3wYVxH1wuYgHGQ+idhWp5juCM9u4DK/OGPnNLtICSQQh3c3pqbwoZ8xGGsMYLXMmuxrDYGdKRENdOtOcOcLfwVcbZrLOJRIsVMqpdFBAaInEq9V/dN5nqU9XnI04WfdZ+EPxioR+ohA7Rv952DhlBH85PUGVLG0VMDY5Bbkf1OtyTg29FAcvhMAZZS0C3evpok6rhRgj42fKu1MEbVHvZkYWt7orV2eAvADNImEV9fbDwaSIaQ6UA43IEmGayi4iyQpVzWWQWI2f5iqAptfTaBxHszLGwIDAQAB";

        mHelper = new IabHelper(getActivity(), base64EncodedPublicKey);

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (!result.isSuccess()) {
                                               Log.d("Google Billing", "In-app Billing setup failed: " +
                                                       result);
                                           } else {
                                               Log.d("Google Billing", "In-app Billing is set up OK");
                                           }
                                       }
                                   });
    return v;
    }


    private void initializeAdapter(){
        PayAdapter adapter = new PayAdapter(paymentsInfo);
        rv.setAdapter(adapter);
    }

    private void AddPayment() {

        showProgressDialog();
        sum = sumedit.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Double balancedouble,sumdouble;
            balancedouble = Double.parseDouble(balance);
            sumdouble = Double.parseDouble(money);
        balancedouble = balancedouble+sumdouble;
        String Balance = new DecimalFormat("#0.00").format(balancedouble);

        APIService service = retrofit.create(APIService.class);
        Call<Post> call = service.addPayment(user_id, money,Balance,ontimeString);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                Bundle bundle = new Bundle();
                bundle.putString("info_username", username);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                MainScreen mainactivity = new MainScreen();
                ft.replace(R.id.frameLay, mainactivity);
                mainactivity.setArguments(bundle);
                ft.commit();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Bundle bundle = new Bundle();
                bundle.putString("info_username", username);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                MainScreen mainactivity = new MainScreen();
                ft.replace(R.id.frameLay, mainactivity);
                mainactivity.setArguments(bundle);
                ft.commit();

            }
        });
    }

    private void PaymentsInfo() {

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit2.create(APIService.class);

        Call<List<Payment>> call = service.getPayments(user_id);

        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, retrofit2.Response<List<Payment>> response2) {
                if (response2.isSuccessful()) {
                    servicesid.clear();
                    servicesontime.clear();
                    servicesperiod.clear();
                    servicesofftime.clear();
                    servicescost.clear();
                    servicesinfo.clear();

                    List<Payment> services = response2.body();
                    String payment_id,payment_time,payment_sum,serviceStack;
                    paymentsInfo = new ArrayList<>();
                    for (int i = 0; i < services.size(); i++) {
                        Payment service = services.get(i);
                        payment_id = service.getId();
                        payment_time = service.getTime();
                        payment_sum = service.getSum();

                        serviceStack = "оплата №" + payment_id + " на сумму " + payment_sum + "р. в " + payment_time;
                        servicesinfo.add(serviceStack);

                        servicesid.add(payment_id);
                        servicesperiod.add(payment_time);
                        servicescost.add(payment_sum);
                        paymentsInfo.add(new Payment_item(payment_id,payment_sum,payment_time));
                    }
                    initializeAdapter();

                    servicescoststr = new String[servicescost.size()];
                    servicescost.toArray(servicescoststr);

                    servicesperiodstr = new String[servicesperiod.size()];
                    servicesperiod.toArray(servicesperiodstr);

                    servicesidstr = new String[servicesid.size()];
                    servicesid.toArray(servicesidstr);

                    colors = new String[servicesinfo.size()];
                    servicesinfo.toArray(colors);


                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                hideProgressDialog();

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
                Toast.makeText(getActivity(),"Consuming problems",Toast.LENGTH_LONG).show();
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
                        //Приходящий ответ!!!!! ------------------------------------------------------------------------

                        AddPayment();
                        Toast.makeText(getActivity(),"Payment added",Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(),"Problems while finishing payment. Try again later.",Toast.LENGTH_LONG).show();
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

}



