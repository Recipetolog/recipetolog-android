package ru.art241111.dish_recipes.adapters.dishesRecyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.art241111.dish_recipes.view.sharedFragments.protocols.OnDataEnd
import ru.art241111.dish_recipes.view.sharedFragments.protocols.OnItemClickListener
import ru.art241111.dish_recipes.data.FullDish
import ru.art241111.dish_recipes.databinding.RecyclerViewItemBinding
import ru.art241111.dish_recipes.view.sharedFragments.protocols.onClickFavoriteButton

/**
 * RecyclerView adapter.
 * @author Artem Geraimov.
 */
class DishesRecyclerViewAdapter(private var items: List<FullDish>,
                                private var itemListener: OnItemClickListener,
                                private var favoriteButtonListener: onClickFavoriteButton,
                                private var end: OnDataEnd)
                                : RecyclerView.Adapter<DishesRecyclerViewAdapter.ViewHolder>() {
    /**
     * link data.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(items[position], itemListener, favoriteButtonListener)

    /**
     * Set size
     */
    override fun getItemCount(): Int = items.size

    /**
     * Create items.
     */
    class ViewHolder(private var binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dish: FullDish, listener: OnItemClickListener?, favoriteButtonListener:onClickFavoriteButton?) {
            binding.dish = dish
            if (listener != null) {
                binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
            }

            if (favoriteButtonListener != null) {
                binding.ivFavorite.setOnClickListener {
                    favoriteButtonListener.onClickFavoriteButton(position = layoutPosition)
                    binding.invalidateAll()
                }
            }
            binding.executePendingBindings()
        }
    }

    /**
     * Create new item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    /**
     * Data refresh.
     */
    fun replaceData(arrayList: List<FullDish>) {
        items = arrayList
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutPosition = holder.layoutPosition

        if((layoutPosition > (items.size - 2))){
            end.onDataEnd()
        }
    }
}