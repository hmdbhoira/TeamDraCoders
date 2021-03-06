package com.example.user.myapp1;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment {


    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emergency, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        view.findViewById(R.id.btn_emergency).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {


                String number = "9870016163";
                Uri number1 = Uri.parse("tel:" + number);
                Intent dial = new Intent(Intent.ACTION_CALL);
                dial.setData(number1);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(dial);

            }

        });


        view.findViewById(R.id.btn_ambulance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = "101";
                Uri number1 = Uri.parse("tel:" + number);
                Intent dial = new Intent(Intent.ACTION_CALL);
                dial.setData(number1);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(dial);

            }
        });

        view.findViewById(R.id.btn_police).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "100";
                Uri number1 = Uri.parse("tel:" + number);
                Intent dial = new Intent(Intent.ACTION_CALL);
                dial.setData(number1);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(dial);
            }
        });

        final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.beep);

        view.findViewById(R.id.btn_sos).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mp.isPlaying())
                    mp.pause();
                else
                    mp.start();
            }
        });

        view.findViewById(R.id.btn_sos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mp.isPlaying())
                    mp.pause();
                else
                    mp.start();

                String msg = "An Incident has Occured, Please find the Location via the notification I have sent.";
                try {
                    SmsManager sms = SmsManager.getDefault();

                    String numbers[] = {"9820952523", "9870016163", "8454902075"};

                    for(String number : numbers) {
                        sms.sendTextMessage(number, null, msg, null, null);
                    }
                        Toast.makeText(getActivity(), "Sms Sent", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Sending Failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


    }

}