package com.egorchepiga.billingv2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.egorchepiga.billingv2.fragment_dialog.DateTimeDialog;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by George on 25.03.2016.
 */
public class NavigationView extends Activity implements DateTimeDialog.onSomeEventListener {



    Fragment frag;
    FragmentTransaction ft;
    FragmentManager fm;
    TextView username_nav,balance_nav,id_nav,phone_nav,password_nav;
    Drawer.Result drawer;
    SharedPreferences sPref;

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("info_username", username_nav.getText().toString());
        ed.putString("info_password", password_nav.getText().toString());
        ed.putString("info_balance", balance_nav.getText().toString());
        ed.putString("info_id", id_nav.getText().toString());
        ed.putString("phone", phone_nav.getText().toString());
        ed.commit();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        username_nav.setText(sPref.getString("info_username", ""));
        password_nav.setText(sPref.getString("info_password", ""));
        balance_nav.setText(sPref.getString("info_balance", ""));
        id_nav.setText(sPref.getString("info_id", ""));
        phone_nav.setText(sPref.getString("phone", ""));

    }



    /*@Override
    public void onBackPressed() {

        onBackPressedListener backPressedListener = null;
        Repair fragment;
        try{
        fragment = (Repair)getFragmentManager().findFragmentById(R.id.frameLay);}
        catch (Exception e){fragment = null;}
        if (fragment != null) {
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLay);
            frameLayout.removeAllViews();
            backPressedListener = (onBackPressedListener) fragment;
            backPressedListener.onBackPressed();
            } else {
            super.onBackPressed();
        }

    }*/


    @Override
    public void HomeSelection() {
        drawer.setSelection(0);
    }

    @Override
    public void DrawerEnabled(Boolean state) {
        drawer.updateItem(new PrimaryDrawerItem().withName("Add service").withIcon(FontAwesome.Icon.faw_plus).setEnabled(state),2 );
        drawer.updateItem(new PrimaryDrawerItem().withName("Payment").withIcon(FontAwesome.Icon.faw_money).setEnabled(state), 3);
        drawer.updateItem(new PrimaryDrawerItem().withName("Repairs").withIcon(FontAwesome.Icon.faw_bug).setEnabled(state), 4);
        drawer.updateItem(new PrimaryDrawerItem().withName("Password").withIcon(FontAwesome.Icon.faw_pencil).setEnabled(state), 6);
        drawer.updateItem(new PrimaryDrawerItem().withName("Support").withIcon(FontAwesome.Icon.faw_support).setEnabled(state), 7);
        drawer.updateItem(new PrimaryDrawerItem().withName("Exit").withIcon(FontAwesome.Icon.faw_sign_out).setEnabled(state), 8);
    }

    @Override
    public void DataEvent(String myYear, String myMonth, String myDay) {
        Fragment frag1 = getFragmentManager().findFragmentById(R.id.frameLay);
        ((TextView)frag1.getView().findViewById(R.id.myYears)).setText(myYear);
        ((TextView)frag1.getView().findViewById(R.id.myMonths)).setText(myMonth);
        ((TextView)frag1.getView().findViewById(R.id.myDays)).setText(myDay);
    }

    @Override
    public void TimeEvent(String myHour, String myMinute) {
        Fragment frag1 = getFragmentManager().findFragmentById(R.id.frameLay);
        ((TextView)frag1.getView().findViewById(R.id.myHours)).setText(myHour);
        ((TextView)frag1.getView().findViewById(R.id.myMinutes)).setText(myMinute);
    }

    @Override
    public void someEvent(String s) {
        Fragment frag1 = getFragmentManager().findFragmentById(R.id.frameLay);
        if (s.length()>24){

        ((TextView)frag1.getView().findViewById(R.id.textDate)).setText(s);
    }else {
            ((TextView)frag1.getView().findViewById(R.id.textTime)).setText(s);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_layout);

        password_nav = (TextView) findViewById(R.id.password_nav);
        password_nav.setVisibility(View.INVISIBLE);

        username_nav = (TextView) findViewById(R.id.info_username_nav);
        username_nav.setVisibility(View.INVISIBLE);

        balance_nav = (TextView) findViewById(R.id.info_balance_nav);
        balance_nav.setVisibility(View.INVISIBLE);

        id_nav = (TextView) findViewById(R.id.info_id_nav);
        id_nav.setVisibility(View.INVISIBLE);

        phone_nav = (TextView) findViewById(R.id.phone_nav);
        phone_nav.setVisibility(View.INVISIBLE);

        frag = new Fragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sPref = getPreferences(MODE_PRIVATE);
        username_nav.setText(sPref.getString("info_username", ""));

        if (!username_nav.getText().toString().isEmpty()){
            loadText();
            Fragment frag2 = new MainScreen();
            Bundle bundle = new Bundle();
            bundle.putString("info_username", username_nav.getText().toString());
            frag2.setArguments(bundle);
            ft = getFragmentManager().beginTransaction();
            ft.add(R.id.frameLay, frag2,"MainFragment");
            ft.commit();


        } else {
            Fragment frag2 = new Login();
            ft = getFragmentManager().beginTransaction();
            ft.add(R.id.frameLay, frag2);
            ft.commit();
        }

        drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withBadge("").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Add service").withIcon(FontAwesome.Icon.faw_plus).setEnabled(false),
                        new PrimaryDrawerItem().withName("Payment").withIcon(FontAwesome.Icon.faw_money).withBadge("").withIdentifier(2).setEnabled(false),
                        new PrimaryDrawerItem().withName("Repairs").withIcon(FontAwesome.Icon.faw_bug).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Password").withIcon(FontAwesome.Icon.faw_pencil).setEnabled(false),
                        new SecondaryDrawerItem().withName("Support").withIcon(FontAwesome.Icon.faw_support).withBadge("").withIdentifier(1).setEnabled(false),
                        new SecondaryDrawerItem().withName("Exit").withIcon(FontAwesome.Icon.faw_sign_out).setEnabled(false)
                ).withOnDrawerListener(new Drawer.OnDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                // Скрываем клавиатуру при открытии Navigation Drawer
                InputMethodManager inputMethodManager = (InputMethodManager) NavigationView.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(NavigationView.this.getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }
        })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обработка клика
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        if (position == 1) {
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Fragment frag2 = new Fragment();
                            if (username_nav.getText().toString() != "" & username_nav.getText().toString() != "null") {
                                frag2 = new MainScreen();
                                Bundle bundle = new Bundle();
                                bundle.putString("info_username", username_nav.getText().toString());
                                ft.addToBackStack(null);
                                frag2.setArguments(bundle);
                            } else {
                                frag2 = new Login();
                            }
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2,"MainFragment");
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                        if (position == 2) {
                            Fragment frag2 = new AddService();
                            Bundle bundle = new Bundle();
                            bundle.putString("info_id", id_nav.getText().toString());
                            bundle.putString("info_balance", balance_nav.getText().toString());
                            frag2.setArguments(bundle);
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2);
                            ft.addToBackStack(null);
                            ft.commit();


                        }

                        if (position == 3) {
                            Fragment frag2 = new Fragment();
                            frag2 = new AddPayment();
                            Bundle bundle = new Bundle();
                            bundle.putString("info_id", id_nav.getText().toString());
                            bundle.putString("info_balance", balance_nav.getText().toString());
                            bundle.putString("info_username", username_nav.getText().toString());
                            frag2.setArguments(bundle);
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2);
                            ft.addToBackStack(null);
                            ft.commit();

                        }

                        if (position == 4) {
                            Fragment frag2 = new Fragment();
                            frag2 = new Repair();
                            Bundle bundle = new Bundle();
                            bundle.putString("info_id", id_nav.getText().toString());
                            bundle.putString("phone", phone_nav.getText().toString());
                            frag2.setArguments(bundle);
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2,"Repair_YO");
                            ft.addToBackStack("addRepair");
                            ft.commit();

                        }

                        if (position == 6) {

                            Fragment frag2 = new Change();
                            Bundle bundle = new Bundle();
                            bundle.putString("info_username", username_nav.getText().toString());
                            bundle.putString("info_password", password_nav.getText().toString());
                            frag2.setArguments(bundle);
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2);
                            ft.addToBackStack(null);
                            ft.commit();

                        }

                        if (position == 7) {
                            Fragment frag2 = new Support();
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2);
                            ft.addToBackStack(null);
                            ft.commit();

                        }

                        if (position == 8) {
                            Fragment frag2 = new Login();

                            drawer.updateItem(new PrimaryDrawerItem().withName("Add service").withIcon(FontAwesome.Icon.faw_plus).setEnabled(false),2 );
                            drawer.updateItem(new PrimaryDrawerItem().withName("Payment").withIcon(FontAwesome.Icon.faw_money).setEnabled(false), 3);
                            drawer.updateItem(new PrimaryDrawerItem().withName("Repairs").withIcon(FontAwesome.Icon.faw_bug).setEnabled(false), 4);
                            drawer.updateItem(new PrimaryDrawerItem().withName("Password").withIcon(FontAwesome.Icon.faw_pencil).setEnabled(false), 6);
                            drawer.updateItem(new PrimaryDrawerItem().withName("Support").withIcon(FontAwesome.Icon.faw_support).setEnabled(false), 7);
                            drawer.updateItem(new PrimaryDrawerItem().withName("Exit").withIcon(FontAwesome.Icon.faw_sign_out).setEnabled(false), 8);
                            drawer.setSelection(0);

                            username_nav.setText("");

                            sPref = getPreferences(MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString("info_username", "");
                            ed.putString("info_password", "");
                            ed.putString("info_balance", "");
                            ed.putString("info_id", "");
                            ed.putString("phone", "");
                            ed.commit();

                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frameLay, frag2);
                            ft.addToBackStack(null);
                            ft.commit();

                        }

                        /*
                            if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                // учтите, не делайте так, если ваш бейдж содержит символ "+"
                                try {
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d("test", "Не нажимайте на бейдж, содержащий плюс! :)");
                                }
                            }
                        }
                        */
                    }
                })
                        //Обработка long клика
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // Обработка длинного клика, например, только для SecondaryDrawerItem
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);

                            Toast.makeText(NavigationView.this, NavigationView.this.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();



    }

}
