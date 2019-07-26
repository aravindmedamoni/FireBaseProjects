package com.aravind.registerformwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference userRef = myRef.child("Users");

    private EditText username,email,mobileNumber;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.Register_button);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        mobileNumber = findViewById(R.id.mobileNumber);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String MobileNumber = mobileNumber.getText().toString().trim();

                if (!(Username.equals("") && Email.equals("") && MobileNumber.equals(""))){

                    HashMap<String,String> userDetails = new HashMap<>();
                    userDetails.put("Username",Username);
                    userDetails.put("Email",Email);
                    userDetails.put("Mobile Number",MobileNumber);

                    userRef.push().setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                clearData();
                            }else {
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                clearData();
                            }
                        }
                    });

                }else {
                    Toast.makeText(MainActivity.this, "Please Provide All The Details", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void clearData(){
        username.setText("");
        email.setText("");
        mobileNumber.setText("");
    }

    public void getData(View view) {

        startActivity(new Intent(this,UserDetailsActivity.class));
    }
}
