package com.rmaproject.myqoran.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.rmaproject.myqoran.R
import java.util.regex.Pattern

object TajweedHelper {

    private const val kasratain = '\u064d'
    private const val fathatain = '\u064b'
    private const val dammatain = '\u064c'
    private const val shadda = '\u0651'
    private const val space = ' '

    //arabic letters
    private const val nun = '\u0646'
    private const val mim = '\u0645'
    private const val qaf = '\u0642'
    private const val toa = '\u0637'
    private const val ba = '\u0628'
    private const val zim = '\u062c'
    private const val dal = '\u062f'
    private const val soad = '\u0635'
    private const val zaal = '\u0630'
    private const val tha = '\u062b'
    private const val kaf = '\u0643'
    private const val wow = '\u0648'
    private const val shin = '\u0634'
    private const val seen = '\u0633'
    private const val zha = '\u0632'
    private const val fa = '\u0641'
    private const val ta = '\u062a'
    private const val doad = '\u0636'
    private const val zoa = '\u0638'
    private const val ra = '\u0631'
    private const val lam = '\u0644'
    private const val indopak_kaf = '\u06a9'

    //stop signs uthmani
    private const val stop_sign_zim = '\u06da'
    private const val stop_sign_lam = '\u06d9'
    private const val stop_sign_mim = '\u06d8'
    private const val stop_sign_three_dots = '\u06db'
    private const val stop_sign_qaf_lam = '\u06d7'
    private const val stop_sign_soad_lam = '\u06d6'

    //sukuns
    private const val sukun = '\u0652'
    private const val curvy_sukun = '\u06e1'

    //others
    private const val low_meem = '\u06ed'
    private const val high_meem = '\u06e2'
    private const val alif_hamza = '\u0627'
    private const val empty_ya = alif_hamza
    private const val empty_alif = '\u0649'
    private const val another_ya = '\u064a'
    private const val ta_marbuta = '\u0647'
    private const val supercript_alif_khara_fatha = '\u0670'
    private const val uthmani_stop_signs =
        "" + stop_sign_three_dots + stop_sign_zim + stop_sign_qaf_lam + stop_sign_soad_lam + stop_sign_lam + stop_sign_mim
    private val gunnahmatcher = Pattern.compile("[$nun|$mim]$shadda").matcher("")
    private val iqlabmmatcher =
        Pattern.compile("[$high_meem$low_meem][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space?$ba")
            .matcher("")
    private val qalqalamatcher =
        Pattern.compile("[$qaf$toa$ba$zim$dal]($sukun|$curvy_sukun|[^$ta_marbuta]?[^$ta_marbuta$empty_alif$empty_ya]?[^$ta_marbuta$empty_alif$alif_hamza]$)")
            .matcher("")
    private val idhghammatcher =
        Pattern.compile("([$nun$fathatain$dammatain$kasratain][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space[$nun$mim$another_ya$wow]$shadda?)|$mim[$uthmani_stop_signs$sukun$curvy_sukun]?$space$mim")
            .matcher("")
    private val idhghammatcherwihtoutgunnah =
        Pattern.compile("[$nun$kasratain$fathatain$dammatain][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space[$ra$lam]")
            .matcher("")
    private val ikhfamatcher =
        Pattern.compile("([$nun$kasratain$fathatain$dammatain][$sukun$curvy_sukun$empty_ya$empty_alif]?[$uthmani_stop_signs]?$space?[$soad$zaal$tha$kaf$zim$shin$qaf$seen$dal$toa$zha$fa$ta$doad$zoa$indopak_kaf])|$mim[$sukun$curvy_sukun]?$space?$ba")
            .matcher("")

    private var colors: IntArray? = null

    fun getTajweed(context: Context?, s: String): Spannable {
        if (colors == null) {
            colors = IntArray(6)
            colors!![0] = ContextCompat.getColor(context!!, R.color.color_ghunna)
            colors!![1] = ContextCompat.getColor(context, R.color.color_qalqala)
            colors!![2] = ContextCompat.getColor(context, R.color.color_iqlab)
            colors!![3] = ContextCompat.getColor(context, R.color.color_idgham)
            colors!![4] = ContextCompat.getColor(context, R.color.color_idghamwo)
            colors!![5] = ContextCompat.getColor(context, R.color.color_ikhfa)
        }
        val text: Spannable = SpannableString(s)
        gunnahmatcher.reset(s)
        while (gunnahmatcher.find()) {
//            Timber.d("gunnah Found " + gunnahmatcher.group(), "starting at " + gunnahmatcher.start() + " and ending at " + gunnahmatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![0]),
                gunnahmatcher.start(),
                getEnd(s, gunnahmatcher.end()),
                0
            )
        }
        qalqalamatcher.reset(s)
        while (qalqalamatcher.find()) {
//            Timber.d("qalqala Found " + qalqalamatcher.group(), "starting at " + qalqalamatcher.start() + " and ending at " + qalqalamatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![1]),
                qalqalamatcher.start(),
                qalqalamatcher.end(),
                0
            )
        }
        //
        iqlabmmatcher.reset(s)
        while (iqlabmmatcher.find()) {
//            Timber.d("iqlab Found text " + iqlabmmatcher.group()+ "starting at " + iqlabmmatcher.start() + " " + "and ending at " + iqlabmmatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![2]),
                getIqlabStart(s, iqlabmmatcher.start()),
                iqlabmmatcher.end() + 1,
                0
            )
        }
        //
        idhghammatcher.reset(s)
        while (idhghammatcher.find()) {
//            Timber.d("idhgam Found " + idhghammatcher.group(), idhghammatcher.group() + " starting at " + idhghammatcher.start() + " and ending at " + idhghammatcher.end());
            text.setSpan(
                ForegroundColorSpan(colors!![3]),
                getStart(s, idhghammatcher.start()),
                getEnd(s, idhghammatcher.end()),
                0
            )
        }
        idhghammatcherwihtoutgunnah.reset(s)
        while (idhghammatcherwihtoutgunnah.find()) {
//            Timber.d("idhgamwo Found " + idhghammatcherwihtoutgunnah.group(), "starting at " + idhghammatcherwihtoutgunnah.start() + " and ending at " + idhghammatcherwihtoutgunnah.end());
            text.setSpan(
                ForegroundColorSpan(colors!![4]),
                getStart(s, idhghammatcherwihtoutgunnah.start()),
                idhghammatcherwihtoutgunnah.end() - 1,
                0
            )
        }
        ikhfamatcher.reset(s)
        while (ikhfamatcher.find()) {
//            Timber.d("ikhfa Found text " + ikhfamatcher.group()+ "starting at " + ikhfamatcher.start() + " " + "and ending at " + ikhfamatcher.end() + " " + "actual " + getEnd(s, ikhfamatcher.end()) + " " + s.charAt(ikhfamatcher.end() + 1));
            text.setSpan(
                ForegroundColorSpan(colors!![5]),
                getStart(s, ikhfamatcher.start()),
                getEnd(s, ikhfamatcher.end()),
                0
            )
        }

        return text
    }

    private fun getIqlabStart(m: String, start: Int): Int {
        val ch = m[start - 1]
        return if ((ch == fathatain) or (ch == dammatain) or (ch == kasratain)) {
            if (m[start - 2] == shadda) start - 3 else start - 2
        } else start - 1
    }

    private fun getEnd(m: String, end: Int): Int {
        if (m[end] == shadda) {
            return if (m[end + 2] == supercript_alif_khara_fatha) { // standing fathah
                end + 3
            } else end + 2
        }
        return if (m[end + 1] == supercript_alif_khara_fatha || m[end + 1] == shadda) { // standing fathah
            end + 2
        } else end + 1
    }

    private fun getStart(m: String, start: Int): Int {
        val ch = m[start]
        return if ((ch == fathatain) or (ch == dammatain) or (ch == kasratain)) {
            if (m[start - 1] == shadda) {
                start - 2
            } else start - 1
        } else start
    }
}