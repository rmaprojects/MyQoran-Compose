package com.rmaproject.myqoran.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

object GlobalActions {
    fun copyAyah(
        context: Context,
        surahName: String,
        ayahText: String,
        translation: String,
    ) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            surahName,
            "$ayahText\n\n$translation"
        )
        clipboard.setPrimaryClip(clip)
    }
    fun shareAyah(
        context: Context,
        surahName: String,
        ayahText: String,
        translation: String,
    ) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, surahName)
        shareIntent.putExtra(Intent.EXTRA_TEXT, ayahText)
        shareIntent.putExtra(Intent.EXTRA_TEXT, translation)
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}