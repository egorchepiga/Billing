package com.egorchepiga.billingv2;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by George on 27.03.2016.
 */
public class Support extends Fragment {

    Button phoneBtn,mailBtn,vkBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.support_lay, null);

        phoneBtn = (Button) v.findViewById(R.id.phoneBtn1);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+79119968846"));
                try {
                    startActivity(intent);
                }catch (SecurityException e){e.printStackTrace();
                }
            }
        });

        mailBtn = (Button) v.findViewById(R.id.mailBtn2);
        mailBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to = {"egorchepiga@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL,to);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Billing Support");
                intent.putExtra(Intent.EXTRA_TEXT, "Hello!");
                intent.setType("message/rfc822");
                Intent chooser = Intent.createChooser(intent,"Send Email");
                startActivity(chooser);
            }
        });

        vkBtn = (Button) v.findViewById(R.id.vkBtn3);
        vkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://vk.com/egorchepiga"));
                startActivity(intent);
            }
        });

        return v;
    }
}