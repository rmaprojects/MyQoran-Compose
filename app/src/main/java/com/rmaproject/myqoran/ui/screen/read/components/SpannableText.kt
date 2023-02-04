package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun SpannableText(
    text: String,
    modifier: Modifier = Modifier,
    spanStyle: SpanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground
    ),
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (String) -> Unit,
) {

    val annotatedString = buildSpannable(text, spanStyle)

    ClickableText(
        text = annotatedString,
        modifier,
        style,
        softWrap,
        overflow,
        maxLines,
        onTextLayout,
        onClick = { offset ->
            text.split(" ").forEach { tag ->
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
) = buildAnnotatedString {
    text.split(" ").forEach {
        if ("""\d""".toRegex().containsMatchIn(it)) {
            pushStringAnnotation(it, it)
            withStyle(style = spanStyle) {
                append("$it ")
            }
            pop()
        } else {
            append("$it ")
        }
    }
}