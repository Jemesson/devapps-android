package com.cesar.br.foodlovers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
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
        const val MAIN_ACTIVITY_FOOD_STATE = "FOOD_STATE"
        const val LAUNCH_SECOND_ACTIVITY_REQUEST_CODE = 1
    }

    private var mFoodList = arrayListOf<Food>(

    )

    private val mFoodAdapter = FoodAdapter(this, mFoodList, this::onFoodClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

            if (isNameValid(name)) {
                val price = Random.nextDouble(1.0, 30.0)
                val typeIndex = resources.getStringArray(R.array.food_types).indexOf(type)
                val food = Food(mFoodList.lastIndex + 1, type, name, price, 1, typeIndex)

                mFoodList.add(food)
                mFoodAdapter.notifyItemInserted(mFoodList.lastIndex)

                editFoodName.text.clear()
                editFoodName.clearFocus()
            } else {
                Toast.makeText(this, getString(R.string.food_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isNameValid(name: String): Boolean = !name.isNullOrEmpty()
}
