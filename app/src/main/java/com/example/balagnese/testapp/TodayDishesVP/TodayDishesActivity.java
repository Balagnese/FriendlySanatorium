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

        //breakfast
        List<DishesGroup> notSelectedBreakfast = breakfast.getNot_selected();
//        for (DishesGroup dishesGroup:
//                notSelectedBreakfast) {
//            List<Dish> dishes = dishesGroup.getDishes();
//            for (Dish dish: dishes) {
//                String dishName = dish.getName();
//            }
//        }

        List<ClientSelectedDish> selectedBreakfast = breakfast.getSelected();
        for (ClientSelectedDish selectedDish: selectedBreakfast) {
            Dish dish = selectedDish.getDish();
            DishesGroup group = selectedDish.getDishes_group();
            DishesGroup groupForShow = selectDish(dish, group);
            showDishesGroup(groupForShow);
        }

        TextView supperTextView = new TextView(this);
        supperTextView.setText("Обед");
        supperTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(supperTextView);

        //supper
        List<ClientSelectedDish> selectedSupper = supper.getSelected();
        for (ClientSelectedDish selectedDish:
             selectedSupper) {
            Dish dish = selectedDish.getDish();
            DishesGroup group = selectedDish.getDishes_group();
            DishesGroup groupForShow = selectDish(dish, group);
            showDishesGroup(groupForShow);

        }

        TextView lunchTextView = new TextView(this);
        lunchTextView.setText("Ужин");
        lunchTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(lunchTextView);

        //lunch
        List<ClientSelectedDish> selectedLunch = lunch.getSelected();
        for (ClientSelectedDish selectedDish:
             selectedLunch) {
            Dish dish = selectedDish.getDish();
            DishesGroup group = selectedDish.getDishes_group();
            DishesGroup groupForShow = selectDish(dish, group);
            showDishesGroup(groupForShow);
        }



    }

    public DishesGroup selectDish(Dish dish, DishesGroup dishesGroup){
        List<Dish> dishes = dishesGroup.getDishes();
        for (Dish d: dishes) {
            if (d.getDish_id() == dish.getDish_id())
                d.select();
        }
        return dishesGroup;
    }

    public void showDishesGroup(DishesGroup dishesGroup){
        String dishesGroupName = dishesGroup.getName();
        TextView name = new TextView(this);
        name.setText(dishesGroupName);
        name.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
        layout.addView(name);

        RadioGroup radioGroup = new RadioGroup(this);


        for (Dish dish:
             dishesGroup.getDishes()) {
            RadioButton newRadioButton = new RadioButton(this);
            newRadioButton.setText(dish.getName());
            if (dish.isSelected())
                newRadioButton.setChecked(true);
            //newRadioButton.setEnabled(false);
            newRadioButton.setClickable(false);
            newRadioButton.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
            radioGroup.addView(newRadioButton);
        }
        layout.addView(radioGroup);
    }

    @Override
    public void navigateToClientActivity() {

    }
}

