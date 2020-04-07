package ru.art241111.dish_recipes.ui.mainActivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.art241111.dish_recipes.models.Dish;
import ru.art241111.dish_recipes.R;
import ru.art241111.dish_recipes.adapters.RecyclerViewAdapter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;

import static ru.art241111.dish_recipes.ui.mainActivity.createArray.addItemsToArray.addItemsToArrayList;
import static ru.art241111.dish_recipes.utils.constantSting.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    EditText et_ingredients;

    ArrayList<String> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingredients = new ArrayList<>();
        restoringValuesOfIngredients(savedInstanceState);

        et_ingredients = findViewById(R.id.et_ingredients);
        setListenerOnET(et_ingredients);

        setListenerOnButton((Button) findViewById(R.id.bt_add_ingredients));

        ArrayList<Dish> dishArrayList = new ArrayList<>();
        addItemsToArrayList(dishArrayList);

        recyclerView = findViewById(R.id.rv_dish);
        customizationRecyclerView(dishArrayList);
    }

    private void restoringValuesOfIngredients(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            ingredients = savedInstanceState.getStringArrayList("ingredients");
            assert ingredients != null;
            for (int i = 0; i < ingredients.size(); i++) {
                drawView(ingredients.get(i));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("ingredients", ingredients);
    }

    private void drawView(String ingredientName){
        final FlowLayout tableIngredients = findViewById(R.id.fl_list_ingredients);

        LayoutInflater inflater = getLayoutInflater();
        final View ingredient = inflater.inflate(R.layout.ingredient, null);

        customizationNewView(ingredientName, ingredient, tableIngredients);

        tableIngredients.addView(ingredient);
    }
    private void addIngredients(){
        String ingredientName = et_ingredients.getText().toString();
        ingredients.add(ingredientName);

        drawView(ingredientName);
        et_ingredients.setText("");
    }

    private void customizationNewView(final String ingredientName, final View ingredient, final FlowLayout tableIngredients) {
        TextView textView = ingredient.findViewById(R.id.tv_name_ingredient);
        textView.setText(ingredientName);

        ImageView imageView = ingredient.findViewById(R.id.btn_delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableIngredients.removeView(ingredient);
                ingredients.remove(ingredientName);
            }
        });
    }

    private void setListenerOnButton(Button bt_add_ingredients) {
        bt_add_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredients();
            }
        });
    }

    private void setListenerOnET(final EditText editText) {
        editText.setOnKeyListener(new View.OnKeyListener()  {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER))
                                          {
                                              // сохраняем текст, введенный до нажатия Enter в переменную
                                              addIngredients();
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );
    }

    private void customizationRecyclerView(ArrayList<Dish> dishArrayList) {
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerViewAdapter(dishArrayList, this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

}