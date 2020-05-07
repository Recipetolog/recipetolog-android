package ru.art241111.dish_recipes.view.dishActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.art241111.dish_recipes.R
import ru.art241111.dish_recipes.data.FullDish
import ru.art241111.dish_recipes.databinding.ActivityDishBinding


class DishActivity : AppCompatActivity() {
    // binding with layout
    private lateinit var binding: ActivityDishBinding

    /**
     * Take data from intent.
     * If intent null, return empty object
     */
    private fun getDataFromIntent(): FullDish? {
        val intent = intent
        return if (intent != null) {
            intent.getParcelableExtra("Dish")
        } else FullDish()
    }

    /**
     * Start activity, draw layout
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // A binding with layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dish)

        // Loading dish data to layout
        val dish = getDataFromIntent()
        binding.fullDish = dish

        // Setup and customization tab host
        setupTabHost()
    }

    /**
     * Setup and customization tab host
     */
     private fun setupTabHost() {
        binding.tabHost.setup()

        var tabSpec = binding.tabHost.newTabSpec("tag1")
        tabSpec.setContent(R.id.tab1)
        tabSpec.setIndicator("Ингредиенты")
        binding.tabHost.addTab(tabSpec)

        tabSpec = binding.tabHost.newTabSpec("tag2")
        tabSpec.setContent(R.id.tab2)
        tabSpec.setIndicator("Рецепт")
        binding.tabHost.addTab(tabSpec)
    }
}