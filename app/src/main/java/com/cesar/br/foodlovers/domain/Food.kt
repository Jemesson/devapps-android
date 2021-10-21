package com.cesar.br.foodlovers.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Food(
    val id: Int = 1,
    val type: String,
    val name: String,
    var price: Double = 0.0,
    var quantity: Int = 0,
    var img: Int = 0
): Parcelable {
    companion object {
        fun calculateRandomPrice(): Double {
            return Random.nextDouble(1.0, 30.0)
        }
    }

    fun calculateTotalPrice(): Double {
        return price.times(quantity)
    }
}
