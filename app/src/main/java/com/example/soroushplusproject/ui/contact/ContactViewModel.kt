package com.example.soroushplusproject.ui.contact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.data.Repository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getData() {
        viewModelScope.launch {
            repository.getContact().collect{
                Log.e("TAG", "getData: $it", )
            }
        }

    }
}