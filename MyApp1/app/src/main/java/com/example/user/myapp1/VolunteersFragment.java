package com.example.user.myapp1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * Created by haveryahad on 12/12/2017.
 */

public class VolunteersFragment extends Fragment {

    private ListView listview;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<String> Name= new ArrayList<>();

    @Nullable

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("message");



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_volunteers,null);

//        String[] volunteer={"Hammad","Arwa","Sami Ali","Ahad","Uzma","Javeria","Ashfi","Manasi","Nida"};
//        ListView lst = (ListView) view.findViewById(R.id.listvw);

        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("Volunteers");
        DatabaseReference subCategoriesRef = myRef.child("Name");
        listview = (ListView) view.findViewById(R.id.listvw);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Name);
        listview.setAdapter(arrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //String key = dataSnapshot.getKey();
                String value= "Name: "+dataSnapshot.child("Name").getValue().toString()+"\nContact: "+dataSnapshot.child("MobileNo").getValue().toString();
               // int number= dataSnapshot.child("MobileNo").getValue().to();

                //tring value = key.getValue(Name);
                Name.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }




    public void buttonClicked(View view) {
        // Write a message to the database

        //EditText editText = (EditText)findViewById(R.id.message1);
        // EditText editText1=(EditText)findViewById(R.id.contact);
        //EditText editText2=(EditText)findViewById(R.id.email);


        //String child=editText1.getText().toString();

        //myRef=database.getReference("users").child(child);
        //myRef.child("name").setValue(editText.getText().toString());
        //myRef.child("contact").setValue(editText1.getText().toString());
        //myRef.child("email").setValue(editText2.getText().toString());

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Volunteers");

    }
}

