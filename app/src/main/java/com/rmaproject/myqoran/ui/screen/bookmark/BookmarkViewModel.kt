package com.rmaproject.myqoran.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import com.rmaproject.myqoran.data.repository.QoranRepository
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    private val repository: QoranRepository
) : ViewModel() {

}