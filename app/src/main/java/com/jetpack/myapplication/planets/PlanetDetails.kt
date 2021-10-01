package com.jetpack.myapplication.planets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jetpack.myapplication.planets.ui.theme.PlanetTheme

class PlanetDetails : ComponentActivity() {

    private val planet: Planets by lazy {
        intent?.getSerializableExtra("PLANET") as Planets
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetTheme {
                PlanetScreen(planet)
            }
        }
    }
}
