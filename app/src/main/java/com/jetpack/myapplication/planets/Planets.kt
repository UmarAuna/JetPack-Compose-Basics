package com.jetpack.myapplication.planets

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Planets(
    val planetName: String,
    @DrawableRes val icon: Int,
    val surfaceTemperature: String,
    val discovery: String,
    val originOfName: String,
    val diameter: String,
    val orbit: String,
    val days: String,
    val moon: Int
) : Serializable
