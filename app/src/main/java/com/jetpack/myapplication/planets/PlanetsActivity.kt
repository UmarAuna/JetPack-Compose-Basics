package com.jetpack.myapplication.planets

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.myapplication.R
import com.jetpack.myapplication.planets.ui.theme.PlanetTheme
import java.util.*

class PlanetsActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // PlanetsList(planets = PlanetData.planetsList)
                    PlanetsList(planets = PlanetData.planetsListVector)
                }
            }
        }
    }
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@ExperimentalMaterialApi
@Composable
fun PlanetsList(planets: List<Planets>) {
    Column() {
        Text(
            text = stringResource(R.string.planets),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(10.dp)
        )

        val planet = planets.maxByOrNull { it.moon }

        Text(
            text = getGreetingMessage(planet),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(10.dp)
        )

        LazyColumn() {
            items(planets) { planet ->
                // PlanetView(planet)
                // PlanetsListVector(planet)
                PlanetImageList(planet)
            }
        }
    }
}

@Composable
private fun getGreetingMessage(planet: Planets?): String {
    val c = Calendar.getInstance()
    val timeOfDay = c.get(Calendar.HOUR_OF_DAY)
    return when (timeOfDay) {
        in 0..11 -> {
            stringResource(R.string.good_morning)
        }
        in 12..15 -> {
            stringResource(R.string.good_afternoon)
        }
        in 16..20 -> {
            stringResource(R.string.good_evening, planet?.planetName!!, planet.moon)
        }
        in 21..23 -> {
            stringResource(R.string.good_night)
        }
        else -> stringResource(R.string.hello)
    }
}

@ExperimentalMaterialApi
@Composable
fun PlanetsListVector(planet: Planets) {

    val context = LocalContext.current
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clickable { /*setShowDialog(true)*/ }
                .padding(vertical = 10.dp, horizontal = 20.dp),
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                val intent = Intent(context, PlanetDetails::class.java)
                intent.putExtra("PLANET", planet)
                context.startActivity(intent)
            }
        ) {
            Row {
                Image(
                    painter = painterResource(id = planet.icon),
                    contentDescription = planet.planetName,
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier.fillMaxHeight(fraction = 0.5f)) {
                        Text(
                            text = planet.planetName,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h6,
                        )
                    }
                    Box(modifier = Modifier.fillMaxHeight(fraction = 0.5f)) {
                        Text(
                            text = "Days: ${planet.days}",
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
        DialogDemo(showDialog, setShowDialog, planet)
    }
}

@ExperimentalMaterialApi
@Composable
fun PlanetImageList(planet: Planets) {

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = 10.dp, horizontal = 20.dp),
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {
                val intent = Intent(context, PlanetDetails::class.java)
                intent.putExtra("PLANET", planet)
                context.startActivity(intent)
            }
        ) {
            Row {
                /*Image(
                    painter = painterResource(id = planet.icon),
                    contentDescription = planet.planetName,
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                )*/
                Image(
                    painter = painterResource(id = planet.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(84.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(modifier = Modifier.fillMaxHeight(fraction = 0.5f)) {
                        Text(
                            text = planet.planetName,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h6,
                        )
                    }
                    Box(modifier = Modifier.fillMaxHeight(fraction = 0.5f)) {
                        Text(
                            text = "Days: ${planet.days}",
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetView(planet: Planets) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Surface(shape = RoundedCornerShape(10.dp), elevation = 8.dp) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = planet.icon),
                    contentDescription = planet.planetName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop

                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        planet.planetName,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h5,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Surface Temperature : ${planet.surfaceTemperature}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Discovery : ${planet.discovery}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Named : ${planet.originOfName}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Diameter : ${planet.diameter}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Orbit : ${planet.orbit}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Days : ${planet.days}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Moon : ${planet.moon}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }
    }
}

@Composable
fun DialogDemo(showDialog: Boolean, setShowDialog: (Boolean) -> Unit, planet: Planets) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text(planet.planetName)
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Dismiss")
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "Surface Temperature : ${planet.surfaceTemperature}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Discovery : ${planet.discovery}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Named : ${planet.originOfName}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Diameter : ${planet.diameter}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Orbit : ${planet.orbit}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Days : ${planet.days}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        "Moon : ${planet.moon}",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Image(
                        painter = painterResource(id = planet.icon),
                        contentDescription = planet.planetName,
                        modifier = Modifier.clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        )
    }
}

/*@Composable
fun AndroidVersion(menus: List<String> = listOf()) {
    Column {
        for (name in menus) {
            Text(text = name, modifier = Modifier.padding(16.dp))
            Divider(color = Color.Black)
        }
    }
}*/

@Composable
fun Greeting4(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.padding(16.dp))
}

@ExperimentalMaterialApi
@Preview(
    showBackground = true,
    // showSystemUi = true,
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
fun DefaultPreview3() {
    PlanetTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            // PlanetsList(planets = PlanetData.planetsList)
            PlanetsList(planets = PlanetData.planetsListVector)
        }
    }
  /*  MyApp {
        *//*val list = listOf("Lollipop", "Marshmallow", "Nougat",
            "Pie", "Oreo", "Android 10", "Android 11", "Android 12")
        AndroidVersion(list)*//*
    }*/
}
