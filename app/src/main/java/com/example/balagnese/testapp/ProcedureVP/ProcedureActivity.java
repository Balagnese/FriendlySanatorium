package com.example.balagnese.testapp.ProcedureVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.balagnese.testapp.ClientVP.ClientActivity;
import com.example.balagnese.testapp.DataTypes.ClientProcedure;
import com.example.balagnese.testapp.DataTypes.Procedure;
import com.example.balagnese.testapp.R;

public class ProcedureActivity extends AppCompatActivity implements ProcedureView{

    ProcedurePresenter pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedure);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        pp = new ProcedurePresenter(this, id);
        Button home = findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp.navigateToClientActivity();
            }
        });
    }

    @Override
    public void showProcedureInfo(Procedure procedure) {
        TextView name = findViewById(R.id.procedureNameTextView);
        name.setText(procedure.getName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Длительность процедуры: ").append(procedure.getDuration()).append(" минут\r\n");
        stringBuilder.append("\r\n").append(procedure.getDescription());
        TextView description = findViewById(R.id.descriptionTextView);
        description.setText(stringBuilder.toString());
    }

    @Override
    public void navigateToClientActivity() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }
}
