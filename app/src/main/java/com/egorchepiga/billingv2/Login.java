package com.egorchepiga.billingv2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.POJO.User;
import com.egorchepiga.billingv2.fragment_dialog.DateTimeDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 12.03.2016.
 */
public class Login extends Fragment{

    ProgressDialog pDialog;
    String[] info;
    public static String user_info,password_info,balance_info,id_info;
    private EditText username,password;
    private Button login;
    public static RequestQueue requestQueue;
    private static final String URL = "http://cybergenesis.ru/egor/get_login";
    private StringRequest request;
    private String TAG = MainScreen.class.getSimpleName();


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
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View v = localInflater.inflate(R.layout.login_lay, null);
        super.onCreate(savedInstanceState);

        username = (EditText) v.findViewById(R.id.username);
        password = (EditText) v.findViewById(R.id.password);
        login = (Button) v.findViewById(R.id.login);

        username.setTextColor(Color.BLACK);
        password.setTextColor(Color.BLACK);

        pDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading dictionary file...");
        pDialog.setCancelable(false);

        login = (Button) v.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProgressDialog();
                LoginRequest();
            }
        });

    return v;}


    private void LoginRequest() {
        String URl = "http://cybergenesis.ru/egor/get_login"+"?username="+username.getText().toString()+"&password="+password.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<User> call = service.getLogin(username.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {


                someEventListener.DrawerEnabled(true);

                if (response.body().getId() != null & response.body().getId() != "" & response.body().getId() != "null") {
                    balance_info = response.body().getBalance();
                    user_info = response.body().getUsername();
                    password_info = response.body().getPassword();
                    id_info = response.body().getId();

                    ((TextView) getActivity().findViewById(R.id.info_username_nav)).setText(user_info);

                    hideProgressDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("info_username", user_info);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    MainScreen mainactivity = new MainScreen();
                    ft.replace(R.id.frameLay, mainactivity);
                    mainactivity.setArguments(bundle);
                    ft.commit();

                }else {
                    Toast.makeText(getActivity(), "Incorrect login or password. Try again.", Toast.LENGTH_SHORT ).show();
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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