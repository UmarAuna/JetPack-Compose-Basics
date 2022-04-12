package com.jetpack.myapplication.todoRoom

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jetpack.myapplication.planets.ui.theme.HelloWorldTheme
import kotlinx.coroutines.launch
import java.util.*

class TodoDetailView : ComponentActivity() {

    private val todo: Todo by lazy {
        intent?.getSerializableExtra("TODO") as Todo
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloWorldTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    GetScaffoldBody()
                }
            }
        }
    }

    @Composable
    private fun GetScaffoldBody() {

        val scaffoldState: ScaffoldState = rememberScaffoldState(
            snackbarHostState = SnackbarHostState()
        )

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { todo.fullName?.let { Text(text = it.take(10), color = Color.White) } },
                    backgroundColor = Color(0xFFFDA433),
                )
            },
            content = { DetailedView(scaffoldState) },
            backgroundColor = Color(0xFFBEEFF5),
        )
    }

    @Composable
    private fun DetailedView(scaffoldState: ScaffoldState) {
        val context = LocalContext.current
        val textState = remember { mutableStateOf(todo.notes) }
        val scope = rememberCoroutineScope()
        val model: TodoViewModel = viewModel(
            factory = TodoViewModelFactory(
                context.applicationContext as Application
            )
        )

        Surface(
            modifier = Modifier
                .width(650.dp)
                .height(350.dp)
                .padding(5.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                todo.notes?.let { Text(text = it) }

                textState.value?.let { text ->
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp,),

                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFF4DB6AC),
                            focusedIndicatorColor = Color.Transparent, // hide the indicator
                        ),
                        value = text, onValueChange = { textState.value = it }, placeholder = {
                            Text(text = "Enter Your Notes")
                        },
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF81C784)),
                    onClick = {
                        if (textState.value?.isEmpty() == true) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "TextField is empty",
                                )
                            }
                        } else {
                            todo.id?.let { id ->
                                textState.value?.let { text ->
                                    model.updateData(
                                        id,
                                        text
                                    )
                                }
                            }

                           /* model.update(
                                Todo(
                                    todo.id,
                                    UUID.randomUUID().toString(),
                                    textState.value
                                )
                            )*/
                            scope.launch {
                                textState.value = ""
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Notes updated",
                                )
                            }
                        }
                    }
                ) {
                    Text(text = "Update Note")
                }
            }
        }
    }
}
