package com.cesar.br.foodlovers

import com.cesar.br.foodlovers.domain.Food
import org.junit.Test

class FoodUnitTest {

    @Test
    fun `When a food is created, the id is 1`(){
        val firstId = 1;
        val food = Food(type = "xablau", name = "xablau")

        assert(food.id == firstId)
    }

    @Test
    fun `When a food is created, the type cannot be null`(){
        val nullType = null
        val food = Food(type = "xablau", name = "")

        assert(food.type != nullType)
    }

    @Test
    fun `When a food is created, the name cannot be null`(){
        val nullName = null
        val food = Food(type = "", name = "xablau")

        assert(food.name != nullName)
    }

    @Test
    fun `When a food is created, the quantity is zero`(){
        val zeroQuantity = 0
        val food = Food(type = "xablau", name = "xablau")

        assert(food.quantity == zeroQuantity)
    }

    @Test
    fun `When a food is created, the image counter is 0`(){
        val imgCounter = 0
        val food = Food(type = "xablau", name = "xablau")

        assert(food.quantity == imgCounter)
    }

    @Test
    fun `When a food is created, the price value must be between 1 and 30`(){
        val food = Food(
            type = "xablau",
            name = "xablau",
            price = Food.calculateRandomPrice()
        )

        assert(food.price in 1.0..30.0)
    }


    @Test
    fun `When a food price changes, the total price must multiply with the quantity`(){
        val total = 60.0
        val food = Food(
            type = "xablau",
            name = "xablau",
            price = 30.0,
            quantity = 2
        )

        assert(food.calculateTotalPrice() == total)
    }
}
