package com.jetpack.myapplication.koinRestAPI.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jetpack.myapplication.R
import com.jetpack.myapplication.koinRestAPI.util.loadPicture
import com.jetpack.myapplication.koinRestAPI.viewmodel.PlanetHomeViewModel
import com.jetpack.myapplication.ui.theme.PlanetsTheme
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
class PlanetHomeView : ComponentActivity() {
    private val viewModel: PlanetHomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetsTheme {
                // A surface container using the 'background' color from the theme

                Surface(color =  Color.parse("#EBEBEB")) {
                    PlanetHomeScreen()
                }
            }
        }
    }

    fun Color.Companion.parse(colorString: String): Color =
        Color(color = android.graphics.Color.parseColor(colorString))

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun PlanetHomeScreen() {
        LaunchedEffect(Unit, block = {
            viewModel.getAllPlanets()
        })
        val observePlanet by viewModel.planetList.observeAsState(listOf())
        val observePlanetOffline by viewModel.observePlanet.observeAsState(listOf())
        val observeError by viewModel.showError.observeAsState()
        val isLoading by viewModel.isLoading.observeAsState(false)
        val context = LocalContext.current
        val isEmpty = isEmpty(observePlanetOffline)
        val snackState = remember { SnackbarHostState() }
        val snackScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // snackScope.launch { snackState.showSnackbar(observeError2.toString()) }

            // Toast.makeText(context, viewModel.errorMessage, Toast.LENGTH_SHORT).show()

            // Toast.makeText(context, viewModel.errorMessage, Toast.LENGTH_SHORT).show()

          /*  if (viewModel.showError == null) {
                Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
                viewModel.showError.value?.let {
                    Text(
                        text = "YES",
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                Toast.makeText(context, "No", Toast.LENGTH_SHORT).show()

                Text(
                    text = viewModel.showError.value.toString(),
                    textAlign = TextAlign.Center,
                )
            }*/

            // Toast.makeText(context, viewModel.showError.value.toString(), Toast.LENGTH_SHORT).show()
           /* viewModel.showError.value?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                )
            }*/
            PlanetProgressIndicator(isDisplayed = isLoading)

           /* val image = loadPicture(url = "https://res.cloudinary.com/dxrxviiv8/image/upload/v1649596230/quran-lyfe/h68.png", defaultImage = R.drawable.placeholder).value
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .size(250.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(16.dp)))

                )
            }*/

            GlideImage(
                imageModel = "https://res.cloudinary.com/dxrxviiv8/image/upload/v1649596238/quran-lyfe/p12.png",
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Fit,
                // shows a placeholder while loading the image.
                placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                // shows an error ImageBitmap when the request failed.
                error = ImageBitmap.imageResource(R.drawable.placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .size(250.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(25.dp))),
                requestOptions = {
                    RequestOptions()
                        /*  .override(256, 256)*/
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp, start = 20.dp, end = 20.dp)
            ) {
                if (viewModel.errorMessage.isEmpty()) {
                    Text(
                        text = "${if (isEmpty) "Is Empty" else "Not Empty"} ",
                        textAlign = TextAlign.Center,
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(observePlanetOffline) { planet ->

                            planet.planetName?.let { Text(text = it) }
                            GlideImage(
                                imageModel = planet.planetImage,
                                // Crop, Fit, Inside, FillHeight, FillWidth, None
                                contentScale = ContentScale.Fit,
                                // shows a placeholder while loading the image.
                                placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                                // shows an error ImageBitmap when the request failed.
                                error = ImageBitmap.imageResource(R.drawable.placeholder),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
                                requestOptions = {
                                    RequestOptions()
                                        .override(256, 256)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop()
                                }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "${viewModel.errorMessage} ${if (isEmpty) "Is Empty" else "Not Empty"} ",
                        textAlign = TextAlign.Center,
                    )

                    SnackbarHost(hostState = snackState, Modifier)
                    snackScope.launch { snackState.showSnackbar(viewModel.errorMessage) }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(observePlanetOffline) { planet ->

                            planet.planetName?.let { Text(text = it) }
                            GlideImage(
                                imageModel = planet.planetImage,
                                // Crop, Fit, Inside, FillHeight, FillWidth, None
                                contentScale = ContentScale.Fit,
                                // shows a placeholder while loading the image.
                                placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
                                // shows an error ImageBitmap when the request failed.
                                error = ImageBitmap.imageResource(R.drawable.placeholder),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
                                requestOptions = {
                                    RequestOptions()
                                        .override(256, 256)
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .centerCrop()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun isEmpty(list: List<*>?): Boolean {
        return list != null && list.isEmpty()
    }
}
