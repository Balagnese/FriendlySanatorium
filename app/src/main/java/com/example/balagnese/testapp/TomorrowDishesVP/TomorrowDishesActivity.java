package com.example.balagnese.testapp.TomorrowDishesVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balagnese.testapp.DataTypes.ClientMealSelection;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDish;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.DataTypes.Dish;
import com.example.balagnese.testapp.DataTypes.DishesGroup;
import com.example.balagnese.testapp.DataTypes.PostDish;
import com.example.balagnese.testapp.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TomorrowDishesActivity extends AppCompatActivity implements TomorrowDishesView{

    LinearLayout layout;
    String publicId;
    List<PostDish> postDishes = new ArrayList<>();
    ClientSelectedDishModel m;
    TomorrowDishesPresenter tdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow_dishes);
        layout = findViewById(R.id.tomorrowMenuLayout);
        Intent intent = getIntent();
        publicId = intent.getStringExtra("client");
        tdp = new TomorrowDishesPresenter(this, publicId);

    }

    public void showClientSelectedMenu(){

        Button manageButton = findViewById(R.id.manageButton);
        manageButton.setText("Изменить");
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClientMenuForSelect();
            }
        });

        ClientMealSelection breakfast = m.getBreakfast();
        ClientMealSelection supper = m.getSupper();
        ClientMealSelection lunch = m.getSupper();

        TextView breakfastTextView = new TextView(this);
        breakfastTextView.setText("Завтрак");
        breakfastTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(breakfastTextView);

        List<DishesGroup> notSelectedBreakfast = breakfast.getNot_selected();
        showNotSelectedMenu(notSelectedBreakfast, m.getDaily_menu_id(), "breakfast");

        TextView supperTextView = new TextView(this);
        supperTextView.setText("Обед");
        supperTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(supperTextView);

        List<DishesGroup> notSelectedSupper = supper.getNot_selected();
        showNotSelectedMenu(notSelectedSupper, m.getDaily_menu_id(), "supper");

        TextView lunchTextView = new TextView(this);
        lunchTextView.setText("Ужин");
        lunchTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(lunchTextView);

        List<DishesGroup> notSelectedLunch = lunch.getNot_selected();
        showNotSelectedMenu(notSelectedLunch, m.getDaily_menu_id(), "lunch");
    }

    public int countDishesGroup(){
        ClientMealSelection breakfast = m.getBreakfast();
        ClientMealSelection supper = m.getSupper();
        ClientMealSelection lunch = m.getSupper();
        List<DishesGroup> notSelectedBreakfast = breakfast.getNot_selected();
        List<DishesGroup> notSelectedSupper = supper.getNot_selected();
        List<DishesGroup> notSelectedLunch = lunch.getNot_selected();
        return notSelectedBreakfast.size() + notSelectedSupper.size() + notSelectedLunch.size();
    }

    public void showClientMenuForSelect(){

        Button manageButton = findViewById(R.id.manageButton);
        manageButton.setText("Сохранить");
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postDishes.size() == countDishesGroup())
                {
                    tdp.selectMenu(postDishes);
                    tdp.navigateToClientActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Заполните все поля",Toast.LENGTH_SHORT).show();
                }


            }
        });

        ClientMealSelection breakfast = m.getBreakfast();
        ClientMealSelection supper = m.getSupper();
        ClientMealSelection lunch = m.getSupper();

        TextView breakfastTextView = new TextView(this);
        breakfastTextView.setText("Завтрак");
        breakfastTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(breakfastTextView);

        List<ClientSelectedDish> selectedBreakfast = breakfast.getSelected();
        showSelectedMenu(selectedBreakfast, m.getDaily_menu_id(), "breakfast");

        TextView supperTextView = new TextView(this);
        supperTextView.setText("Обед");
        supperTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(supperTextView);

        //supper
        List<ClientSelectedDish> selectedSupper = supper.getSelected();
        showSelectedMenu(selectedSupper, m.getDaily_menu_id(), "supper");

        TextView lunchTextView = new TextView(this);
        lunchTextView.setText("Ужин");
        lunchTextView.setTextAppearance(this, R.style.TextAppearance_AppCompat_Large);
        layout.addView(lunchTextView);

        //lunch
        List<ClientSelectedDish> selectedLunch = lunch.getSelected();
        showSelectedMenu(selectedLunch, m.getDaily_menu_id(), "lunch");
    }

    @Override
    public void showClientMenu(ClientSelectedDishModel menu) {
        ClientSelectedDishModel m = menu;
        ClientMealSelection breakfast = m.getBreakfast();

        if (breakfast.getSelected().size() == 0)
        {
            showClientSelectedMenu();
        }
        else{
            showClientMenuForSelect();
        }

    }

    @Override
    public void navigateToClientActivity() {

    }

    public void showNotSelectedMenu(List<DishesGroup> dishesGroups, final int dailyMenuId, final String mealTag){
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
                public void onCheckedChanged(RadioGroup radioGroup1, int checkedId) {
                    Dish checkedDish = dishes.get(checkedId - 1);
                    PostDish postDish = new PostDish(dailyMenuId, mealTag,
                            group.getDishes_group_id(), checkedDish.getDish_id());
                    postDishes.add(postDish);
                }
            });

            layout.addView(radioGroup);
        }
    }

    public void showSelectedMenu(List<ClientSelectedDish> selectedMeal, final int dailyMenuId, final String mealTag){
        for (ClientSelectedDish selectedDish:
                selectedMeal) {
            String name = selectedDish.getDishes_group().getName();
            TextView nameText = new TextView(this);
            nameText.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
            nameText.setText(name);
            layout.addView(nameText);
            final RadioGroup radioGroup = new RadioGroup(this);
            final DishesGroup group = selectedDish.getDishes_group();
            final List<Dish> dishes = group.getDishes();
            for (Dish dish:
                    dishes) {
                RadioButton newRadioButton = new RadioButton(this);
                newRadioButton.setText(dish.getName());
                if (dish.getDish_id() == selectedDish.getDish().getDish_id()) {
                    dish.select();{
                        newRadioButton.setChecked(true);
                    }

                }
                newRadioButton.setClickable(false);
                newRadioButton.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
                radioGroup.addView(newRadioButton);
            }
            layout.addView(radioGroup);
        }

    }
}
