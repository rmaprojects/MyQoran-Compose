package com.rmaproject.myqoran.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Converters {
    fun convertMillisToActualDate(
        dateAdded: Long
    ): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateAdded))
    }

    fun convertNumberToThreeDigits(
        number: Int
    ): String {
        return String.format("%03d", number)
    }

    fun adaptEnTranslation(translation: String): String {
        val pattern = Pattern.compile("\\(([^)]+)\\)")
        return translation.let { pattern.matcher(it).replaceAll("") }
    }

    private fun reverseAyahTextNumber(ayahText: String?): String {
        val digit = mutableListOf<Char>()
        ayahText?.forEach {
            if (it.isDigit()) {
                digit.add(it)
            }
        }
        val ayahNumberArabic = digit.joinToString("")
        val textWithoutNumber = ayahText?.replace(ayahNumberArabic, "")
        return textWithoutNumber + digit.reversed().joinToString("")
    }

    fun applyTajweed(
        context: Context,
        ayahText: String
    ): Spannable {
        val reversedAyahText = reverseAyahTextNumber(ayahText)
        return TajweedHelper.getTajweed(
            context,
            reversedAyahText
        )
    }

}

fun Spannable.toAnnotatedString(primaryColor: Color): AnnotatedString {
    val builder = AnnotatedString.Builder(this.toString())
    val copierContext = CopierContext(primaryColor)
    SpanCopier.values().forEach { copier ->
        getSpans(0, length, copier.spanClass).forEach { span ->
            copier.copySpan(span, getSpanStart(span), getSpanEnd(span), builder, copierContext)
        }
    }
    return builder.toAnnotatedString()
}

private data class CopierContext(
    val primaryColor: Color,
)

private enum class SpanCopier {
    URL {
        override val spanClass = URLSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            val urlSpan = span as URLSpan
            destination.addStringAnnotation(
                tag = name,
                annotation = urlSpan.url,
                start = start,
                end = end,
            )
            destination.addStyle(
                style = SpanStyle(color = context.primaryColor, textDecoration = TextDecoration.Underline),
                start = start,
                end = end,
            )
        }
    },
    FOREGROUND_COLOR {
        override val spanClass = ForegroundColorSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            val colorSpan = span as ForegroundColorSpan
            destination.addStyle(
                style = SpanStyle(color = Color(colorSpan.foregroundColor)),
                start = start,
                end = end,
            )
        }
    },
    UNDERLINE {
        override val spanClass = UnderlineSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            destination.addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = start,
                end = end,
            )
        }
    },
    STYLE {
        override val spanClass = StyleSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            val styleSpan = span as StyleSpan

            destination.addStyle(
                style = when (styleSpan.style) {
                    Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
                    Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
                    Typeface.BOLD_ITALIC -> SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
                    else -> SpanStyle()
                },
                start = start,
                end = end,
            )
        }
    };

    abstract val spanClass: Class<out CharacterStyle>
    abstract fun copySpan(span: Any, start: Int, end: Int, destination: AnnotatedString.Builder, context: CopierContext)
}