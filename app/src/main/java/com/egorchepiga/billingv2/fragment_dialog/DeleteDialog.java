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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 28.03.2016.
 */
public class DeleteDialog extends DialogFragment implements DialogInterface.OnClickListener {

    final String LOG_TAG = "myLogs";
    String Returned,Balance,service_id,info_id,info_username;

    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        Returned = bundle.getString("Returned");
        Balance = bundle.getString("Balance");
        service_id = bundle.getString("service_id");
        info_id = bundle.getString("info_id");
        info_username = bundle.getString("info_username");

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Удаление услуги").setPositiveButton("да", this)
                .setNegativeButton("нет", this)
                .setMessage("Хотите удалить эту услугу?    На счёт будет зачислено "+Returned+" рублей.");
        return adb.create();
    }




    public void onClick(DialogInterface dialog, int which) {
        String i = null;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://cybergenesis.ru/egor/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService service = retrofit.create(APIService.class);
                //  String test =String.valueOf(balance)+" "+info_id+" "+servicesidstr[position];
                Call<Post> call = service.deleteService(service_id,info_id,Balance);
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
                Log.d("Response log", "ALL ok");

                break;
            case Dialog.BUTTON_NEGATIVE:
                i = "no";
                break;
            case Dialog.BUTTON_NEUTRAL:
                i = "maybe";
                break;
        }
        if (i != null)
            Log.d(LOG_TAG, "Dialog 2: " + i);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 2: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 2: onCancel");
    }
}
