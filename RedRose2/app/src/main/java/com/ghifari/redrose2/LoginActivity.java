package com.ghifari.redrose2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.v("LoginActivity", "start");
        final EditText te_username = (EditText) findViewById(R.id.editText_Username);
        final EditText te_password = (EditText) findViewById(R.id.editText_Password);

        Button b_signin = (Button) findViewById(R.id.button_SignIn);
        Button b_register = (Button) findViewById(R.id.button_Register);

        b_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("button", "signin-click");
                Log.v("button", "signin "+te_username.getText()+" "+te_password.getText());
                String username = te_username.getText().toString();
                String password = te_password.getText().toString();
                if(isValidAuth(username, password)) {
                    Intent intent_Goal = new Intent(LoginActivity.this, GoalActivity.class);
                    LoginActivity.this.startActivity(intent_Goal);
                }
            }
        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("button", "register-click");
                Log.v("button", "register "+te_username.getText()+" "+te_password.getText());

                Intent intent_Goal = new Intent(LoginActivity.this, GoalActivity.class);
                LoginActivity.this.startActivity(intent_Goal);
            }
        });
    }

    public boolean isValidAuth(String username, String password)
    {
        return true;
    }
}
