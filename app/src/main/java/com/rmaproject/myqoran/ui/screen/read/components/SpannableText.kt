package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun SpannableText(
    text: String,
    modifier: Modifier = Modifier,
    spanStyle: SpanStyle = SpanStyle(color = Color.Blue),
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (String) -> Unit,
) {

    val annotatedString = buildSpannable(text, spanStyle, true)

    ClickableText(
        text = annotatedString,
        modifier,
        style,
        softWrap,
        overflow,
        maxLines,
        onTextLayout,
        onClick = { offset ->
            text.split("""\d+""".toRegex()).forEach { tag ->
                annotatedString.getStringAnnotations(tag, offset, offset).firstOrNull()?.let {
                    onClick.invoke(it.item)
                }
            }
        }
    )
}

private fun buildSpannable(
    text: String,
    spanStyle: SpanStyle,
    isClickable: Boolean
) = buildAnnotatedString {
    text.split(" ").forEach {
        if (it.contains("""\d+""".toRegex())) {
            if (isClickable) pushStringAnnotation(it, it)
            withStyle(style = spanStyle) {
                append("$it ")
            }
            if (isClickable) pop()
        } else {
            append("$it ")
        }
    }
}