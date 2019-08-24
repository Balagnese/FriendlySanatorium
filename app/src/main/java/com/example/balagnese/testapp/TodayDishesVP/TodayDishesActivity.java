package com.example.balagnese.testapp.TodayDishesVP;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.DataTypes.ClientMealSelection;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDish;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.DataTypes.Dish;
import com.example.balagnese.testapp.DataTypes.DishesGroup;
import com.example.balagnese.testapp.R;

import java.util.List;

public class TodayDishesActivity extends AppCompatActivity implements TodayDishesView {

    TodayDishesPresenter cdp;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dishes);
        Intent intent = getIntent();
        String publicId = intent.getStringExtra("client");
        cdp = new TodayDishesPresenter(this, publicId);

        layout = findViewById(R.id.todayMenuLayout);

    }

    @Override
    public void showClientMenu(ClientSelectedDishModel menu) {
        ClientSelectedDishModel m = menu;
        ClientMealSelection breakfast = m.getBreakfast();
        ClientMealSelection supper = m.getSupper();
        ClientMealSelection lunch = m.getSupper();

        TextView breakfastTextView = new TextView(this);
        breakfastTextView.setText("Завтрак");
        breakfastTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(breakfastTextView);

        List<ClientSelectedDish> selectedBreakfast = breakfast.getSelected();
        showSelectedMenu(selectedBreakfast);

        TextView supperTextView = new TextView(this);
        supperTextView.setText("Обед");
        supperTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(supperTextView);

        //supper
        List<ClientSelectedDish> selectedSupper = supper.getSelected();
        showSelectedMenu(selectedSupper);

        TextView lunchTextView = new TextView(this);
        lunchTextView.setText("Ужин");
        lunchTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(lunchTextView);

        //lunch
        List<ClientSelectedDish> selectedLunch = lunch.getSelected();
        showSelectedMenu(selectedLunch);

    }

    public void showSelectedMenu(List<ClientSelectedDish> selectedMeal){
        for (ClientSelectedDish selectedDish:
                selectedMeal) {
            String name = selectedDish.getDishes_group().getName();
            TextView nameText = new TextView(this);
            nameText.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
            nameText.setText(name);
            layout.addView(nameText);
            RadioGroup radioGroup = new RadioGroup(this);
            DishesGroup group = selectedDish.getDishes_group();
            for (Dish dish:
                    group.getDishes()) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(dish.getName());
                if (dish.getDish_id() == selectedDish.getDish().getDish_id()) {
                    dish.select();
                    newRadioButton.setChecked(true);
                }
                newRadioButton.setClickable(false);
                newRadioButton.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
                radioGroup.addView(newRadioButton);

            }

            layout.addView(radioGroup);
        }

    }

    @Override
    public void navigateToClientActivity() {

    }
}

