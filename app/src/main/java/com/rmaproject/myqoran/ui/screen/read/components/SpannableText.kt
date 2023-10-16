package com.rmaproject.myqoran.ui.screen.read.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


@Composable
fun SpannableText(
    text: String,
    modifier: Modifier = Modifier,
    spanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.primary
    ),
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground
    ),
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (String) -> Unit,
) {

    val fixedText = Regex("(?<=\\p{L})(?=\\d)").replace(text, " ")
    val annotatedString = buildSpannable(fixedText, spanStyle)

    ClickableText(
        text = annotatedString,
        modifier,
        style,
        softWrap,
        overflow,
        maxLines,
        onTextLayout,
        onClick = { offset ->
            fixedText.split(" ").forEach { tag ->
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
    val regex = Regex("""\d""")
    text.split(" ").forEach {
        if (regex.containsMatchIn(it)) {
            pushStringAnnotation(it, it)
            withStyle(
                style = spanStyle.copy(
                    textDecoration = TextDecoration.Underline,
                    fontSize = 10.sp
                )
            ) {
                append(" $it ")
            }
            pop()
        } else {
            append(" $it ")
        }
    }
}