package com.cesar.br.foodlovers.adapter

import android.content.Context
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cesar.br.foodlovers.R
import com.cesar.br.foodlovers.domain.Food
import kotlinx.android.synthetic.main.item_food.view.*
import java.util.*

class FoodAdapter(private val context: Context,
				  private val foods: List<Food>,
				  private val callback: (Food) -> Unit) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

	private val foodImages: TypedArray by lazy {
		context.resources.obtainTypedArray(R.array.food_images)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val itemView = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_food, parent, false)

		val vh = ViewHolder(itemView)
		vh.itemView.setOnClickListener {
			val food = foods[vh.adapterPosition]
			callback(food)
		}

		return vh
	}

	override fun getItemCount() = foods.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val food = foods[position]
		holder.foodImg.setImageDrawable(foodImages.getDrawable(food.img))
		holder.textFoodName.text = food.name

		Locale.setDefault(Locale.US)
		val subTotal = String.format("%.2f", food.calculateTotalPrice())
		holder.textFoodPrice.text = context.getString(R.string.food_subtotal, subTotal)
	}

	class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
		val foodImg : ImageView = itemView.iconFood
		val textFoodName: TextView = itemView.textFoodName
		val textFoodPrice: TextView = itemView.textFoodSubTotal
	}
}
