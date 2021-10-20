package com.cesar.br.foodlovers.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val id: Int = 1,
    val type: String,
    val name: String,
    var price: Double = 0.0,
    var quantity: Int = 0,
    var img: Int = 0
): Parcelable
