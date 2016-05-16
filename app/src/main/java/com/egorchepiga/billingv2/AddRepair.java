package com.egorchepiga.billingv2;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.egorchepiga.billingv2.API_Service.APIService;
import com.egorchepiga.billingv2.POJO.Post;
import com.egorchepiga.billingv2.POJO.Type;
import com.egorchepiga.billingv2.fragment_dialog.DateTimeDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by George on 21.03.2016.
 */
public class AddRepair extends Fragment{

    DialogFragment dialogFragment;
    CheckBox phoneBox;
    Boolean checktime,checkdate;
    String[] typeArrayString;
    ProgressDialog pDialog;
    String dateString,info_id,phone,phone2,time,description,type,id,param;
    public  TextView textTime,textDate,descriptionText,telephoneText;
    Calendar contime = new GregorianCalendar();
    int DIALOG_DATE = 2;
    int DIALOG_TIME = 1;
    int myDay,myHour,myMinute,myYear,myMonth;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    Date date;
    Spinner spinner;
    View v;
    TextView myDayT,myHourT,myMinuteT,myYearT,myMonthT;
    FragmentTransaction ft;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        dialogFragment = new DateTimeDialog();


        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Light);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        v = localInflater.inflate(R.layout.add_repair, null);


        spinner = (Spinner) v.findViewById(R.id.spinner);
        descriptionText = (TextView) v.findViewById(R.id.descriptionText);
        telephoneText = (TextView) v.findViewById(R.id.telephoneText);
        textTime = (TextView) v.findViewById(R.id.textTime);
        textDate = (TextView) v.findViewById(R.id.textDate);

        myDayT = (TextView) v.findViewById(R.id.myDays);
        myMonthT = (TextView) v.findViewById(R.id.myMonths);
        myYearT = (TextView) v.findViewById(R.id.myYears);
        myHourT = (TextView) v.findViewById(R.id.myHours);
        myMinuteT = (TextView) v.findViewById(R.id.myMinutes);

        contime = Calendar.getInstance();

        myHour = contime.get(Calendar.HOUR_OF_DAY);
        myMinute = contime.get(Calendar.MINUTE);
        myDay = contime.get(Calendar.DAY_OF_MONTH);
        myYear = contime.get(Calendar.YEAR);
        myMonth = contime.get(Calendar.MONTH);

        Bundle bundle = this.getArguments();
        info_id = bundle.getString("info_id");
        phone = bundle.getString("phone");
        type = bundle.getString("type");
        time = bundle.getString("time");
        description = bundle.getString("description");
        id = bundle.getString("id");


        textTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checktime = true;
                Bundle bundle = new Bundle();
                bundle.putInt("id", 1);
                AddRepair addRepair = new AddRepair();
                dialogFragment.setTargetFragment(addRepair,1);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), "dialogFragment");
            }
        });

        textDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkdate = true;
                Bundle bundle = new Bundle();
                bundle.putInt("id",2);
                AddRepair addRepair = new AddRepair();
                dialogFragment.setTargetFragment(addRepair,1);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(), "dialogFragment");
            }
        });

        phoneBox = (CheckBox) v.findViewById(R.id.phoneBox);
        phoneBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (phoneBox.isChecked()) {
                    phone2 = telephoneText.getText().toString();
                    telephoneText.setText(((TextView)getActivity().findViewById(R.id.phone_nav)).getText());
                    telephoneText.setEnabled(false);
                } else {
                    telephoneText.setText(phone2);
                    telephoneText.setEnabled(true);
                }
            }
        });

        pDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Please wait");
        pDialog.setMessage("Loading dictionary file...");
        pDialog.setCancelable(false);
        showProgressDialog();

        Button add = (Button) v.findViewById(R.id.button12);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (id == "null" || id == null || id == "") {
                    AddRepairMethod();
                } else {
                    ChangeRepairMethod();
                }
            }
        });

        GetType();

        if (id!="null" & id != null & id !=""){
            descriptionText.setText(description);
            telephoneText.setText(phone);
        }

        checktime = false;
                checkdate = false;

   return v;
    }


    public void ChangeRepairMethod(){
        showProgressDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        time = time.replaceAll(" ","-");
        type = spinner.getSelectedItem().toString();
        phone = telephoneText.getText().toString();
        description =  descriptionText.getText().toString();

        try {
            contime.setTime(formatter.parse(time));
        }catch (ParseException e){e.printStackTrace();}

        if (checktime == true){
            contime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(myHourT.getText().toString()));
            contime.set(Calendar.MINUTE,Integer.parseInt(myMinuteT.getText().toString()));
            contime.set(Calendar.SECOND,0);
        }

        if (checkdate == true){
            contime.set(Calendar.YEAR,Integer.parseInt(myYearT.getText().toString()));
            contime.set(Calendar.MONTH,Integer.parseInt(myMonthT.getText().toString()));
            contime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(myDayT.getText().toString()));
        }

        if (checkdate == false & checktime == false){
        dateString = time;
        }else {
            dateString = formatter.format(contime.getTime());
        }

        String description2 = descriptionText.getText().toString();
        if (description2 == "null" ||  description2 == ""){
            description2 = "Contact me please!";
        }

        Call<Post> call = service.changeRepair(id, type, phone, dateString, description2);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {

                hideProgressDialog();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT ).show();
                hideProgressDialog();
            }
        });

    }

    public void AddRepairMethod(){
        showProgressDialog();

        if (checkdate == false || checktime == false){
            Toast.makeText(getActivity(),"OMG",Toast.LENGTH_SHORT).show();
        }else {
        contime.set(Calendar.YEAR,Integer.parseInt(myYearT.getText().toString()));
        contime.set(Calendar.MONTH,Integer.parseInt(myMonthT.getText().toString()));
        contime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(myDayT.getText().toString()));
        contime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(myHourT.getText().toString()));
        contime.set(Calendar.MINUTE,Integer.parseInt(myMinuteT.getText().toString()));
        contime.set(Calendar.SECOND,0);
        date = contime.getTime();
        dateString = formatter.format(date);

        String phone2 = telephoneText.getText().toString();
        if (phone2 == "null" ||  phone2 == ""){
            phone2 = phone;
        }

        String description2 = descriptionText.getText().toString();
        if (description2.isEmpty()){
            description2 = "Contact me please!";
        }

        String URL = "http://cybergenesis.ru/egor/add_repair?type=Bug&telephone=999&time=2016-03-22-14:17:05&abonent_id=96&status=Completed&description=dfga";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Post> call = service.addRepair(spinner.getSelectedItem().toString(),phone2,dateString,info_id,"wainitg_for_confirmation",description2);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, retrofit2.Response<Post> response) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getActivity(), "Check your connectrion or try later.", Toast.LENGTH_SHORT ).show();
                hideProgressDialog();
            }
        });
    }
    }

    public void GetType(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cybergenesis.ru/egor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<List<Type>> call = service.getType();
        call.enqueue(new Callback<List<Type>>() {
            @Override
            public void onResponse(Call<List<Type>> call, retrofit2.Response<List<Type>> response) {
                List<Type> typeList = response.body();
                String typeString;
                List<String> typeArray = new ArrayList<>();


                for (int i = 0; i < typeList.size(); i++) {
                    Type type = typeList.get(i);
                    typeString = type.getType();
                    typeArray.add(typeString);
                }
                typeArrayString = new String[typeArray.size()];
                typeArray.toArray(typeArrayString);
                // адаптер
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_black_text,R.id.list_content, typeArrayString);
                adapter.setDropDownViewResource(R.layout.list_black_text);

                spinner.setAdapter(adapter);
                // заголовок
                spinner.setPrompt("Repair_item type");
                // устанавливаем обработчик нажатия
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<List<Type>> call, Throwable t) {
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
