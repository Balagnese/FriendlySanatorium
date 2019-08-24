package com.example.balagnese.testapp.ClientVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.balagnese.testapp.AuthVP.AuthActivity;
import com.example.balagnese.testapp.ChildVP.ChildActivity;
import com.example.balagnese.testapp.ClientInfo;
import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.DishesMenuVP.DishesMenuActivity;
import com.example.balagnese.testapp.ProceduresVP.ProceduresActivity;
import com.example.balagnese.testapp.R;

import java.util.LinkedList;
import java.util.List;

public class ClientActivity extends AppCompatActivity implements ClientView {
    ClientPresenter cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        cp = new ClientPresenter(this);

        Button logOutButton = findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.logOut();
            }
        });

        Button proceduresButton = findViewById(R.id.proceduresButton);
        proceduresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.navigateToClientProceduresActivity();
            }
        });

        Button menuButton = findViewById(R.id.clientMenuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cp.navigateToClientDishesActivity();
            }
        });
    }

    @Override
    public void showClientData(Client client) {
        TextView clientFIOTextView = findViewById(R.id.clientFIOTextView);
        clientFIOTextView.setText(ClientInfo.getInstance().getFIO(client));
    }


    @Override
    public void showClientChildrenList(final List<Client> children) {
        ListView childrenListView = findViewById(R.id.childrenListView);
        List<String> childrenFIO = new LinkedList<>();
        for (Client child:children) {
            childrenFIO.add(ClientInfo.getInstance().getFIO(child));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, childrenFIO);
        childrenListView.setAdapter(adapter);
        childrenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Client childClicked = children.get(position);
                cp.navigateToChildActivity(childClicked);
            }
        });
    }

    @Override
    public void navigateToChildActivity(Client child) {
        Intent intent = new Intent(this, ChildActivity.class);
        intent.putExtra("child", child.getPublic_id());
        startActivity(intent);
    }

    @Override
    public void navigateToAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToClientProceduresActivity() {
        Intent intent = new Intent(this, ProceduresActivity.class);
        intent.putExtra("client", "");
        startActivity(intent);
    }

    @Override
    public void navigateToDishesActivity() {
        Intent intent = new Intent(this, DishesMenuActivity.class);
        intent.putExtra("client", "");
        startActivity(intent);
        //int j = 0;
    }


}
