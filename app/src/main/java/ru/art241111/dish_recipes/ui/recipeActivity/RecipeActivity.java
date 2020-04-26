package ru.art241111.dish_recipes.ui.recipeActivity;

import androidx.appcompat.app.AppCompatActivity;
import ru.art241111.dish_recipes.R;
import ru.art241111.dish_recipes.models.Dish;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {
    private Dish getDataFromIntent(){
        Intent intent = getIntent();
        if(intent != null){
            return intent.getParcelableExtra("Dish");
        } else return new Dish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setupTabHost();
        customizationScrollView();

        Dish dish = getDataFromIntent();
        setTextToTextView(dish);
    }

    private void setTextToTextView(Dish dish) {
        ImageView iv_dish = findViewById(R.id.iv_recipe_dish);
        TextView name = findViewById(R.id.tv_name_dish);
        TextView description = findViewById(R.id.tv_description_dish);
        TextView recipe = findViewById(R.id.tv_recipe_dish);
        TextView ingredients = findViewById(R.id.tv_ingredients);

        iv_dish.setImageResource(dish.getImageDish());
        name.setText(dish.getNameDish());
        description.setText(dish.getDescriptionDish());
        recipe.setText(dish.getRecipe());
        ingredients.setText(dish.getDescriptionDish());
    }

    private void setupTabHost() {
        TabHost tabHost = findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Ингредиенты");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Рецепт");
        tabHost.addTab(tabSpec);
    }


    private void customizationScrollView() {
        ScrollView scrollView = findViewById(R.id.scrollable);
        scrollView.fullScroll(View.FOCUS_UP);
    }
}
