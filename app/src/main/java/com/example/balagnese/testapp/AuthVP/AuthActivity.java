package com.example.balagnese.testapp.AuthVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.balagnese.testapp.ClientVP.ClientActivity;
import com.example.balagnese.testapp.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class AuthActivity extends AppCompatActivity implements AuthView{

    AuthPresenter ap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ap = new AuthPresenter(this);

        Button authButton = findViewById(R.id.authButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView loginText = findViewById(R.id.loginText);
                TextView passwordText = findViewById(R.id.passwordText);
                String password = passwordText.getText().toString();
                String login = loginText.getText().toString();
                ap.logIn(login, password);
                navigateToClientActivity();
            }
        });
    }

    @Override
    public void navigateToClientActivity() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}
