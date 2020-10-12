package com.cesar.br.foodlovers.listener

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cesar.br.foodlovers.R
import com.cesar.br.foodlovers.adapter.FoodAdapter
import com.cesar.br.foodlovers.domain.Food

class SwipeListener(
    private val context: Context,
    private val adapter: FoodAdapter,
    private val foods: ArrayList<Food>
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val food = foods[viewHolder.adapterPosition]

        foods.remove(food)
        adapter.notifyItemRemoved(viewHolder.adapterPosition)

        Toast.makeText(
            context,
            context.getString(R.string.food_removed, food.name),
            Toast.LENGTH_LONG
        ).show()
    }
}
