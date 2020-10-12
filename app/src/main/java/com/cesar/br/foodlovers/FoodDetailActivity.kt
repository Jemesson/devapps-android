package com.cesar.br.foodlovers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cesar.br.foodlovers.domain.Food
import kotlinx.android.synthetic.main.activity_food_detail.*
import java.util.*

class FoodDetailActivity : AppCompatActivity() {
    companion object {
        const val RESULT = "result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        val food = intent.extras?.getParcelable<Food>(MainActivity.MAIN_ACTIVITY_FOOD_EXTRA_ID)

        if (food != null) {
            val images = applicationContext.resources.obtainTypedArray(R.array.food_images)
            imgFoodDetail.setImageDrawable(images.getDrawable(food.img))
            images.recycle()

            lblFoodType.text = food.type
            lblFoodName.text = food.name

            Locale.setDefault(Locale.US)
            edtFoodPrice.setText(String.format("%.2f", food.price))
            edtFoodQt.setText(food.quantity.toString())
        }

        handlePressSave(food)
    }

    private fun handlePressSave(food: Food?) {
        btnSave.setOnClickListener {
            val price = if (edtFoodPrice.text.isNullOrEmpty()) 1.0 else edtFoodPrice.text.toString()
                .toDouble()
            val qtd = if (edtFoodQt.text.isNullOrEmpty()) 1 else edtFoodQt.text.toString().toInt()

            if (food != null) {
                food.price = price
                food.quantity = qtd
            }

            val intentBack = Intent(this, MainActivity::class.java)
            intentBack.putExtra(RESULT, food)
            setResult(RESULT_OK, intentBack)

            finish()
        }
    }
}
