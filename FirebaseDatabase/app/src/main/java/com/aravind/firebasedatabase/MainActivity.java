package com.aravind.firebasedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myGame = myRef.child("game");

    private TextView textView;
    private Button paper,rock,scissor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        paper = findViewById(R.id.paper);
        rock = findViewById(R.id.rock);
        scissor = findViewById(R.id.scissor);

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myGame.setValue("Paper");
            }
        });

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myGame.setValue("Rock");
            }
        });

        scissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myGame.setValue("Scissor");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        myGame.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.i("TAG", "Value is: " + value);
                textView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("TAG", "Failed to read value.", error.toException());
                Toast.makeText(MainActivity.this, "Failed to read value."+error.toException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gotoNextActivity(View view) {
        startActivity( new Intent(this,UploadsActivity.class));
    }
}
