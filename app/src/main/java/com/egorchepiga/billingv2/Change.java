package com.egorchepiga.billingv2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.POJO.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 14.03.2016.
 */
public class Change extends Fragment {

    Intent intent1;
    ProgressDialog pDialog;
    private String TAG = MainScreen.class.getSimpleName();
    String oldusername,username,newpassword,oldpassword;
    EditText usernameedit,newpasswordedit,oldpasswordedit;
    Button confirm;
    View v;

    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.user_change, null);

        usernameedit = (EditText) v.findViewById(R.id.usernameedit);
        oldpasswordedit = (EditText) v.findViewById(R.id.oldpasswordedit);
        newpasswordedit = (EditText) v.findViewById(R.id.newpasswordedit);

        Bundle bundle = this.getArguments();
        oldusername = bundle.getString("info_username");
        oldpassword = bundle.getString("info_password");

        usernameedit.setText(oldusername);
        usernameedit.setRawInputType(0x00000000);

;

        pDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading dictionary file...");
        pDialog.setCancelable(false);


        confirm = (Button) v.findViewById(R.id.button5);
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (oldpasswordedit.getText().toString().equals(oldpassword)){
                    ChangeUser();}else {
                    Bundle bundle = new Bundle();
                    bundle.putString("info_username", oldusername);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    MainScreen mainactivity = new MainScreen();
                    ft.replace(R.id.frameLay, mainactivity);
                    mainactivity.setArguments(bundle);
                    ft.commit();
                }
            }
        });

        return v;
    }


    private void ChangeUser() {

        newpassword = newpasswordedit.getText().toString();
        username = usernameedit.getText().toString();

        String URL = "http://cybergenesis.ru/egor/change_abonent?oldusername="+oldusername+"&username="+username+"&password="+newpassword;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Post> call = service.changeUserInfo(oldusername,username,newpassword);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                Bundle bundle = new Bundle();
                bundle.putString("info_username", oldusername);
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
                bundle.putString("info_username", oldusername);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                MainScreen mainactivity = new MainScreen();
                ft.replace(R.id.frameLay, mainactivity);
                mainactivity.setArguments(bundle);
                ft.commit();

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
