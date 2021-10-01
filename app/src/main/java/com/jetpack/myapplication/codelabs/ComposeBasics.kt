package com.jetpack.myapplication.codelabs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.myapplication.ui.theme.HelloWorldTheme

class ComposeBasics : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                // Greeting2("Android")
                MyScreenContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    HelloWorldTheme {
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(100) { "Hello Android #${it + 1}" }) {
    val counterState = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxHeight()) {
        NameList(names, modifier = Modifier.weight(1f))
        Counter(
            count = counterState.value,
            updateCount = { newCount ->
                counterState.value = newCount
            }
        )
    }

    /*Column {
        Greeting2("Android")
        Divider(color = Color.Black)
        Greeting2("There")
    }*/
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting2(name = name)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text("I have been clicked $count times")
    }
}

@Composable
fun Greeting2(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)
    // Modifier parameters tell a UI element how to lay out, display, or behave within its parent layout. Modifiers are regular Kotlin objects.
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected })
    )
}

@Composable
fun Greeting3(name: String) {
    // Modifier parameters tell a UI element how to lay out, display, or behave within its parent layout. Modifiers are regular Kotlin objects.
    Text(
        text = "Hello $name!",
        modifier = Modifier.padding(24.dp),
        style = MaterialTheme.typography.body1.copy(color = Color.Black)
        // style = MaterialTheme.typography.h1
        // color = MaterialTheme.colors.surface
    )
}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview2() {
    MyApp {
        Greeting3("Android")
    }
}

@Preview(showBackground = true, name = "Screen Content Preview")
@Composable
fun ScreenContentPreview2() {
    MyApp {
        MyScreenContent()
    }
}
