package com.jetpack.myapplication.planets

import android.app.Activity
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.media.Image
import android.view.Surface
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.myapplication.R
import com.jetpack.myapplication.planets.ui.theme.PlanetTheme
import org.w3c.dom.Text

@Composable
fun PlanetScreen(planet: Planets) {
    val scrollState = rememberScrollState()
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        IconButton(onClick = {
            activity?.finish()
        }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = null)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .verticalScroll(scrollState)
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
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                PlanetDetailText(stringResource(R.string.surface_temperature), planet.surfaceTemperature)
                PlanetDetailText(stringResource(R.string.discovery), planet.discovery)
                PlanetDetailText(stringResource(R.string.named), planet.originOfName)
                PlanetDetailText(stringResource(R.string.diameter), planet.diameter)
                PlanetDetailText(stringResource(R.string.orbit), planet.orbit)
                PlanetDetailText(stringResource(R.string.days), planet.days)
                PlanetDetailText(stringResource(R.string.moon), planet.moon.toString())
            }
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
            fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
        )
    }
    Spacer(modifier.height(2.dp))
    Text(
        planetText,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body1,
        fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif
    )
    Spacer(modifier.height(5.dp))
}

@Preview(
    showBackground = true,
    //  showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
@Preview(
    showBackground = true,
    //  showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
fun PlanetDetailPreview() {
    PlanetTheme {
        // A surface container using the 'background'color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val planets = PlanetData.singlePlanet
            PlanetScreen(planets)
        }
    }
}

@Preview(
    showBackground = true,
    //  showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@ExperimentalAnimationApi
@Composable
fun JetpackCompose() {
    Card {
        var expanded by remember { mutableStateOf(false) }
        Column(Modifier.padding(16.dp).clickable { expanded = !expanded }) {
            Image(painterResource(id = R.drawable.ic_planet_neptune), contentDescription = null)
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = "Neptune",
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}
