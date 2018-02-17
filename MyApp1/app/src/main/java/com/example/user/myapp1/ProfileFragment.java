package com.example.user.myapp1;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Button bc, bm;
    EditText en, em;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        en = (EditText) view.findViewById(R.id.et1);
        em = (EditText) view.findViewById(R.id.et2);
        bm = (Button) view.findViewById(R.id.bt1);
        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendsms();
            }
        });
//        bc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                phoneCall();
//            }
//        });


    }

    protected void sendsms() {
        String phno = en.getText().toString();
        String msg = "An Incident has Occured, Please find the Location via the notification I have sent.";
        try {
            SmsManager sms = SmsManager.getDefault();

            String numbers[] = {"9820952523", "9870016163", "8454902075"};

            for(String number : numbers) {
                sms.sendTextMessage(number, null, msg, null, null);
            }

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phno, null, msg, null, null);
            Toast.makeText(getActivity(), "Sms Sent", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Sending Failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void phoneCall() {
        String phno = en.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phno));
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

}
