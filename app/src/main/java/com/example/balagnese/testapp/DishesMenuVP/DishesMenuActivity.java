package com.example.balagnese.testapp.DishesMenuVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.balagnese.testapp.R;
import com.example.balagnese.testapp.TodayDishesVP.TodayDishesActivity;

public class DishesMenuActivity extends AppCompatActivity implements DishesMenuView{

    DishesMenuPresenter dmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_menu);
        Intent intent = getIntent();
        String publicId = intent.getStringExtra("client");
        dmp = new DishesMenuPresenter(this, publicId);

        Button today = findViewById(R.id.todayMenuButton);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dmp.navigateTodayDishesActivity();
            }
        });
    }

    @Override
    public void navigateToTomorrowDishesActivity(String publicId) {
//        TODO not today
//        Intent intent = new Intent(this, TodayDishesActivity.class);
//        intent.putExtra("client", publicId);
//        startActivity(intent);
    }

    @Override
    public void navigateTodayDishesActivity(String publicId) {
        Intent intent = new Intent(this, TodayDishesActivity.class);
        intent.putExtra("client", publicId);
        startActivity(intent);

    }
}
