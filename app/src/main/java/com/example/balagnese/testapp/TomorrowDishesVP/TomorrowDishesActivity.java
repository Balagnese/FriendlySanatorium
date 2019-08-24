package com.example.balagnese.testapp.TomorrowDishesVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.balagnese.testapp.DataTypes.ClientMealSelection;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDish;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.DataTypes.Dish;
import com.example.balagnese.testapp.DataTypes.DishesGroup;
import com.example.balagnese.testapp.R;

import java.util.List;

public class TomorrowDishesActivity extends AppCompatActivity implements TomorrowDishesView{

    LinearLayout layout;
    String publicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow_dishes);
        layout = findViewById(R.id.tomorrowMenuLayout);
        Intent intent = getIntent();
        publicId = intent.getStringExtra("client");
        TomorrowDishesPresenter tdp = new TomorrowDishesPresenter(this, publicId);

    }

    @Override
    public void showClientMenu(ClientSelectedDishModel menu) {

        ClientSelectedDishModel m = menu;
        ClientMealSelection breakfast = m.getBreakfast();
        ClientMealSelection supper = m.getSupper();
        ClientMealSelection lunch = m.getSupper();

        if (breakfast.getSelected().size() == 0)
        {
            TextView breakfastTextView = new TextView(this);
            breakfastTextView.setText("Завтрак");
            breakfastTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
            layout.addView(breakfastTextView);

            List<DishesGroup> notSelectedBreakfast = breakfast.getNot_selected();
            showNotSelectedMenu(notSelectedBreakfast);

            TextView supperTextView = new TextView(this);
            supperTextView.setText("Обед");
            supperTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
            layout.addView(supperTextView);

            List<DishesGroup> notSelectedSupper = supper.getNot_selected();
            showNotSelectedMenu(notSelectedSupper);

            TextView lunchTextView = new TextView(this);
            lunchTextView.setText("Ужин");
            lunchTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
            layout.addView(lunchTextView);

            List<DishesGroup> notSelectedLunch = lunch.getNot_selected();
            showNotSelectedMenu(notSelectedLunch);


        }
        else{
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

    }

    @Override
    public void navigateToClientActivity() {

    }

    public void showNotSelectedMenu(List<DishesGroup> dishesGroups){
        for (final DishesGroup group:
                dishesGroups) {
            String name = group.getName();
            TextView nameText = new TextView(this);
            nameText.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
            nameText.setText(name);
            layout.addView(nameText);

            RadioGroup radioGroup = new RadioGroup(this);
            final List<Dish> dishes = group.getDishes();
            for (Dish dish:
                    dishes) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(dish.getName());
                newRadioButton.setClickable(false);
                newRadioButton.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
                radioGroup.addView(newRadioButton);

            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    Dish checkedDish = dishes.get(checkedId);
                    //TODO сформировать тело для отправки запроса
                }
            });

            layout.addView(radioGroup);
        }
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
            final List<Dish> dishes = group.getDishes();
            for (Dish dish:
                    dishes) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(dish.getName());
                if (dish.getDish_id() == selectedDish.getDish().getDish_id()) {
                    dish.select();
                    newRadioButton.setChecked(true);
                }
                //newRadioButton.setClickable(false);
                newRadioButton.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
                radioGroup.addView(newRadioButton);

            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    Dish checkedDish = dishes.get(checkedId);

                }
            });

            layout.addView(radioGroup);
        }

    }
}
