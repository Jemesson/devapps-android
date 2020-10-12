package com.cesar.br.foodlovers.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val id: Int,
    val type: String,
    val name: String,
    var price: Double,
    var quantity: Int = 0,
    var img: Int = 0
): Parcelable
