package com.example.balagnese.testapp.ChildVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.balagnese.testapp.DishesMenuVP.DishesMenuActivity;
import com.example.balagnese.testapp.ProceduresVP.ProceduresActivity;
import com.example.balagnese.testapp.ClientInfo;
import com.example.balagnese.testapp.ClientVP.ClientActivity;
import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.R;

public class ChildActivity extends AppCompatActivity implements ChildView{

    ChildPresenter cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        Intent intent = getIntent();
        String publicId = intent.getStringExtra("child");
        cp = new ChildPresenter(this, publicId);

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.navigateToClientActivity();
            }
        });

        Button procedureButton = findViewById(R.id.childProceduresButton);
        procedureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.navigateToChildProceduresActivity();
            }
        });
    }

    @Override
    public void showChildData(Client client) {
        TextView childInfoTextView = findViewById(R.id.childInfoTextView);
        childInfoTextView.setText(ClientInfo.getInstance().getFIO(client));
    }


    @Override
    public void navigateToChildProceduresActivity(Client client) {
        Intent intent = new Intent(this, ProceduresActivity.class);
        intent.putExtra("client", client.getPublic_id());
        startActivity(intent);

    }

    @Override
    public void navigateToClientActivity() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChildMenuActivity(Client client) {
        Intent intent = new Intent(this, DishesMenuActivity.class);
        intent.putExtra("client", client.getPublic_id());
        startActivity(intent);
    }

}
