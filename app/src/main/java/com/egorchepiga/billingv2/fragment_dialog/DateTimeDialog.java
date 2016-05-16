package com.egorchepiga.billingv2.fragment_dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by George on 29.03.2016.
 */
public class DateTimeDialog extends DialogFragment implements DialogInterface.OnClickListener {


    Boolean checktime,checkdate;
    int DIALOG_DATE = 2;
    int DIALOG_TIME = 1;
    int myDay,myHour,myMinute,myYear,myMonth;
    final String LOG_TAG = "myLogs";



    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        int id = bundle.getInt("id");

        Calendar contime = new GregorianCalendar();
        contime = Calendar.getInstance();
        myYear = contime.get(Calendar.YEAR);
        myMonth = contime.get(Calendar.MONTH);
        myDay = contime.get(Calendar.DAY_OF_MONTH);
        myHour = contime.get(Calendar.HOUR_OF_DAY);
        myMinute = contime.get(Calendar.MINUTE);

        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_LIGHT,myCallBack2, myYear, myMonth, myDay);
            return tpd;
        }
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), TimePickerDialog.THEME_HOLO_LIGHT, myCallBack, myHour, myMinute, true);
            return tpd;
        }
        //  return super.onCreateDialog(id);
return super.onCreateDialog(savedInstanceState);
    }


    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            // положительная кнопка
            case Dialog.BUTTON_POSITIVE:

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


    DatePickerDialog.OnDateSetListener myCallBack2 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            checkdate = true;
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            someEventListener.DataEvent(String.valueOf(myYear),String.valueOf(myMonth),String.valueOf(myDay));
            someEventListener.someEvent("Repair_item date is is " + myDay + "/" + myMonth + "/" + myYear);
        }
    };
    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            checktime = true;
            myHour = hourOfDay;
            myMinute = minute;
            someEventListener.TimeEvent(String.valueOf(myHour),String.valueOf(myMinute));
            if (minute<10){
                someEventListener.someEvent("Repair_item time is: " + myHour + ":0" + myMinute);
            }else {
                someEventListener.someEvent("Repair_item time is: " + myHour + ":" + myMinute);
            }
        }
    };

    public interface onSomeEventListener {
        public void someEvent(String s);
        public void DataEvent(String myYear,String myMonth, String myDay);
        public void TimeEvent(String myHour,String myMinute);
        public void DrawerEnabled(Boolean state);
        public void HomeSelection();
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
}
