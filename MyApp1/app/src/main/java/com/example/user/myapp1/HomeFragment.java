package com.example.user.myapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by haveryahad on 12/21/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView textViewShow;
    private Button buttonChoose1;
    private Button VolUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

//        textViewShow = (TextView) view.findViewById(R.id.textViewShow1);
        buttonChoose1 = (Button) view.findViewById(R.id.btn_incidents);
        VolUser = (Button) view.findViewById(R.id.volUser);

        buttonChoose1.setOnClickListener(this);
        VolUser.setOnClickListener(this);

//        view.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"This is Home Fragment!",Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    public void onClick(View view) {
      if (view == buttonChoose1) {
         // Toast.makeText(getActivity(),"Clicked!",Toast.LENGTH_SHORT).show();
          startActivity(new Intent(getActivity(), ShowImagesActivity.class));

        }

        if (view == VolUser) {
          startActivity(new Intent(getActivity(),UserActivity.class));
        }
    }



}
