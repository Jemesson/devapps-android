package com.cesar.br.foodlovers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesar.br.foodlovers.adapter.FoodAdapter
import com.cesar.br.foodlovers.domain.Food
import com.cesar.br.foodlovers.listener.SwipeListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        const val MAIN_ACTIVITY_FOOD_EXTRA_ID = "FOOD_EXTRA_ID"
        const val LAUNCH_SECOND_ACTIVITY_REQUEST_CODE = 1
    }

    private var mFoodList = arrayListOf<Food>(

    )

    private val mFoodAdapter = FoodAdapter(this, mFoodList, this::onFoodClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (getFoodBackground() != "") {
            constraintLayout.setBackgroundColor(Color.BLUE)
        }

        setupRecyclerView()
        pressAddButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LAUNCH_SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val food = data?.getParcelableExtra<Food>(FoodDetailActivity.RESULT)
                if (food != null) {
                    mFoodList[food.id] = food
                    mFoodAdapter.notifyItemChanged(food.id)
                }
            }
        }
    }

    private fun onFoodClickListener(food: Food) {
        val intent = Intent(this, FoodDetailActivity::class.java)
        intent.putExtra(MAIN_ACTIVITY_FOOD_EXTRA_ID, food)

        startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY_REQUEST_CODE)
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mFoodAdapter
        }

        val swipeListener = SwipeListener(this@MainActivity, mFoodAdapter, mFoodList)
        ItemTouchHelper(swipeListener).attachToRecyclerView(recyclerView)
    }

    private fun pressAddButton() {
        btnAddFood.setOnClickListener {
            val type = foodsSpinner.selectedItem.toString()
            val name = editFoodName.text.toString()

            if (name.isNotEmpty()) {
                val imagePos = resources.getStringArray(R.array.food_types).indexOf(type)
                val food = Food(
                    id = mFoodList.lastIndex + 1,
                    type = type,
                    name = name,
                    quantity = 1,
                    price = Food.calculateRandomPrice(),
                    img = imagePos
                )

                mFoodList.add(food)
                mFoodAdapter.notifyItemInserted(mFoodList.lastIndex)

                editFoodName.text.clear()
                editFoodName.clearFocus()
            } else {
                Toast.makeText(this, getString(R.string.food_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFoodBackground(): String {
        return Class.forName("android.os.SystemProperties").let {
            it.getDeclaredMethod(
                "get",
                String::class.java
            ).invoke(null, "food.background") as String
        }
    }
}
