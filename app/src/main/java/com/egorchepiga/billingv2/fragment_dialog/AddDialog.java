package com.egorchepiga.billingv2.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.MainScreen;
import com.egorchepiga.billingv2.POJO.Post;
import com.egorchepiga.billingv2.R;
import com.egorchepiga.billingv2.util.IabHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 28.03.2016.
 */
public class AddDialog extends DialogFragment implements DialogInterface.OnClickListener {

    IabHelper mHelper;
    static String ITEM_SKU;
    final String LOG_TAG = "myLogs";
    String Returned,Balance,service_id,info_id,info_username,positionStr,serviceCost,ontimeString,offtimeString;


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        serviceCost = bundle.getString("serviceCost");
        Balance = bundle.getString("Balance");
        positionStr = bundle.getString("positionStr");
        ontimeString = bundle.getString("ontimeString");
        offtimeString = bundle.getString("offtimeString");
        info_id = bundle.getString("info_id");
        info_username = bundle.getString("info_username");



        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Добавление услуги")
                .setPositiveButton("Да", this)
                .setNegativeButton("Нет", this)
                .setMessage("Вы хотите добавить эту услугу за "+serviceCost+" рублей?");
        return adb.create();
    }


    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            // положительная кнопка
            case Dialog.BUTTON_POSITIVE:

                Double serviceCostDouble = Double.parseDouble(serviceCost);

                if (Double.parseDouble(Balance) >= serviceCostDouble) {
                    Balance = String.valueOf(Double.parseDouble(Balance) - serviceCostDouble);

                    //String URL = "http://cybergenesis.ru/egor/add_service?ontime=" + ontimeString + "&offtime=" + offtimeString + "&user_id=" + info_id + "&service=" + Integer.toString(position + 1) + "&balance=" + String.valueOf(balance);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://cybergenesis.ru/egor/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    APIService service = retrofit.create(APIService.class);
                    Call<Post> call = service.addService(ontimeString, offtimeString, info_id, positionStr, Balance);
                    call.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {

                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });


                    Bundle bundle = new Bundle();
                    bundle.putString("info_username", info_username);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    MainScreen mainactivity = new MainScreen();
                    ft.replace(R.id.frameLay, mainactivity);
                    mainactivity.setArguments(bundle);
                    ft.commit();
                }


                break;
            // негаитвная кнопка
            case Dialog.BUTTON_NEGATIVE:
                break;


            // нейтральная кнопка
            //case Dialog.BUTTON_NEUTRAL:
            //    break;
        }
};


    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 2: onCancel");
    }





}
