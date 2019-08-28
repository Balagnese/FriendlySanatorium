package com.example.balagnese.testapp.ProceduresVP;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.balagnese.testapp.ClientVP.ClientActivity;
import com.example.balagnese.testapp.DataTypes.ClientProcedure;
import com.example.balagnese.testapp.ProcedureVP.ProcedureActivity;
import com.example.balagnese.testapp.R;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProceduresActivity extends AppCompatActivity implements ProceduresView {

    ProceduresPresenter pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedures);
        Intent intent = getIntent();
        String publicId = intent.getStringExtra("client");
        pp = new ProceduresPresenter(this, publicId);


        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pp.navigateToClientActivity();
            }
        });
    }

    @Override
    public void showProcedures(Map<String, List<ClientProcedure>> procedures) {

        TableLayout tableLayout = findViewById(R.id.ppprocedureTableLayout);

        TableRow header = new TableRow(this);

        TextView hname = new TextView(this);
        hname.setText("Название");
        hname.setGravity(Gravity.CENTER_HORIZONTAL);
        hname.setWidth(tableLayout.getWidth()/6);
        hname.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
        header.addView(hname);

        TextView htime = new TextView(this);
        htime.setText("Время");
        htime.setGravity(Gravity.CENTER_HORIZONTAL);
        htime.setWidth(tableLayout.getWidth()/6);
        htime.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
        header.addView(htime);

        TextView hplace = new TextView(this);
        hplace.setText("Место");
        hplace.setGravity(Gravity.CENTER_HORIZONTAL);
        hplace.setWidth(tableLayout.getWidth()/6);
        hplace.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
        header.addView(hplace);

        TextView hduration = new TextView(this);
        hduration.setText("Проведение (мин)");

        hduration.setGravity(Gravity.CENTER_HORIZONTAL);
        hduration.setWidth(tableLayout.getWidth()/4);
        hduration.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
        header.addView(hduration);

        tableLayout.addView(header);

        SortedSet<String> sortKeys = new TreeSet<>(procedures.keySet());

        for (String key:
                sortKeys) {
            TableRow titleRow = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            titleRow.setLayoutParams(lp);

//            String key = keys.get(i);
            TextView date = new TextView(this);
            date.setText("Дата: ".concat(key));
            date.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
            date.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            tableLayout.addView(date);

            List<ClientProcedure> procedureList = procedures.get(key);
            for (final ClientProcedure p: procedureList) {
                TableRow procedure = new TableRow(this);
                procedure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = 0;
                        pp.navigateToProcedureActivity(p);
                    }
                });

                TextView name = new TextView(this);
                name.setText(p.getProcedure().getName());
                //name.setWidth(tableLayout.getWidth()/3);
                name.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body1);
                procedure.addView(name);

                TextView time = new TextView(this);
                time.setText(p.getTimeForPrint());
                time.setGravity(Gravity.CENTER_HORIZONTAL);
                //time.setWidth(tableLayout.getWidth()/5);
                time.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body1);
                procedure.addView(time);

                TextView place = new TextView(this);
                place.setText(p.getPlace());
                place.setGravity(Gravity.CENTER_HORIZONTAL);
                //place.setWidth(tableLayout.getWidth()/4);
                place.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body1);
                procedure.addView(place);

                TextView duration = new TextView(this);
                duration.setText(String.valueOf(p.getProcedure().getDuration()));
                duration.setGravity(Gravity.CENTER_HORIZONTAL);
                //duration.setWidth(tableLayout.getWidth()/4);
                duration.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body1);
                procedure.addView(duration);

                tableLayout.addView(procedure);
            }

            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            tableRow.addView(textView);
            tableLayout.addView(tableRow);

        }
    }

    @Override
    public void navigateToHomeActivity() {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);

    }

    @Override
    public void navigateToProcedureActivity(ClientProcedure procedure) {
        Intent intent = new Intent(this, ProcedureActivity.class);
        intent.putExtra("id", procedure.getProcedure().getId());
        startActivity(intent);
    }
}
