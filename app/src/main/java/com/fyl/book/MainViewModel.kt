package com.fyl.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyl.book.data.SharedPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository
): ViewModel() {

    private val isShowActonBarMenu: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {

    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}