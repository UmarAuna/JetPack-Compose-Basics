package com.jetpack.myapplication.planets

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.myapplication.ui.theme.HelloWorldTheme

@Composable
fun PlanetScreen(planet: Planets) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Image(
            painter = painterResource(id = planet.icon),
            contentDescription = planet.planetName,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Text(
                planet.planetName,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h5,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            PlanetDetailText("Surface Temperature", planet.surfaceTemperature)
            PlanetDetailText("Discovery", planet.discovery)
            PlanetDetailText("Named", planet.originOfName)
            PlanetDetailText("Diameter", planet.diameter)
            PlanetDetailText("Orbit", planet.orbit)
            PlanetDetailText("Days", planet.days)
            PlanetDetailText("Moon", planet.moon.toString())
        }
    }
}

@Composable
fun PlanetDetailText(planetHolder: String, planetText: String, modifier: Modifier = Modifier) {
    Spacer(modifier.height(2.dp))
    Divider()
    Spacer(modifier.height(5.dp))

    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            planetHolder,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSurface,
            fontFamily = FontFamily.SansSerif
        )
    }
    Spacer(modifier.height(2.dp))
    Text(
        planetText,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body1,
        fontFamily = FontFamily.SansSerif
    )
    Spacer(modifier.height(5.dp))
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
fun PlanetDetailPreview() {
    HelloWorldTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val planets = PlanetData.singlePlanet
            PlanetScreen(planets)
        }
    }
}
