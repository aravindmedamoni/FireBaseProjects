package com.aravind.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button register_button,login_button;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this,"Current user "+currentUser+" is loggedin",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register_button = findViewById(R.id.register_button);
        login_button = findViewById(R.id.login_button);
    }

    public void onRegister(View view) {

       final String myEmail = email.getText().toString();
       final String myPassowrd = password.getText().toString();

        if (!(myEmail.equals("") && myPassowrd.equals(""))){
            mAuth.createUserWithEmailAndPassword(myEmail, myPassowrd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("TAG", "createUserWithEmail:success");
                               // FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                                //updateUI(user);
                                register_button.setVisibility(View.GONE);
                                login_button.setVisibility(View.VISIBLE);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed."+task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }

                            // ...
                        }
                    });
        }else {
            Toast.makeText(this, "Please Enter Both Email and Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void signIn(View view) {

        final String myEmail = email.getText().toString();
        final String myPassowrd = password.getText().toString();

        mAuth.signInWithEmailAndPassword(myEmail, myPassowrd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "signInWithEmail:success");
                          //  FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                           // updateUI(user);
                            register_button.setVisibility(View.VISIBLE);
                            login_button.setVisibility(View.GONE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
