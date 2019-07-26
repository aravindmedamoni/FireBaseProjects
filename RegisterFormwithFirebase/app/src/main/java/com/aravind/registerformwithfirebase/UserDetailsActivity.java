package com.aravind.registerformwithfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class UserDetailsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference userRef = myRef.child("Users");

    ListView listView;

    ArrayList<String> userInfo = new ArrayList<>();
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        listView = (ListView) findViewById(R.id.listView);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,userInfo);
        listView.setAdapter(arrayAdapter);

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()){
                    Map map = (Map) dataSnapshot.getValue();
                    String name = map.get("Username").toString();
                    String mail = map.get("Email").toString();
                    String number = map.get("Mobile Number").toString();
                    userInfo.add("Username: "+name);
                    userInfo.add("Email: "+mail);
                    userInfo.add("Mobile Number: "+number);
                    userInfo.add("\n");
                    arrayAdapter.notifyDataSetChanged();
                }
//
//                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                    String userDetails = dataSnapshot1.getValue().toString();
//                    try {
//                        JSONObject details = new JSONObject(userDetails);
//                        name = details.getString("Username");
//                      String email = details.getString("Email");
//                      String mobileNumber = details.getString("Mobile Number");
//
//                      userInfo.add(email);
//                      userInfo.add(mobileNumber);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    userInfo.add(name);
//                    arrayAdapter.notifyDataSetChanged();
//                }



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goBack(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
