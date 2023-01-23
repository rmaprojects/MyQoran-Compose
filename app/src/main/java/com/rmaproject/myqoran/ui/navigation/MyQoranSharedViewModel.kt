package com.rmaproject.myqoran.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rmaproject.myqoran.data.local.entities.Surah

class MyQoranSharedViewModel: ViewModel() {
    private val _totalAyah = MutableLiveData<List<Int>>()

    fun setTotalAyah(surah: List<Surah>) {
        val totalAyah = mutableListOf<Int>()
        surah.forEach {
            totalAyah.add(it.numberOfAyah!!)
        }
        _totalAyah.value = totalAyah
    }

    fun getTotalAyah() = _totalAyah.value
}