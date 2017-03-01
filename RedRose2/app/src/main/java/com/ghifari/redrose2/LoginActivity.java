package com.ghifari.redrose2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("LoginActivity", "start");

        final EditText te_username = (EditText) findViewById(R.id.editText_Username);
        final EditText te_password = (EditText) findViewById(R.id.editText_Password);

        final Button b_signin = (Button) findViewById(R.id.button_SignIn);
        Button b_register = (Button) findViewById(R.id.button_Register);

        final TextView t_userview = (TextView) findViewById((R.id.editText_Username));
        t_userview.setText("wawawawaw");

        //firabase-stuff==================
        if(((MyApplication)getApplication()).getmAuth() == null) {
            mAuth = FirebaseAuth.getInstance();
            ((MyApplication)getApplication()).setmAuth(mAuth);
        }
        else
        {
            mAuth = ((MyApplication)getApplication()).getmAuth();
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("FirebaseAuth", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d("FirebaseAuth", "onAuthStateChanged:signed_in:" + user.getDisplayName());
                    t_userview.setText(user.getEmail());
                    signedin = true;
                    b_signin.setText("Sign out");
                } else {
                    // User is signed out
                    Log.d("FirebaseAuth", "onAuthStateChanged:signed_out");
                    signedin = false;
                    t_userview.setText("");
                    b_signin.setText("Sign in");
                }
                // ...
            }
        };//mAuthListener

        b_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("button", "signin-click");
                Log.v("button", "signin "+te_username.getText()+" "+te_password.getText());
                String username = te_username.getText().toString();
                String password = te_password.getText().toString();
                if(!signedin) {
                    if (isValidAuth(username, password)) {
                        SignInUser(username, password);
                    }
                }
                else {
                    mAuth.signOut();
                }
            }
        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("button", "register-click");
                Log.v("button", "register "+te_username.getText()+" "+te_password.getText());

                RegisterUser(te_username.getText().toString(), te_password.getText().toString());
                mAuth.signOut();
//                Intent intent_Goal = new Intent(LoginActivity.this, GoalActivity.class);
//                LoginActivity.this.startActivity(intent_Goal);
            }
        });
    }

    public boolean isValidAuth(String username, String password)
    {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void RegisterUser(String username, String password)
    {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FirebaseAuth", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void SignInUser(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FirebaseAuth", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("FirebaseAuth", "signInWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(intent);
                        }

                        // ...
                    }
                });
    }

    public FirebaseUser GetUserInfo()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }

        return user;
    }

    //Atribut=============================================
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String name = "";
    boolean signedin = false;
}
