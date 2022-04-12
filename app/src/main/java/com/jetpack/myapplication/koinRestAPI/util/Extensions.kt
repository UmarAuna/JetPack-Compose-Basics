package com.jetpack.myapplication.koinRestAPI.util

import android.content.Context
import android.graphics.Bitmap
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.takeOrElse
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.jetpack.myapplication.R
import java.io.ByteArrayOutputStream

private var mToast: Toast? = null

val LOG_TAG = "com.jetpack.myapplication"

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

inline val String.isNumeric: Boolean
    get() = matches("[-+]?\\d*\\.?\\d+".toRegex())

inline val String.tagName: String
    get() = "$LOG_TAG.$this"

@Suppress("DEPRECATION")
inline val String.fromHtml: Spanned
    get() = Html.fromHtml(this)

fun Context?.showToast(@StringRes resId: Int) = showToast(this?.getString(resId))

fun Context?.showToast(message: String?) {
    var toastMessage: String? = message
    if (message != null && message.isNumeric) {
        toastMessage = try {
            this?.getString(message.toInt())
        } catch (e: Exception) {
            message
        }
    }
    mToast?.cancel()
    if (this != null)
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).apply { show() }
}

fun Bitmap.toBase64(): String? {
    return try {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, stream)
        Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
    } catch (e: Exception) {
        null
    }
}

// TODO: Try this: https://github.com/ireward/compose-html
/*If you have tags in the HTML you need to set the
TextView property movementMethod = LinkMovementMethod.getInstance()
to make the links clickable.*/
@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
) {
    val textColor = color
        .takeOrElse { style.color }
        .takeOrElse { LocalContentColor.current.copy(alpha = LocalContentAlpha.current) }
        .toArgb()

    val density = LocalDensity.current

    val textSize = with(density) {
        style.fontSize
            .takeOrElse { LocalTextStyle.current.fontSize }
            .toPx()
    }

    val lineHeight = with(density) {
        style.lineHeight
            .takeOrElse { LocalTextStyle.current.lineHeight }
            .roundToPx()
    }

    val formattedText = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            AppCompatTextView(context).apply {
                setTextColor(textColor)

                // I haven't found out how to extract the typeface from style so I created my_font_family.xml and set it here
                typeface = ResourcesCompat.getFont(context, R.font.harbour_bold)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

                if (style.lineHeight.isSp) {
                    this.lineHeight = lineHeight
                } else {
                    // Line Height could not be set
                }
            }
        },
        update = { it.text = formattedText }
    )
}

/*fun Context.noNetworkConnectivityError(): AppResult.Error {
    return AppResult.Error(Exception(this.resources.getString(R.string.no_network_connectivity)))
}*/
