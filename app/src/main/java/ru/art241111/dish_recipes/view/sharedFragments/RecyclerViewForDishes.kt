package ru.art241111.dish_recipes.view.sharedFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.art241111.dish_recipes.R
import ru.art241111.dish_recipes.adapters.dishesRecyclerViewAdapter.DishesRecyclerViewAdapter
import ru.art241111.dish_recipes.view.sharedFragments.protocols.OnDataEnd
import ru.art241111.dish_recipes.view.sharedFragments.protocols.OnItemClickListener
import ru.art241111.dish_recipes.data.FullDish
import ru.art241111.dish_recipes.databinding.FragmentRecyclerViewForDishesBinding
import ru.art241111.dish_recipes.models.DishRepository
import ru.art241111.dish_recipes.view.sharedFragments.protocols.onClickFavoriteButton
import ru.art241111.dish_recipes.view.AppActivity
import ru.art241111.dish_recipes.view.screenViewDish.fragments.IngredientsAndRecipeInfoFragment
import ru.art241111.dish_recipes.view.sharedFragments.protocols.onLoadDishes
import ru.art241111.dish_recipes.view_models.SearchDishViewModel

class RecyclerViewForDishes: Fragment(), OnItemClickListener, OnDataEnd, onClickFavoriteButton, onLoadDishes {
    private lateinit var binding: FragmentRecyclerViewForDishesBinding
    private lateinit var viewModel: SearchDishViewModel

    private lateinit var onDataEnd: OnDataEnd
    private lateinit var onClickFavoriteButton: onClickFavoriteButton
    private lateinit var onLoadDishes: onLoadDishes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        arguments?.let {
//            onDataEnd = it.get
//            dish = it.getParcelable(ARG_SELECTED_DISH)!!
        }

        viewModel = ViewModelProviders.of(activity as AppActivity).get(SearchDishViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recycler_view_for_dishes, container, false)

        binding.executePendingBindings()

        // Customization RecycleView: set layoutManager, adapter, data.
        customizationRecycleView()

        loadDishes()

        return binding.root
    }

    override fun loadDishes() {
        viewModel.loadDishesWhenScreenCreate()
    }

    /**
     * Customization RecycleView: set layoutManager, adapter, data.
     */
    private fun customizationRecycleView() {
        val dishesRecyclerViewAdapter =
                DishesRecyclerViewAdapter(arrayListOf(),
                        this,
                        this,
                        this)

        binding.rvDish.layoutManager = LinearLayoutManager(activity)
        binding.rvDish.adapter = dishesRecyclerViewAdapter
        viewModel.dishes.observe(activity as AppActivity,
                Observer{
                    it?.let{ dishesRecyclerViewAdapter.replaceData(it.toList())}
                })
    }

    /**
     * This method works when the user clicks on an element RecycleView.
     * @param position - the position of the item on which the user clicked.
     */
    override fun onItemClick(position: Int) {
        val dish: FullDish? = viewModel.dishes.value?.elementAt(position)
        val bundle = bundleOf("selected_dish" to dish)

        findNavController().navigate(R.id.viewDishActivity, bundle)
    }

    /**
     * This method works when the user reached the end of the recycler view.
     */
    override fun onDataEnd() {
        viewModel.loadDishesWhenDataEnd()
    }

    /**
     * If user click on favorite button on recycler view
     * @param position - position of element
     */
    override fun onClickFavoriteButton(position: Int) {
        val dish: FullDish? = viewModel.dishes.value?.elementAt(position)

        if (dish != null) {
            dish.isFavorite = !dish.isFavorite
            if(dish.isFavorite){
                DishRepository(null).addFavoriteDishes(dish)
            } else {
                DishRepository(null).removeFavoriteDishes(dish)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RecyclerViewForDishesFragment.
         */
        @JvmStatic
        fun newInstance(onDataEnd: OnDataEnd,
                        onClickFavoriteButton: onClickFavoriteButton,
                        onLoadDishes: onLoadDishes) =
                    IngredientsAndRecipeInfoFragment().apply {
                        arguments = Bundle().apply {
//                            putParcelable("onLoadDishes",onLoadDishes)
//                            putParcelable("onItemClickListener", onDataEnd)
//                            putParcelable("onItemClickListener", onClickFavoriteButton)
                        }
                    }
    }
}